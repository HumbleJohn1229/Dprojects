package server;

import java.util.Scanner;

import client.FoodDAO;
import client.FoodDTO;
import client.Messages;
import client.Messages.MessageType;

public class ServerController {
	private ServerMain serverMain;
	private RandomChoice randomChoice;
	private FoodDAO foodDao;
	private Scanner input;

	public ServerController() {
		serverMain = new ServerMain();
		foodDao = new FoodDAO();
		randomChoice = new RandomChoice(foodDao.selectYesterday());
		input = new Scanner(System.in);
	}

	public void sendResult() {
		FoodDTO selected = randomChoice.pickOne();
		if (selected != null) {
			PerClientThread.sendAll(new Messages(MessageType.RESULT, selected));
			foodDao.insertResult(selected);
		} else {
			PerClientThread.sendAll(new Messages(MessageType.RESULT, "제출된 것이 없습니다"));
		}
	}

	public void startServer() {
		serverMain.start();

	}

	public void offServer() {
		serverMain.closeServer();
	}

	public void insertResult(FoodDTO dto) {
		foodDao.insertResult(dto);
	}

	public void updateDB() {
		FoodDTO dto = new FoodDTO();
		System.out.print("수정할 메뉴 : ");
		dto.setName(input.nextLine());
		System.out.print("새로운 식당 : ");
		dto.setRestraunt(input.nextLine());

		foodDao.update(dto);
	}

	public void deleteDB() {
		System.out.print("삭제할 식당 : ");
		foodDao.delete(input.nextLine());
	}

	public void selectAll() {
		foodDao.select();
	}

	public void createDB() {
		foodDao.createTable();
	}

	public void insertAll() {
		for (FoodDTO e : PerClientThread.foods) {
			foodDao.insertSubmitted(e);
		}
	}

}