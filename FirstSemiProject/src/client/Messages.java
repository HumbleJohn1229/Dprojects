package client;

import java.io.Serializable;

import client.FoodDTO;

public class Messages implements Serializable{
	private MessageType messageType;
	private String name;
	private String fullMsg;
	static final long serialVersionUID = 42L;
	
	public Messages(MessageType messageType, String name, FoodDTO food) {
		this.messageType = messageType;
		this.name = name;
		fullMsg = name+"님이 "+food.getRestraunt()+"의 "+food.getName()+messageType.getBasicMsg();
	}
	
	public Messages(MessageType messageType, String name, String msg) {
		this.messageType = messageType;
		this.name = name;
		this.fullMsg = name + messageType.getBasicMsg() + msg;
	}

	public Messages(MessageType messageType, String name) {
		this(messageType, name, "");
	}

	public Messages(MessageType messageType) {
		this(messageType, "", "");
	}

	public Messages(MessageType messageType, FoodDTO food) {
		this.messageType = messageType;
		this.fullMsg = food.getRestraunt()+"의 "+food.getName();
	}
	public MessageType getMessageType() {
		return messageType;
	}

	public String getName() {
		return name;
	}

	public String getFullMsg() {
		return fullMsg;
	}
	
	public void setFullMsg(String fullMsg) {
		this.fullMsg = fullMsg;
	}

	public enum MessageType {
		EXITEARLY(""), EXIT("님이 퇴장하였습니다"), SERVERDOWN("서버가 종료되었습니다"), ENTER("님이 입장하였습니다"), FOOD("를 제출하였습니다"), RESULT(""), 
		ORDINARY(" : "), CHIEF("☆"), ELECTED("당신이 이제 대빵입니다"), SUGGEST("방장님이 결과를 요청했습니다.");

		private String basicMsg;

		MessageType(String basicMsg) {
			this.basicMsg = basicMsg;
		}

		public String getBasicMsg() {
			return basicMsg;
		}
	}
}
