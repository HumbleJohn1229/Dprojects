package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ResultUI extends JFrame implements ActionListener{
	
	public ResultUI(String menu){
		JLabel resultfront = new JLabel("오늘의 메뉴");
		JLabel resultbody = new JLabel(menu);
		JButton jb1 = new JButton("가즈아");
		
		Container container = getContentPane();
		
		//set design
		resultfront.setForeground(new Color(16, 55, 80));
		resultbody.setForeground(Color.WHITE);
		
		jb1.setForeground(Color.WHITE);
		jb1.setBackground(new Color(104, 81, 65));
		jb1.setBorderPainted(false); 
		jb1.setFocusPainted(false); 
//		jb1.setContentAreaFilled(false);
		
		container.setBackground(new Color(42, 193, 188));
		
		//fonts
		resultfront.setFont(new Font("굴림", Font.BOLD, 12));
		resultbody.setFont(new Font("굴림", Font.BOLD, 15));
		
		//load on container
		container.add(BorderLayout.NORTH, resultfront);
		container.add(BorderLayout.CENTER, resultbody);
		container.add(BorderLayout.SOUTH, jb1);	

		
		//add actionListener
		jb1.addActionListener(this);
		
		setTitle("이거 먹어");
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setBounds(800,500,200,110);
		setVisible(true);
		setResizable(false);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		dispose();
	}
}