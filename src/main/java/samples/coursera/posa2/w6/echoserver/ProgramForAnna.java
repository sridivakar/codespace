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

public class ProgramForAnna
{

	static interface EventHandlerInterface
	{

		public void handleInput(SelectableChannel handle, int event_type);

		public SelectableChannel getHandle();
	}

	/**
	 * Patterns : Acceptor of Acceptor-Connector Pattern
	 */
	static class ServerAcceptor implements EventHandlerInterface
	{
		private ServerSocketChannel handle;
		private Reactor reactor;

		public ServerAcceptor(Reactor reactor, ServerSocketChannel handle)
		{
			super();
			this.reactor = reactor;
			this.handle = handle;
		}

		@Override
		public void handleInput(SelectableChannel channel, int event_type)
		{
			if ((event_type & SelectionKey.OP_ACCEPT) == 0
					|| !(channel instanceof ServerSocketChannel))
			{
				return;
			}

			ServerSocketChannel serverChannel = (ServerSocketChannel) channel;
			try
			{
				SocketChannel client_handle = serverChannel.accept();
				client_handle.configureBlocking(false);
				EchoServerHandler echoServerHandler = new EchoServerHandler(
						reactor, client_handle);
				reactor.register(client_handle, SelectionKey.OP_READ,
						echoServerHandler);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		@Override
		public SelectableChannel getHandle()
		{
			return handle;
		}
	}

	/**
	 * Patterns : Concrete Event Handler in Reactor Pattern
	 */
	static class EchoServerHandler implements EventHandlerInterface
	{
		private SocketChannel handle;

		public EchoServerHandler(Reactor reactor, SocketChannel handle)
		{
			super();
			this.handle = handle;
		}

		@Override
		public void handleInput(SelectableChannel channel, int event_type)
		{
			if ((event_type & SelectionKey.OP_READ) == 0
					|| !(channel instanceof SocketChannel))
			{
				return;
			}

			SocketChannel client_channel = (SocketChannel) channel;
			ByteBuffer buffer = ByteBuffer.allocate(2048);
			try
			{
				int read_count = client_channel.read(buffer);

				if (read_count == -1)
				{
					return;
				}

				buffer.flip();

				client_channel.write(buffer);
				buffer.clear();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		@Override
		public SelectableChannel getHandle()
		{
			return handle;
		}
	}

	/**
	 * Patterns : Synchronous Event Demuxer in Reactor Pattern and Wrapper
	 * Facade pattern
	 */
	static class SynchEventDemux
	{

		private final Selector selector;

		public SynchEventDemux()
		{
			Selector local = null;
			try
			{
				local = Selector.open();
			}
			catch (IOException e)
			{
				local = null;
				e.printStackTrace();
			}

			this.selector = local;
		}

		public void register(SelectableChannel selectableChannel,
				int selectionKey)
		{
			try
			{
				selectableChannel.register(selector, selectionKey);
			}
			catch (ClosedChannelException e)
			{
				e.printStackTrace();
			}
		}

		public Set<SelectionKey> select()
		{
			Set<SelectionKey> selectedKeys = null;
			try
			{
				this.selector.select();
				selectedKeys = selector.selectedKeys();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			return selectedKeys;
		}
	}

	/**
	 * Patterns : Reactor of Reactor Pattern
	 */
	public static class Reactor
	{
		private Map<SelectableChannel, EventHandlerInterface> map = new HashMap<SelectableChannel, EventHandlerInterface>();
		private SynchEventDemux sync_event_demultiplexer = new SynchEventDemux();

		public void register(SelectableChannel handle, int event_type,
				EventHandlerInterface event_handler)
		{
			map.put(handle, event_handler);
			sync_event_demultiplexer.register(handle, event_type);
		}

		public void run_event_loop()
		{
			while (true)
			{
				Set<SelectionKey> keys = sync_event_demultiplexer.select();
				Iterator<SelectionKey> itr = keys.iterator();
				while (itr.hasNext())
				{
					SelectionKey key = itr.next();
					itr.remove();
					SelectableChannel channel = key.channel();
					if (!channel.isOpen())
					{
						key.cancel();
					}
					EventHandlerInterface event_handler = (EventHandlerInterface) map
							.get(channel);

					event_handler.handleInput(channel, key.interestOps());
				}
			}
		}
	}

	public static void main(String[] args)
	{

		ServerSocketChannel server_handle = null;
		try
		{
			if (args.length < 1)
			{
				System.out.println("Please provide port number.");
				return;
			}

			int port;
			try
			{
				port = Integer.parseInt(args[0]);
			}
			catch (NumberFormatException e)
			{
				System.out
						.println("Please provide valid integer for port number.");
				return;
			}

			server_handle = ServerSocketChannel.open();
			ServerSocket server_socket = server_handle.socket();
			server_socket.bind(new InetSocketAddress(port));

			server_handle.configureBlocking(false);
			Reactor reactor = new Reactor();

			reactor.register(server_handle, SelectionKey.OP_ACCEPT,
					new ServerAcceptor(reactor, server_handle));
			reactor.run_event_loop();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (server_handle != null)
				{
					server_handle.close();
				}
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
