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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ReactiveEchoServerV2 {

	static interface IEventHandler<T extends SelectableChannel> {

		void handleInput(T channel, int eventType);

		T getHandle();
	}

	static class EchoServerAcceptor implements IEventHandler<ServerSocketChannel> {
		private ServerSocketChannel serverSocketChannel;
		private EchoReactor reactor;

		public EchoServerAcceptor(EchoReactor reactor, ServerSocketChannel serverSocketChannel) {
			super();
			this.reactor = reactor;
			this.serverSocketChannel = serverSocketChannel;
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
			return serverSocketChannel;
		}
	}

	static class EchoServerHandler implements IEventHandler<SocketChannel> {
		private SocketChannel socketChannel;

		public EchoServerHandler(EchoReactor reactor, SocketChannel socketChannel) {
			super();
			this.socketChannel = socketChannel;
		}

		@Override
		public void handleInput(SocketChannel clientChannel, int eventType) {
			if ((eventType & SelectionKey.OP_READ) == 0) {
				return;
			}

			ByteBuffer buffer = ByteBuffer.allocate(2048);
			try {
				clientChannel.read(buffer);

				buffer.flip();

				clientChannel.write(buffer);
				buffer.clear();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public SocketChannel getHandle() {
			return socketChannel;
		}

	}

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

	static class EchoReactor {
		private SynchronousEventDemultiplexer synchronousEventDemultiplexer = new SynchronousEventDemultiplexer();
		private Map<SelectableChannel, IEventHandler<? extends SelectableChannel>> map = new HashMap<SelectableChannel, IEventHandler<? extends SelectableChannel>>();

		public void register(SelectableChannel channel, int eventType, IEventHandler<? extends SelectableChannel> eventHandler) {
			map.put(channel, eventHandler);
			synchronousEventDemultiplexer.register(channel, eventType);
		}

		public void runEventLoop() {
			while (true) {
				Set<SelectionKey> selectedKeys = synchronousEventDemultiplexer.select();
				Iterator<SelectionKey> iterator = selectedKeys.iterator();

				while (iterator.hasNext()) {
					SelectionKey selectedKey = iterator.next();
					iterator.remove();
					SelectableChannel channel = selectedKey.channel();
					if (channel.isOpen()) {

					}
					IEventHandler<SelectableChannel> eventHandler = (IEventHandler<SelectableChannel>) map.get(channel);

					eventHandler.handleInput(channel, selectedKey.interestOps());
				}
			}
		}
	}

	public static void startServer() {

		try {
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			ServerSocket serverSocket = serverSocketChannel.socket();
			serverSocket.bind(new InetSocketAddress(13786));

			serverSocketChannel.configureBlocking(false);
			EchoReactor reactor = new EchoReactor();

			reactor.register(serverSocketChannel, SelectionKey.OP_ACCEPT, new EchoServerAcceptor(reactor, serverSocketChannel));
			reactor.runEventLoop();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		startServer();
	}

}
