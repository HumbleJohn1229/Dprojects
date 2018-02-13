package client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSender {
	private Socket socket; // 소켓
	private String name; // 사용자 이름
	private ObjectOutputStream out; // 서버에 보낼 writer

	public ClientSender(Socket socket, String name) {
		this.socket = socket;
		this.name = name;
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			Messages msg = new Messages(Messages.MessageType.ENTER, name);
			out.writeObject(msg);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendMessage(Messages message) {
		try {
			System.out.println(message.getFullMsg());
			out.writeObject(message);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendFood(FoodDTO food) {
		try {
			out.writeObject(food);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
