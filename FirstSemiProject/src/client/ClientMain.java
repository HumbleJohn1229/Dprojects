package client;

import java.io.IOException;
import java.net.Socket;

public class ClientMain {
	public static void main(String[] args) {
		Socket socket;
			
		try {
			//서버와 연결할 소켓 선언
			socket = new Socket("192.168.0.27",9001);
			
			// 서버에 정보를 보내고 받을 쓰레드 선언
			Thread receiverThread = new ClientReceiver(socket);
			
			//쓰레드 시작
			receiverThread.start();

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}