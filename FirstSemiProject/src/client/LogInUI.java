package client;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import client.Messages.MessageType;

public class LogInUI extends JFrame implements ActionListener {
	private JTextField putNameField;
	private String name;
	private Socket socket;
	private ClientUI clientUI; // ClientReceiver에서도(외부클래스에서도) 접근하도록 하기 위해 ClientUI를 필드로 선언
	private boolean clientUIOn = false;
	
	/**
	 * @param socket
	 */
	LogInUI(Socket socket){
		//ClientUI 생성시 매개변수로 넣어주기 위한 소켓
		this.socket = socket;
		
		// 라벨, 텍스트빌드, 버튼 선언
		JLabel nameLabel = new JLabel("이름 입력");
		putNameField = new JTextField(8);
		JButton submitButton = new JButton("접속");
		
		//컨테이너 객체 생성 및 레이아웃 설정
		Container container = getContentPane();
		setLayout(null);
		
		//컴포넌트 위치
		nameLabel.setBounds(65,5,80,50);
		putNameField.setBounds(25,60,80,25);
		submitButton.setBounds(110,60,60,25);
		
		//컴포넌트 디자인
		nameLabel.setForeground(Color.WHITE);
		submitButton.setBackground(Color.white);
		submitButton.setForeground(new Color(42, 193, 188));
		putNameField.setBackground(Color.white);
		
		//컨테이너에 컴포넌트 탑재
		container.add(nameLabel);
		container.add(putNameField);
		container.add(submitButton);
		
		//컨테이너에 색 입히기
		container.setBackground(new Color(42, 193, 188));
	
		//액션리스터 등록
		putNameField.addActionListener(this);
		submitButton.addActionListener(this);
		
		
		//기타설정
		setTitle("접속");
		setBounds(800,500,200,150);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
					ObjectOutputStream out;
					try {
						out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
						out.writeObject(new Messages(MessageType.EXITEARLY));
						out.flush();
						Thread.sleep(200);
						out.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
				
					
				super.windowClosing(e);
			}
			
		});
		
	}

	@Override
	public void actionPerformed(ActionEvent arg) { // 버튼 혹은 텍스트 필드에서 엔터를 쳤을 때 수행 됨.
		name = putNameField.getText(); //  입력된 값을 이름으로 처리한다.
		putNameField.setText(""); // 텍스트 필드에서 값을 가져오고, 빈 공간으로 만들어 준다.
		
		clientUIOn = true;
		clientUI = new ClientUI(socket, name); // ClientUI를 만들고, 생성자에 방금 입력받은 이름을 넣는다.
		dispose(); // 접속이 되었으므로 창을 닫아준다.
		
	}
	
	//ClientUI의 첫번째 TextArea에 값을 넣어주는 기능 
	public void appendToChatSpace(String msg) {
		clientUI.appendToChatSpace(msg);;
	}
	
	//ClientUI의 nameList에 현재 채팅 참여한 사람 세팅해주기	
	public void appendToNameList(List<String> list) {
		setMyNameToNameList();
		list.remove(name);
		System.out.println("logInUI 안에서 리스트로 이름공간에 추가해주기 전");
		for(String name : list) {
			clientUI.appendToNameList(name);
		}
	}
	
	//ClientUI의 nameList에 자신의 이름을 세팅해주는 기능	
	public void setMyNameToNameList() {
		clientUI.setMyNameToNameList(name);
	}
	
	public String getName() {
		return name;
	}
	
	public void setClientSenderOff() {
		clientUI.setClientSenderOff();
	}
	
	public boolean getClientUIOn() {
		return clientUIOn;
	}
	
	public void setClientName() {
		String newName = name+"(대빵)";
		clientUI.setName(newName);
		this.name = newName;
	}
	
	public void setClientDaeBang() {
		clientUI.setIsDaeBang();
	}
	public ClientSender getClientSender() {
		return clientUI.getClientSender();
	}
}