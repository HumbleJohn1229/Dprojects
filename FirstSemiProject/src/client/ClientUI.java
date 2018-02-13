package client;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import client.Messages.MessageType;

public class ClientUI extends JFrame implements ActionListener {
	
	private ClientSender clientSender;
	private String name;
	private boolean isDaeBang;
	
	//inputs
	private JTextField chatInput;
	private JTextField restaurantInput;
	private JTextField menuInput;
	
	//labels
	private JLabel chatRoomLabel;
	private JLabel userListLabel;
	
	//textareas
	private JTextArea chatSpace;
	private JTextArea nameList;
	private JTextArea menuList;
	
	//submit button
	private JButton foodSubmitBtn;
	private JButton resultBtn;
	
	//scrolls
	private JScrollPane chatSpaceScrl;
	private JScrollPane nameListScrl;
	private JScrollPane menuListScrl;
	


	public ClientUI(Socket socket, String setName) {
		//inputs
		chatInput = new JTextField("");
		restaurantInput = new JTextField("식당이름");
		menuInput = new JTextField("메뉴");
		
		//labels
		chatRoomLabel = new JLabel("채팅방");
		userListLabel = new JLabel("접속 유저");
		
		//textareas
		chatSpace = new JTextArea();
		nameList = new JTextArea();
//		menuList = new JTextArea();
		
		//buttons
		foodSubmitBtn = new JButton("제출");
		resultBtn = new JButton("결과요청");
		
		//scrolls
		chatSpaceScrl = new JScrollPane(chatSpace);
		chatSpaceScrl.getViewport().add(chatSpace);
		nameListScrl = new JScrollPane(nameList);
		menuListScrl = new JScrollPane(menuList);
		
		//container
		Container container = getContentPane();
		container.setLayout(null);
		
		//setFonts and design
		chatInput.setFont(new Font("돋움", Font.PLAIN, 15));
		restaurantInput.setFont(new Font("돋움", Font.HANGING_BASELINE, 13));
		menuInput.setFont(new Font("돋움", Font.HANGING_BASELINE, 13));
		
		chatRoomLabel.setFont(new Font("돋움", Font.BOLD, 15));
		userListLabel.setFont(new Font("돋움", Font.BOLD, 15));
		chatRoomLabel.setForeground(Color.WHITE);
		userListLabel.setForeground(Color.WHITE);
		
		chatSpace.setFont(new Font("돋움", Font.HANGING_BASELINE, 15));
		nameList.setFont(new Font("돋움", Font.HANGING_BASELINE, 15));
		
		foodSubmitBtn.setFont(new Font("돋움", Font.BOLD, 12));
		foodSubmitBtn.setForeground(Color.gray);
		foodSubmitBtn.setBackground(Color.WHITE);
		foodSubmitBtn.setForeground(Color.gray);
		foodSubmitBtn.setBackground(Color.WHITE);
		
		resultBtn.setFont(new Font("돋움", Font.BOLD, 12));
		resultBtn.setBackground(new Color(104, 81, 65));
		resultBtn.setForeground(Color.WHITE);
		resultBtn.setBorderPainted(false); 
		resultBtn.setFocusPainted(false); 

		
		chatSpaceScrl.setFont(new Font("돋움", Font.HANGING_BASELINE, 15));
		nameListScrl.setFont(new Font("돋움", Font.HANGING_BASELINE, 15));		
//		menuListScrl.setFont(new Font("돋움", Font.HANGING_BASELINE, 15));	
		

		// setbounds
		chatInput.setBounds(10, 400, 500, 25); // 채팅입력창
		menuInput.setBounds(520, 80, 100, 25);// 메뉴입력
		restaurantInput.setBounds(520, 50, 100, 25);// 식당입력
		
		userListLabel.setBounds(520, 105, 120, 60); // 유저명단레이블
		chatRoomLabel.setBounds(15, 10, 50, 50); // 채팅방레이블

		foodSubmitBtn.setBounds(623, 50, 60, 55); // 제출버튼
		resultBtn.setBounds(520, 400, 160, 25); // 결과 요청 버튼

		chatSpaceScrl.setBounds(10, 50, 500, 330);// 채팅방
		nameListScrl.setBounds(520, 150, 160, 230);
//		menuListScrl.setBounds(520, 300, 160, 100);
		
		chatSpace.setEditable(false); // 채팅수정금지
		nameList.setEditable(false); // nameList 수정 금지

		//register event listener
		chatSpaceScrl.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {	
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				// TODO Auto-generated method stub
				JScrollBar src = (JScrollBar) e.getSource();
				src.setValue(src.getMaximum());
			}
		});
				
		foodSubmitBtn.addActionListener(this);

		menuInput.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				menuInput.setText("");
			}
		});
		
		restaurantInput.addMouseListener(new MouseAdapter() { 
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				restaurantInput.setText("");
			}
		});
		
		chatInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == e.VK_ENTER) {
					if(clientSender == null) {
						chatSpace.append("\n서버가 꺼져있습니다");
						chatInput.setText("");
					}else {
						Messages message = new Messages(MessageType.ORDINARY, name, chatInput.getText());
						clientSender.sendMessage(message);
						chatInput.setText("");
					}
				}
			}
		});
		
		resultBtn.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if(isDaeBang) {
					clientSender.sendMessage(new Messages(MessageType.SUGGEST));
					return;
				}
				
				chatSpace.append("대빵도 아닌게 감히.. 결과는 대빵만 요청할 수 있습니다\n");
			}
		});
		
		addWindowListener(new WindowAdapter() { // when exit
			@Override
			public void windowClosing(WindowEvent e) {
				if(clientSender!=null) {
					clientSender.sendMessage(new Messages(Messages.MessageType.EXIT, name));
				}
				super.windowClosing(e);
			}

		});
		
		// load on container
		container.add(chatInput);
		container.add(chatSpaceScrl);
		container.add(chatRoomLabel);
		container.add(restaurantInput);
		container.add(foodSubmitBtn);
		container.add(resultBtn);
		container.add(nameListScrl);
		container.add(menuInput);
		container.add(userListLabel);
		
		//container setting
		container.setBackground(new Color(42, 193, 188));
		setTitle("오늘 뭐 먹어?");
		setBounds(400, 400, 700, 485);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		
		//clientSender를 맨 마지막에 생성하는 이유는 혹시 UI가 다 생성이 되기 전에 clientSender 생성자에서 보낸 메세지가 와서 에러가 날까봐이다.
		clientSender = new ClientSender(socket, setName);
		this.name = setName;
		this.isDaeBang = false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {	
		String restraunt = restaurantInput.getText();
		String menu = menuInput.getText();
		
		restaurantInput.setText("제출완료");
		menuInput.setText("제출완료");
		
		FoodDTO food = new FoodDTO();
		food.setRestraunt(restraunt);
		food.setName(menu);
		clientSender.sendMessage(new Messages(Messages.MessageType.FOOD, name, food));
		clientSender.sendFood(food);
		foodSubmitBtn.addActionListener(new submittedCheckFrame());
	}
	
	public void appendToChatSpace(String msg) {
		chatSpace.append(msg + "\n");
	}
	
	public void appendToNameList(String name) {
		nameList.append(name + "\n");
	}

	public void setMyNameToNameList(String name) {
		nameList.setText("★"+name+"\n");
	}

	public void setClientSenderOff() {
		clientSender.sendMessage(new Messages(Messages.MessageType.EXIT));
		clientSender = null;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public ClientSender getClientSender() {
		return clientSender;
	}
	
	public void setIsDaeBang() {
		isDaeBang = true;
	}
}

class submittedCheckFrame extends JFrame implements ActionListener {
	JButton checkedBtn;

	submittedCheckFrame() {
		Container container = getContentPane();
		container.setLayout(null);
		
		//create elements
		JLabel submittedLbl = new JLabel("제출완료");
		checkedBtn = new JButton("확인");
		
		//set fonts
		submittedLbl.setFont(new Font("", Font.HANGING_BASELINE, 15));
		checkedBtn.setFont(new Font("", Font.HANGING_BASELINE, 15));
		
		//set bounds and design
		submittedLbl.setBounds(40, 20, 80, 20);
		submittedLbl.setForeground(Color.WHITE);
		checkedBtn.setBounds(30, 50, 80, 20);
		//register events
		checkedBtn.addActionListener(this);
		
		//load on container
		container.add(submittedLbl);
		container.add(checkedBtn);

		//container settings
		container.setBackground(new Color(42, 193, 188));
		setBounds(700, 500, 130, 130);
		setTitle("제출 완료");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		
					
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		dispose(); // 창닫기 메서드
	}
	

	
}