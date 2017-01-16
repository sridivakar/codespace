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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ProgramForAnna
{

	/**
	 * "Half-sync" implementation of Half-Sync/Async Pattern
	 */
	static class HalfSyncPool
	{
		private Executor tp;

		public HalfSyncPool(int _no_of_threads_in_pool)
		{
			tp = Executors.newFixedThreadPool(_no_of_threads_in_pool);
		}

		public void put(SyncTask task)
		{
			tp.execute(task);
		}
	}

	static class SyncTask implements Runnable
	{
		private SocketChannel channel;
		private String msg;

		public SyncTask(SocketChannel _client_channel, String _client_msg)
		{
			this.msg = _client_msg;
			this.channel = _client_channel;
		}

		@Override
		public void run()
		{
			try
			{
				ByteBuffer buffer = ByteBuffer.allocate(2048);

				// First sends back to the client the thread id
				long t_id = Thread.currentThread().getId();
				String thread_id = "t_id : " + t_id + " ";
				buffer.put(thread_id.getBytes());
				buffer.flip();

				this.channel.write(buffer);
				buffer.clear();

				// Then send back the client's input
				buffer.put(msg.getBytes());
				buffer.flip();
				this.channel.write(buffer);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	static interface EventHandlerInterface
	{

		public void handleInput(SelectableChannel _handle, int _event_type);

		public SelectableChannel getHandle();
	}

	/**
	 * Patterns : Acceptor of Acceptor-Connector Pattern
	 */
	static class ServerAcceptor implements EventHandlerInterface
	{
		private ServerSocketChannel handle;
		private Reactor reactor;
		private HalfSyncPool half_sync_pool;

		public ServerAcceptor(Reactor _reactor, ServerSocketChannel _handle,
				HalfSyncPool _half_sync_pool)
		{
			super();
			this.reactor = _reactor;
			this.handle = _handle;
			this.half_sync_pool = _half_sync_pool;
		}

		@Override
		public void handleInput(SelectableChannel _channel, int _event_type)
		{
			if ((_event_type & SelectionKey.OP_ACCEPT) == 0
					|| !(_channel instanceof ServerSocketChannel))
			{
				return;
			}

			ServerSocketChannel serverChannel = (ServerSocketChannel) _channel;
			try
			{
				SocketChannel client_handle = serverChannel.accept();
				client_handle.configureBlocking(false);
				EchoServerHandler echoServerHandler = new EchoServerHandler(
						reactor, client_handle, half_sync_pool);
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
	 * Patterns : Concrete Event Handler in Reactor
	 * 
	 * : Half-Async implementation of Half-Sync/Async Pattern
	 */
	static class EchoServerHandler implements EventHandlerInterface
	{
		private SocketChannel handle;
		private HalfSyncPool half_sync_pool;

		public EchoServerHandler(Reactor _reactor, SocketChannel _handle,
				HalfSyncPool _half_sync_pool)
		{
			super();
			this.handle = _handle;
			this.half_sync_pool = _half_sync_pool;
		}

		@Override
		public void handleInput(SelectableChannel _channel, int _event_type)
		{
			if ((_event_type & SelectionKey.OP_READ) == 0
					|| !(_channel instanceof SocketChannel))
			{
				return;
			}

			SocketChannel client_channel = (SocketChannel) _channel;
			ByteBuffer buffer = ByteBuffer.allocate(2048);
			try
			{
				int read_count = client_channel.read(buffer);

				if (read_count == -1)
				{
					return;
				}

				buffer.flip();

				String client_msg = new String(buffer.array());
				if (client_msg != null && client_msg.trim().length() > 0)
				{
					half_sync_pool
							.put(new SyncTask(client_channel, client_msg));
				}

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

		public void register(SelectableChannel _selectableChannel,
				int _selectionKey)
		{
			try
			{
				_selectableChannel.register(selector, _selectionKey);
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

		public void register(SelectableChannel _handle, int _event_type,
				EventHandlerInterface _event_handler)
		{
			map.put(_handle, _event_handler);
			sync_event_demultiplexer.register(_handle, _event_type);
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

			int no_of_threads_in_pool = 10;
			HalfSyncPool half_sync_pool = new HalfSyncPool(
					no_of_threads_in_pool);
			reactor.register(server_handle, SelectionKey.OP_ACCEPT,
					new ServerAcceptor(reactor, server_handle, half_sync_pool));
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
