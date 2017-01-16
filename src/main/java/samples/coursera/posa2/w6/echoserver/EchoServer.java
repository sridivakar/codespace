package samples.coursera.posa2.w6.echoserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

	public static void startServer() {
		ServerSocket socket = null;
		try {
			socket = new ServerSocket(13786);
			
			while (true) {
				Socket peer = socket.accept();
				System.out.println("Connected to " + peer);
				BufferedReader inputStream = new BufferedReader(new InputStreamReader(peer.getInputStream()));
				do {
					String line = inputStream.readLine();
					if (line == null) {
						break;
					}
					System.out.println(line);
				} while (true);
				
			}
		} catch (IOException e) {			
			e.printStackTrace();
		} finally {
			try {
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {				
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		
		startServer();
	}

}
