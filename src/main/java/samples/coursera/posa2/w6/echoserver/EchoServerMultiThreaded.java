package samples.coursera.posa2.w6.echoserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class EchoServerMultiThreaded {
	private static Executor executor = new ThreadPoolExecutor(10, 10, 10, TimeUnit.SECONDS,new LinkedBlockingQueue());
	

	public static void startServer() {
		ServerSocket socket = null;
		try {
			socket = new ServerSocket(13786);
			
			while (true) {
				Socket peer = socket.accept();
				executor.execute(new Task(peer));
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
	
	static class Task implements Runnable{
		private final Socket peer;
		
		Task(Socket peer){
			this.peer = peer;
		}
		
		@Override
		public void run() {		
			try {
				System.out.println("Connected to " + peer);
				BufferedReader inputStream = new BufferedReader(new InputStreamReader(peer.getInputStream()));
				do {
					String line = inputStream.readLine();
					if (line == null) {
						break;
					}
					System.out.println(line);
				} while (true);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		
	}
	public static void main(String[] args) {
		
		startServer();
	}

}
