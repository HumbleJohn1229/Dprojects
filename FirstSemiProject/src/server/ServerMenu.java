package server;

import java.util.Scanner;

public class ServerMenu {

	Scanner input; //
	ServerController controller;
	int menuNum; // 메뉴 전환을 위해 쓰일 메뉴번호
	
	ServerMenu() {
		input = new Scanner(System.in);
		controller = new ServerController();
		menuNum = 1;
	}

	public void menuSwitcher() {
		while (true) {
			switch (menuNum) {
			case 0:
				// 서버 종료 기능
				controller.offServer();
				System.out.println("프로그램이 종료되었습니다");
				return;
			case 1:
				basicMenu();
				break;
			case 2:
				serverMenu();
				break;
			case 3:
				randomResultMenu();
				break;
			case 4:
				dbMenu();
				break;
			default:
				System.out.println("번호를 잘못입력되었습니다");
				System.out.println("기본 메뉴로 돌아갑니다");
				menuNum = 1;
				break;
			}
		}
	}

	public void basicMenu() {

		System.out.println("0. 프로그램 종료");
		System.out.println("1. 서버 제어 메뉴");
		System.out.println("2. 랜덤 결과 관련 메뉴");
		System.out.println("3. 데이터 베이스 제어 메뉴");
		System.out.print("실행할 메뉴 번호를 입력하세요>>");

		int menuAgain; // 메뉴 잘못 입력시 메뉴를 다시 실행하기 위해 선언한 변수
		do {
			menuAgain = 0; // 메뉴 어게인 0으로 초기화
			int choice = input.nextInt();
			switch (choice) {
			case 0:
				menuNum = 0;
				break;
			case 1:
				menuNum = 2;
				break;
			case 2:
				menuNum = 3;
				break;
			case 3:
				menuNum = 4;
				break;
			default:
				System.out.println("번호를 잘못입력하셨습니다");
				System.out.println("다시 입력해주세요>>");
				menuAgain = 999; // 0이 아닌 임의의 숫자
				break;
			}
		} while (menuAgain == 999);

	}


	public void serverMenu() {
		System.out.println("0. 프로그램 종료");
		System.out.println("1. 서버 시작");
		System.out.println("2. 서버 종료");
		System.out.println("10.기본 메뉴로 돌아가기");
		System.out.print("실행할 메뉴 번호를 입력하세요>>");

		int menuAgain; // 메뉴 잘못 입력시 메뉴를 다시 실행하기 위해 선언한 변수
		do {
			menuAgain = 0; // 메뉴 어게인 0으로 초기화
			int choice = input.nextInt();
			switch (choice) {
			case 0:
				menuNum = 0;
				break;
			case 1:
				controller.startServer();
				break;
			case 2:
				controller.offServer();
				break;
			case 10:
				menuNum =10;
				break;
			default:
				System.out.println("번호를 잘못입력하셨습니다");
				System.out.println("다시 입력해주세요>>");
				menuAgain = 999; // 0이 아닌 임의의 숫자
				break;
			}
		} while (menuAgain == 999);
	}

	public void randomResultMenu() {
		System.out.println("0. 프로그램 종료");
		System.out.println("1. 결과 보내주기");
		System.out.println("10.기본 메뉴로 돌아가기");
		System.out.print("실행할 메뉴 번호를 입력하세요>>");

		int menuAgain; // 메뉴 잘못 입력시 메뉴를 다시 실행하기 위해 선언한 변수
		do {
			menuAgain = 0; // 메뉴 어게인 0으로 초기화
			int choice = input.nextInt();
			switch (choice) {
			case 0:
				menuNum = 0;
				break;
			case 1:
				controller.sendResult();
				break;

			case 10:
				menuNum =10;
				break;
			default:
				System.out.println("번호를 잘못입력하셨습니다");
				System.out.println("다시 입력해주세요>>");
				menuAgain = 999; // 0이 아닌 임의의 숫자
				break;
			}
		} while (menuAgain == 999);
	}

	public void dbMenu() {
		System.out.println("0. 프로그램 종료");
		System.out.println("1. insertAll");
		System.out.println("2. update");
		System.out.println("3. selectAll");
		System.out.println("4. delete");
		System.out.println("5. 지금까지 받은 메뉴 저장");
		System.out.println("10. 기본메뉴로 돌아가기");
		System.out.println("20. 최초 메뉴 테이블 생성");	
		System.out.print("실행할 메뉴 번호를 입력하세요>>");

		int menuAgain; // 메뉴 잘못 입력시 메뉴를 다시 실행하기 위해 선언한 변수
		do {
			menuAgain = 0; // 메뉴 어게인 0으로 초기화
			int choice = input.nextInt();
			switch (choice) {
			case 0:
				menuNum = 0;
				break;
			case 1:
				controller.insertAll();
				break;			
			case 2:
				controller.updateDB();
				break;
			case 3:
				controller.selectAll();
				break;
			case 4:
				controller.deleteDB();
				break;
			case 5:
				controller.insertAll();
				break;
			case 10:
				menuNum =10;
				break;
			case 20:
				controller.createDB();
				break;	
			default:
				System.out.println("번호를 잘못입력하셨습니다");
				System.out.println("다시 입력해주세요>>");
				menuAgain = 999; // 0이 아닌 임의의 숫자
				break;
			}
		} while (menuAgain == 999);
	}
}