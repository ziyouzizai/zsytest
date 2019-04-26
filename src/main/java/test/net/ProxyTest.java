package test.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ProxyTest {
	public static void main(String[] args) throws IOException {
		ServerSocket ss = new ServerSocket(10010);
		System.out.println("server startup sucessfully");
		long count = 0;
		while(true) {
			Socket s = ss.accept();
			System.out.println("acceptor:" + (++count));
			new Thread(new Task(s)).start();
		}
		
		
	}
}

class Task implements Runnable{
	private Socket socket = null;
	public Task(Socket socket) {
		this.socket = socket;
	}
	public void run() {
		try {
			InputStream input = this.socket.getInputStream();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(input));
			String line = null;
			while((line = br.readLine()) != null) {
				System.out.println(line);
				
				if(line != null && line.trim().toLowerCase().equals("quit")) {
					break;
				}
			}
			socket.shutdownInput();
			socket.shutdownOutput();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}