package samples.coursera.posa2.w7.echoserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ProgramV2 {

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

		    eventHandler.handleInput(channel, selectedKey.interestOps());
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

	public void handleInput(SelectableChannel channel, int eventType);

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
	public void handleInput(SelectableChannel channel, int eventType) {
	    if ((eventType & SelectionKey.OP_ACCEPT) == 0) {
		return;
	    }

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
     * An event handler for READ events
     */
    static class EchoServerHandler implements IEventHandler {
	private final SocketChannel handle;
	private final HalfSyncPool halfSyncPool;

	public EchoServerHandler(EchoReactor reactor, SocketChannel handle, HalfSyncPool halfSyncPool) {
	    this.handle = handle;
	    this.halfSyncPool = halfSyncPool;
	}

	@Override
	public void handleInput(SelectableChannel channel, int eventType) {
	    if ((eventType & SelectionKey.OP_READ) == 0) {
		return;
	    }
	    if (!(channel instanceof SocketChannel)) {
		return;
	    }

	    ByteBuffer buffer = ByteBuffer.allocate(2048);
	    try {
		SocketChannel clientChannel = (SocketChannel) channel;

		// This will read till newline \n, \r or \r\n
		int readCount = clientChannel.read(buffer);

		if (readCount == -1) {
		    return;
		}

		buffer.flip();

		// clientChannel.write(buffer);
		// buffer.clear();
		halfSyncPool.enqueue(new EchoTask(buffer, clientChannel));
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}

	@Override
	public SocketChannel getHandle() {
	    return handle;
	}
    }

    static class EchoTask implements Runnable {
	private final ByteBuffer buffer;
	private final SocketChannel clientChannel;

	public EchoTask(ByteBuffer buffer, SocketChannel clientChannel) {
	    this.buffer = buffer;
	    this.clientChannel = clientChannel;
	}

	@Override
	public void run() {
	    try {
		CharsetEncoder encoder = Charset.forName("US-ASCII").newEncoder();
		String threadId = String.valueOf(Thread.currentThread().getId());
		this.clientChannel.write(encoder.encode(CharBuffer.wrap(threadId)));

		this.clientChannel.write(buffer);
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    }

    static class HalfSyncPool {
	private ThreadPoolExecutor tp = new ThreadPoolExecutor(2, 5, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

	public void enqueue(EchoTask task) {
	    tp.submit(task);
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
	    System.out.println("Help: " + ProgramV2.class.getSimpleName() + " <port> ");
	    return;
	}

	try {
	    int port = Integer.parseInt(args[0]);
	    ProgramV2 server = new ProgramV2();
	    server.startServer(port);
	} catch (NumberFormatException e) {
	    System.out.println("Help: " + ProgramV2.class.getSimpleName() + " <port> ");
	}
    }
}
