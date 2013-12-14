package samples.coursera.posa2.w6.echoserver;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class EchoServerChannel {

    public static void startServer() {
	try {
	    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

	    ServerSocket serverSocket = serverSocketChannel.socket();
	    serverSocket.bind(new InetSocketAddress(13786));

	    serverSocketChannel.configureBlocking(false);

	    Selector selector = Selector.open();

	    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

	    while (true) {
		selector.select();
		Set<SelectionKey> selectedKeys = selector.selectedKeys();

		Iterator<SelectionKey> iterator = selectedKeys.iterator();
		while (iterator.hasNext()) {
		    SelectionKey selectedKey = iterator.next();
		    iterator.remove();

		    if (selectedKey.isAcceptable()) {
			ServerSocketChannel server = (ServerSocketChannel) selectedKey.channel();
			SocketChannel clientChannel = server.accept();
			clientChannel.configureBlocking(false);
			clientChannel.register(selector, SelectionKey.OP_READ);
		    } else if (selectedKey.isReadable()) {
			SocketChannel clientChannel = (SocketChannel) selectedKey.channel();
			ByteBuffer buffer = ByteBuffer.allocate(2048);
			clientChannel.read(buffer);

			buffer.flip();
			selectedKey.interestOps(SelectionKey.OP_WRITE);
			selectedKey.attach(buffer);
		    } else if (selectedKey.isWritable()) {
			SocketChannel clientChannel = (SocketChannel) selectedKey.channel();
			ByteBuffer buffer = (ByteBuffer) selectedKey.attachment();
			if (buffer.hasRemaining()) {
			    clientChannel.write(buffer);
			} else {
			    selectedKey.interestOps(SelectionKey.OP_READ);
			}
		    } else {
			System.out.println(selectedKey);
		    }
		}
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public static void main(String[] args) {

	startServer();
    }

}
