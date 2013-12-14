package samples.coursera.posa2.w6.echoserver;

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
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Program {

    static class EchoReactor {
	private SynchronousEventDemultiplexer synchronousEventDemultiplexer = new SynchronousEventDemultiplexer();
	private Map<SelectableChannel, IEventHandler<? extends SelectableChannel>> demuxTable = new Hashtable<SelectableChannel, IEventHandler<? extends SelectableChannel>>();

	public void register(SelectableChannel handle, int eventType, IEventHandler<? extends SelectableChannel> eventHandler) {
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
		    @SuppressWarnings("unchecked")
		    IEventHandler<SelectableChannel> eventHandler = (IEventHandler<SelectableChannel>) demuxTable.get(channel);

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

    static interface IEventHandler<T extends SelectableChannel> {

	public void handleInput(T channel, int eventType);

	public T getHandle();
    }

    /**
     * Implements Acceptor part of Acceptor-Connector pattern An event handler
     * for ACCEPT events
     */
    static class EchoServerAcceptor implements IEventHandler<ServerSocketChannel> {
	private ServerSocketChannel handle;
	private EchoReactor reactor;

	public EchoServerAcceptor(EchoReactor reactor, ServerSocketChannel handle) {
	    this.reactor = reactor;
	    this.handle = handle;
	}

	@Override
	public void handleInput(ServerSocketChannel serverChannel, int eventType) {
	    if ((eventType & SelectionKey.OP_ACCEPT) == 0) {
		return;
	    }

	    try {
		SocketChannel clientChannel = serverChannel.accept();
		clientChannel.configureBlocking(false);
		EchoServerHandler echoServerHandler = new EchoServerHandler(reactor, clientChannel);
		reactor.register(clientChannel, SelectionKey.OP_READ, echoServerHandler);
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
    static class EchoServerHandler implements IEventHandler<SocketChannel> {
	private SocketChannel handle;

	public EchoServerHandler(EchoReactor reactor, SocketChannel handle) {
	    this.handle = handle;
	}

	@Override
	public void handleInput(SocketChannel clientChannel, int eventType) {
	    if ((eventType & SelectionKey.OP_READ) == 0) {
		return;
	    }

	    ByteBuffer buffer = ByteBuffer.allocate(2048);
	    try {
		int readCount = clientChannel.read(buffer);

		if (readCount == -1) {
		    return;
		}

		buffer.flip();

		clientChannel.write(buffer);
		buffer.clear();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}

	@Override
	public SocketChannel getHandle() {
	    return handle;
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

	    reactor.register(serverSocketChannel, SelectionKey.OP_ACCEPT, new EchoServerAcceptor(reactor, serverSocketChannel));
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
