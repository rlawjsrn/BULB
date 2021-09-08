package co.yedam.board;

import java.util.List;
import java.util.Scanner;

import co.yedam.member.MemberDAO;

public class Main {

	static String loginId = null; //id�� ���Ե�. �������� ��������.

	static Scanner scn = new Scanner(System.in);

	public static void main(String[] args) {
		login();
		while (true) {

			System.out.println("1.�۵�� 2.�ۼ��� 3.�ۻ��� 4.����ȸ 5.����");
			int menu = scn.nextInt();
			scn.nextLine();
			if (menu == 1) {
				System.out.println("����ϼ���");
				// �۵��
				enrollBoard();
			} else if (menu == 2) {
				System.out.println("�����ϼ���");
				// �ۼ���
				modifyBoard();
			} else if (menu == 3) {
				System.out.println("�����ϼ���");
				// �ۻ���
				removeBoard();
			} else if (menu == 4) {
				System.out.println("��ȸ�ϼ���");
				// ����ȸ
				searchBoard();
			} else if (menu == 5) {
				break;
			}
		}
		scn.close();
		System.out.println("end of program");
	}// end of main()

	public static void login() {

		while (true) {
			System.out.println("id�� �Է��ϼ���.");
			System.out.println("id :");
			String id = scn.nextLine();

			System.out.println("pw�� �Է��ϼ���.");
			System.out.println("pw:");
			String pw = scn.nextLine();

			MemberDAO dao = new MemberDAO();

			if (dao.login(id, pw)) { // dao�� �ִ� login�޼ҵ忡 ���ڰ�(�� ����ڰ� �Է��� �Ƶ�,�����)�� ��
				System.out.println("�Է��Ͻ� id�� �����մϴ�.");
				loginId = id;
				break;
			} else {
//			if (!memberPw.equals("")) {
//				System.out.println("��й�ȣ�� ��ġ���� �ʽ��ϴ�. ���Է� ���ּ���");
//			} else {
//				System.out.println("id�� ��й�ȣ�� ��ġ�մϴ�.");
//				break;
//			}
				System.out.println("�Է��Ͻ� id�� �������� �ʽ��ϴ�.");
			}
		}

	}

	public static void enrollBoard() {

		System.out.println("�������� �Է�>>");
		String boardTitle = scn.nextLine();
		
		System.out.println("�۳����� �Է�>>");
		String boardContent = scn.nextLine();

		String boardWriter = loginId;

		Board board = new Board(); // get.set(VOŬ����)�� �ҷ�(����), �׸��� ���� ����
		board.setBoardTitle(boardTitle);
		board.setBoardContent(boardContent);
		board.setBoardWriter(boardWriter);

		BoardDAO dao = new BoardDAO();// DAOŬ������ �θ�.
		dao.insertBoard(board);// DAOŬ������ insertBoard �޼ҵ忡 ��(VO) Ŭ������ ��� ������.:���ڷ� �� �Ӹ� �ƴ϶� Ŭ������ ��� ���� �� �ִ°�.
	}

	public static void modifyBoard() {
		System.out.println("�� ��ȣ �Է�>>");
		int boardNum = scn.nextInt();
		scn.nextLine();

		BoardDAO dao = new BoardDAO();
		Board rboard = dao.getBoard(boardNum);

		System.out.println("�� ���� �Է�>>");
		String boardTitle = scn.nextLine();
		if (!boardTitle.equals("")) {
			rboard.setBoardTitle(boardTitle);
		}

		System.out.println("�� ���� �Է�>>");
		String boardContent = scn.nextLine();
		if (!boardContent.equals("")) {
			rboard.setBoardContent(boardContent);
		}

		String boardWriter = loginId;
		if (!boardWriter.equals("")) {
			rboard.setBoardWriter(boardWriter);
		}

		dao.updateBoard(rboard);

	}

	public static void removeBoard() {
		System.out.println("�� ��ȣ �Է�>>");
		int boardNum = scn.nextInt();
		scn.nextLine();

		BoardDAO dao = new BoardDAO();
		dao.deleteBoard(boardNum);

	}

	//�� ��ȸ
	public static void searchBoard() {
		System.out.println("�ۼ��ڸ� �Է�>>");
		String boardWriter = scn.nextLine();

		System.out.println("�� ������ �Է�>>");
		String boardTitle = scn.nextLine();

		System.out.println("�� ��ȣ�� �Է�>>");
		String boardNum = scn.nextLine();
		boardNum = boardNum.equals("") ? "0" : boardNum;

		Board board = new Board();
		board.setBoardWriter(boardWriter);
		board.setBoardTitle(boardTitle);
		board.setBoardNum(Integer.parseInt(boardNum));

		BoardDAO dao = new BoardDAO();
		
		//�Խñ� �����ֱ�
		List<Board> list = dao.searchBoard(board);
		for (Board br : list) {  //for�ݺ����ε� boardŬ������ �ִ� �÷��� ���پ� �����ְ���.
			System.out.println(br.toString()); //br�� ������ �� ���� ����.(�÷� �� ����Ŭ�� ���� ��)
//			if(�������ѹ��� ""�� �ƴϸ�) {
//				System.out.println("_" + br.toString());
//			}
		}
		//1���Խñ�
		//	1����
		//	1����
		//2���Խñ�	
		
		System.out.println("1.����ۼ� 2.����");
		int menu = scn.nextInt();
		
		if(menu == 1) {
			comment(); //comment�޼ҵ�� ������.(���� Ŭ���� �ȿ� �޼ҵ尡 ������ �� ���� ���)
		}if(menu == 2) {
			scn.close();
			System.out.println("end of program");
		}else{
			System.out.println("�ٽ� �Է��ϼ���");
		}
	}

	//��� �ۼ�
	public static void comment() {

		System.out.println("1.����ۼ� 2.����");
		int boardNum = scn.nextInt();  // int boardNum; > ����
				 	// boardNum = 1; > boardNum�� ���� �ٲ�
		String boardWriter = loginId;
		
		System.out.println("������ �Է��ϼ���");
		String boardContent = scn.nextLine();
		
		Board board = new Board();  //VOŬ������ ���� ����
		board.setBoardNum(boardNum);
		board.setBoardWriter(boardWriter);
		board.setBoardContent(boardContent);
		board.setOriginNum(boardNum); //boardNum�� ���� �ޱ� ������ originnum xxxx 
		
		BoardDAO boardDao = new BoardDAO();
		boardDao.insertComment(board);
		
		
	}

}
