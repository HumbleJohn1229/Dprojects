package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import client.Messages.MessageType;

public class ClientReceiver extends Thread {
	private Socket socket; // 소켓
	private LogInUI logInUI; // 접속 UI, 이 UI 안에 들어가면 ClientUI가 있고, 그 ClientUI 안에 ClientSender가 있다.
	private List foods = new ArrayList<FoodDTO>();
	private boolean isChief;

	public ClientReceiver(Socket socket) {
		this.socket = socket;
		logInUI = new LogInUI(socket);
		isChief = false;
	}

	@Override
	public void run() {
		try {
			//메세지를 서버로부터 읽어 올 Reader 변수 선언 및 메모리 할당 
			ObjectInputStream reader = new ObjectInputStream(socket.getInputStream());
			List<String> currentUserList = null;
			while (true) {
				Messages message = null;
				try {
					message = (Messages)reader.readObject();
					
					if(message.getMessageType().equals(MessageType.EXITEARLY)) {
						reader.close();
						return;
					} else if(message.getMessageType().equals(MessageType.RESULT)) {
						new ResultUI(message.getFullMsg());
						message.setFullMsg("오늘의 메뉴 : " + message.getFullMsg());
					}
					
					System.out.println(message.getFullMsg());
					logInUI.appendToChatSpace(message.getFullMsg());
					
					if(message.getMessageType().equals(MessageType.ENTER)||message.getMessageType().equals(MessageType.EXIT)||message.getMessageType().equals(MessageType.CHIEF)) {
						Object gotList = reader.readObject();
						if(gotList instanceof List<?>) {
							currentUserList = (List<String>)gotList;
							logInUI.appendToNameList(currentUserList);
						}
					} else if(message.getMessageType().equals(MessageType.FOOD)) {
						Object food = reader.readObject();
						if(food instanceof FoodDTO) {
							foods.add(food);
						}
					} else if(message.getMessageType().equals(MessageType.ELECTED)) {
						logInUI.getClientSender().sendMessage(new Messages(MessageType.CHIEF, logInUI.getName(), "여기는 이제 내가 접수한다"));
						logInUI.setClientName();
						logInUI.setClientDaeBang();
					}
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
