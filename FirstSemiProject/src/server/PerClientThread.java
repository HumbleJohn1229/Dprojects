package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import client.FoodDTO;
import client.Messages;
import client.Messages.MessageType;

public class PerClientThread extends Thread {
	static List<ObjectOutputStream> list = Collections.synchronizedList(new ArrayList<ObjectOutputStream>());
	static List<String> currentUserList = Collections.synchronizedList(new ArrayList<String>());
	static List<FoodDTO> foods = Collections.synchronizedList(new ArrayList<FoodDTO>());
	private boolean isDaeBang;
	private boolean isDaeBangSent;
	private Socket socket;
	private ObjectOutputStream out;
	
	public PerClientThread(Socket socket) {
		this.socket = socket;
		isDaeBang = false;
		isDaeBangSent = false;
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			list.add(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		String name = null;
		try {
			ObjectInputStream reader = new ObjectInputStream(socket.getInputStream());

			while (true) {

				if (ServerMain.getServerOff()) {
					sendAll(new Messages(MessageType.SERVERDOWN));
					return;
				}

				Messages message = null;

				try {
					message = (Messages) reader.readObject();
					
					if(message.getMessageType().equals(MessageType.EXITEARLY)) {
						out.writeObject(new Messages(MessageType.EXITEARLY));
						out.flush();
						out.close();
						reader.close();
						return;
					};
					
					System.out.println(message.getFullMsg());
					sendAll(message);
					
					if (message.getMessageType().equals(MessageType.ENTER)) {
						currentUserList.add(message.getName());
						sendAll(new ArrayList<String>(currentUserList));
					} else if(message.getMessageType().equals(MessageType.EXIT)) {
						currentUserList.remove(message.getName());
						list.remove(out);
						sendAll(new ArrayList<String>(currentUserList));
						
						if(isDaeBang) {
							selectChief();
						}
						return;
					} else if(message.getMessageType().equals(MessageType.FOOD)) {
						Object food = reader.readObject();
						if(food instanceof FoodDTO) {
							foods.add((FoodDTO)food);
							sendAll(food);
						}
					} else if(message.getMessageType().equals(MessageType.CHIEF)) {
						isDaeBang = true;
						isDaeBangSent = true;
						currentUserList.remove(message.getName());
						currentUserList.add(0, message.getName()+"(대빵)");
						sendAll(currentUserList);
					} else if(isDaeBang&&message.getMessageType().equals(MessageType.SUGGEST)) {
						new ServerController().sendResult();
					}
					
					if(!isDaeBangSent&&isDaeBang) {
						out.writeObject(new Messages(MessageType.ELECTED));
						isDaeBangSent = true;
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
	}

	public static void sendAll (Object msg) {
		for (ObjectOutputStream e : list) {
			try {
				e.writeObject(msg);
				e.flush();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public void selectChief() {
		if(list.size()>0) {
			ObjectOutputStream messenger = list.get((int) (Math.random()*list.size()));
			try {
				messenger.writeObject(new Messages(MessageType.ELECTED));
				messenger.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			ServerMain.setFirstClient(true);
		}
	}
	
	public void setIsDaeBang() {
		this.isDaeBang = true;
	}
}
