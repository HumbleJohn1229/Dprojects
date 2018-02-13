package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import client.Messages;
import client.Messages.MessageType;

/*접속해오는 사람에게 하나의 쓰레드를 할당해준다.*/
public class ServerMain extends Thread{
	
	private static boolean serverOff; //  서버 끌 때 사용할 플래그 변수
	private static boolean isFirstClient; // 처음 방장을 정할 때
	public ServerMain(){
		serverOff = false;
		isFirstClient = true;
	}
	
	
	@Override
	public void run() {
		ServerSocket serverSocket = null;
		serverOff = false; 
		try {
			serverSocket = new ServerSocket(9001);
			
			System.out.println("\n서버가 시작되었습니다");
			while (!serverOff) {	
				Socket socket = serverSocket.accept();
				Thread member = new PerClientThread(socket);
				if(isFirstClient) {
					((PerClientThread)member).setIsDaeBang();
					isFirstClient = false;
				}
				member.start();
			}
			
			System.out.println("\n서버가 종료되었습니다");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("\n서버가 이미 구동이 되어 있습니다");
			System.out.println("가동중인 서버를 확인해주세요");
		} 

	}
	
	//기능구현
	public void closeServer() {
		serverOff = true;
		PerClientThread.sendAll(new Messages(MessageType.SERVERDOWN));
	}
	
	public static boolean getServerOff() {
		return serverOff;
	}


	public static void setFirstClient(boolean isFirstClient) {
		ServerMain.isFirstClient = isFirstClient;
	}
	
	
}