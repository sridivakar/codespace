package samples.coursera.posa2.w7.echoserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Program {

    static class EchoReactor {
	private SynchronousEventDemultiplexer synchronousEventDemultiplexer = new SynchronousEventDemultiplexer();
	private Map<SelectableChannel, IEventHandler> demuxTable = new Hashtable<SelectableChannel, IEventHandler>();

	public void register(int eventType, IEventHandler eventHandler) {
	    SelectableChannel handle = eventHandler.getHandle();
	    demuxTable.put(handle, eventHandler);
	    synchronousEventDemultiplexer.register(handle, eventType);
	}

	public void runEventLoop() {
	    while (true) {
		Set<SelectionKey> selectedKeys = synchronousEventDemultiplexer.select();
		Iterator<SelectionKey> iterator = selectedKeys.iterator();

		while (iterator.hasNext()) {
		    SelectionKey selectedKey = iterator.next();
		    iterator.remove();
		    SelectableChannel channel = selectedKey.channel();
		    if (!channel.isOpen()) {
			selectedKey.cancel();
		    }

		    IEventHandler eventHandler = demuxTable.get(channel);

		    eventHandler.handleInput(selectedKey, selectedKey.interestOps());
		}
	    }
	}
    }

    /**
     * Plays role of Synchronous Event Demultiplexer in Reactor Pattern Also
     * implements Wrapper Facade pattern, which wraps select
     */
    static class SynchronousEventDemultiplexer {
	private final Selector selector;

	public SynchronousEventDemultiplexer() {
	    Selector local = null;
	    try {
		local = Selector.open();
	    } catch (IOException e) {
		local = null;
		e.printStackTrace();
	    }

	    this.selector = local;
	}

	public void register(SelectableChannel selectableChannel, int selectionKey) {
	    try {
		selectableChannel.register(selector, selectionKey);
	    } catch (ClosedChannelException e) {
		e.printStackTrace();
	    }
	}

	public Set<SelectionKey> select() {
	    Set<SelectionKey> selectedKeys = null;
	    try {
		this.selector.select();
		selectedKeys = selector.selectedKeys();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	    return selectedKeys;
	}
    }

    static interface IEventHandler {

	public void handleInput(SelectionKey selectedKey, int eventType);

	public SelectableChannel getHandle();
    }

    /**
     * Implements Acceptor part of Acceptor-Connector pattern An event handler
     * for ACCEPT events
     */
    static class EchoServerAcceptor implements IEventHandler {
	private ServerSocketChannel handle;
	private EchoReactor reactor;
	private HalfSyncPool halfSyncPool;

	public EchoServerAcceptor(EchoReactor reactor, ServerSocketChannel handle, HalfSyncPool halfSyncPool) {
	    this.reactor = reactor;
	    this.handle = handle;
	    this.halfSyncPool = halfSyncPool;
	}

	@Override
	public void handleInput(SelectionKey selectedKey, int eventType) {
	    if ((eventType & SelectionKey.OP_ACCEPT) == 0) {
		return;
	    }

	    SelectableChannel channel = selectedKey.channel();
	    if (!(channel instanceof ServerSocketChannel)) {
		return;
	    }

	    try {
		ServerSocketChannel serverChannel = (ServerSocketChannel) channel;

		SocketChannel clientChannel = serverChannel.accept();
		clientChannel.configureBlocking(false);
		EchoServerHandler echoServerHandler = new EchoServerHandler(reactor, clientChannel, halfSyncPool);
		reactor.register(SelectionKey.OP_READ, echoServerHandler);
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}

	@Override
	public ServerSocketChannel getHandle() {
	    return handle;
	}
    }

    /**
     * An event handler for READ events.
     * 
     * Performs the "Half-async" portion of the server.
     * 
     * Does multiple trips through the reactor to read each chunk (until \n, \r
     * or \r\n) of client data via a single non-blocking read() each time.
     * 
     * Puts the client data into a synchronous queue by calling the enqueue
     * method on HalfSyncPool to enqueue the message for subsequent processing
     * by a thread in the pool of threads that are waiting in the synchronous
     * queue.
     * 
     */
    static class EchoServerHandler implements IEventHandler {
	private static final Charset charset = Charset.forName("ISO-8859-1");
	private static final CharsetDecoder decoder = charset.newDecoder();

	private final SocketChannel handle;
	private final HalfSyncPool halfSyncPool;

	public EchoServerHandler(EchoReactor reactor, SocketChannel handle, HalfSyncPool halfSyncPool) {
	    this.handle = handle;
	    this.halfSyncPool = halfSyncPool;
	}

	@Override
	public void handleInput(SelectionKey selectedKey, int eventType) {
	    if ((eventType & SelectionKey.OP_READ) == 0) {
		return;
	    }

	    SelectableChannel channel = selectedKey.channel();
	    if (!(channel instanceof SocketChannel)) {
		return;
	    }

	    try {
		ByteBuffer byteBuffer = ByteBuffer.allocate(2048);

		StringBuilder builder;
		Object object = selectedKey.attachment();
		if (object instanceof StringBuilder) {
		    builder = (StringBuilder) object;
		} else {
		    builder = new StringBuilder();
		}

		SocketChannel clientChannel = (SocketChannel) channel;

		// This will read till newline \n, \r or \r\n
		int readCount = clientChannel.read(byteBuffer);

		if (readCount == -1) {
		    return;
		}

		byteBuffer.flip();

		String s = decoder.decode(byteBuffer).toString();
		builder.append(s);
		if (s.endsWith("\n") || s.endsWith("\r") || s.endsWith("\r\n")) {
		    halfSyncPool.enqueue(new EchoTask(builder.toString(), clientChannel));
		    builder.setLength(0);
		}
		byteBuffer.clear();
		selectedKey.attach(builder);
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}

	@Override
	public SocketChannel getHandle() {
	    return handle;
	}
    }

    /**
     * "Half-sync" portion of the server
     * 
     * It has only enqueue method to add the EchoTasks to the
     * ThreadPoolExecutor.
     * 
     * Note: Dequeueing echoTasks and running those will be done by
     * ThreadPoolExecutor.
     */
    static class HalfSyncPool {
	private ThreadPoolExecutor tp = new ThreadPoolExecutor(2, 5, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

	public void enqueue(EchoTask task) {
	    tp.submit(task);
	}
    }

    static class EchoTask implements Runnable {
	private final String message;
	private final SocketChannel clientChannel;

	public EchoTask(String message, SocketChannel clientChannel) {
	    this.message = message;
	    this.clientChannel = clientChannel;
	}

	@Override
	public void run() {
	    try {
		ByteBuffer buffer = ByteBuffer.allocate(512);

		String threadId = String.valueOf(Thread.currentThread().getId()) + " - ";
		buffer.put(threadId.getBytes());
		buffer.put(message.getBytes());
		buffer.flip();

		this.clientChannel.write(buffer);
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    }

    public void startServer(int port) {
	ServerSocketChannel serverSocketChannel = null;
	try {
	    serverSocketChannel = ServerSocketChannel.open();
	    ServerSocket serverSocket = serverSocketChannel.socket();
	    serverSocket.bind(new InetSocketAddress(port));

	    System.out.println("Echo server started, press Ctrl+c to quit");

	    serverSocketChannel.configureBlocking(false);
	    EchoReactor reactor = new EchoReactor();

	    reactor.register(SelectionKey.OP_ACCEPT, new EchoServerAcceptor(reactor, serverSocketChannel, new HalfSyncPool()));
	    reactor.runEventLoop();
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    try {
		if (serverSocketChannel != null) {
		    serverSocketChannel.close();
		}
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    }

    public static void main(String[] args) {
	if (args.length < 1) {
	    System.out.println("Help: " + Program.class.getSimpleName() + " <port> ");
	    return;
	}

	try {
	    int port = Integer.parseInt(args[0]);
	    Program server = new Program();
	    server.startServer(port);
	} catch (NumberFormatException e) {
	    System.out.println("Help: " + Program.class.getSimpleName() + " <port> ");
	}
    }
}
