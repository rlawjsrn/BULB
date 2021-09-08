package co.yedam.board;

import java.util.List;
import java.util.Scanner;

import co.yedam.member.MemberDAO;

public class Main {

	static String loginId = null; //id가 들어가게됨. 공용으로 쓸수있음.

	static Scanner scn = new Scanner(System.in);

	public static void main(String[] args) {
		login();
		while (true) {

			System.out.println("1.글등록 2.글수정 3.글삭제 4.글조회 5.종료");
			int menu = scn.nextInt();
			scn.nextLine();
			if (menu == 1) {
				System.out.println("등록하세요");
				// 글등록
				enrollBoard();
			} else if (menu == 2) {
				System.out.println("수정하세요");
				// 글수정
				modifyBoard();
			} else if (menu == 3) {
				System.out.println("삭제하세요");
				// 글삭제
				removeBoard();
			} else if (menu == 4) {
				System.out.println("조회하세요");
				// 글조회
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
			System.out.println("id를 입력하세요.");
			System.out.println("id :");
			String id = scn.nextLine();

			System.out.println("pw를 입력하세요.");
			System.out.println("pw:");
			String pw = scn.nextLine();

			MemberDAO dao = new MemberDAO();

			if (dao.login(id, pw)) { // dao에 있는 login메소드에 인자값(즉 사용자가 입력한 아디,비번값)을 줌
				System.out.println("입력하신 id가 존재합니다.");
				loginId = id;
				break;
			} else {
//			if (!memberPw.equals("")) {
//				System.out.println("비밀번호가 일치하지 않습니다. 재입력 해주세요");
//			} else {
//				System.out.println("id와 비밀번호가 일치합니다.");
//				break;
//			}
				System.out.println("입력하신 id가 존재하지 않습니다.");
			}
		}

	}

	public static void enrollBoard() {

		System.out.println("글제목을 입력>>");
		String boardTitle = scn.nextLine();
		
		System.out.println("글내용을 입력>>");
		String boardContent = scn.nextLine();

		String boardWriter = loginId;

		Board board = new Board(); // get.set(VO클래스)를 불러(선언), 그리고 값을 담음
		board.setBoardTitle(boardTitle);
		board.setBoardContent(boardContent);
		board.setBoardWriter(boardWriter);

		BoardDAO dao = new BoardDAO();// DAO클래스를 부름.
		dao.insertBoard(board);// DAO클래스의 insertBoard 메소드에 보(VO) 클래스를 담아 보낸다.:인자로 값 뿐만 아니라 클래스도 담아 보낼 수 있는거.
	}

	public static void modifyBoard() {
		System.out.println("글 번호 입력>>");
		int boardNum = scn.nextInt();
		scn.nextLine();

		BoardDAO dao = new BoardDAO();
		Board rboard = dao.getBoard(boardNum);

		System.out.println("글 제목 입력>>");
		String boardTitle = scn.nextLine();
		if (!boardTitle.equals("")) {
			rboard.setBoardTitle(boardTitle);
		}

		System.out.println("글 내용 입력>>");
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
		System.out.println("글 번호 입력>>");
		int boardNum = scn.nextInt();
		scn.nextLine();

		BoardDAO dao = new BoardDAO();
		dao.deleteBoard(boardNum);

	}

	//글 조회
	public static void searchBoard() {
		System.out.println("작성자를 입력>>");
		String boardWriter = scn.nextLine();

		System.out.println("글 제목을 입력>>");
		String boardTitle = scn.nextLine();

		System.out.println("글 번호를 입력>>");
		String boardNum = scn.nextLine();
		boardNum = boardNum.equals("") ? "0" : boardNum;

		Board board = new Board();
		board.setBoardWriter(boardWriter);
		board.setBoardTitle(boardTitle);
		board.setBoardNum(Integer.parseInt(boardNum));

		BoardDAO dao = new BoardDAO();
		
		//게시글 보여주기
		List<Board> list = dao.searchBoard(board);
		for (Board br : list) {  //for반복문인데 board클래스에 있는 컬럼을 한줄씩 보여주게함.
			System.out.println(br.toString()); //br은 데이터 한 줄을 뜻함.(컬럼 한 사이클을 도는 것)
//			if(오리진넘버가 ""이 아니면) {
//				System.out.println("_" + br.toString());
//			}
		}
		//1번게시글
		//	1번댓
		//	1번댓
		//2번게시글	
		
		System.out.println("1.댓글작성 2.종료");
		int menu = scn.nextInt();
		
		if(menu == 1) {
			comment(); //comment메소드로 가세요.(같은 클래스 안에 메소드가 존재할 때 쓰는 방법)
		}if(menu == 2) {
			scn.close();
			System.out.println("end of program");
		}else{
			System.out.println("다시 입력하세요");
		}
	}

	//댓글 작성
	public static void comment() {

		System.out.println("1.댓글작성 2.종료");
		int boardNum = scn.nextInt();  // int boardNum; > 선언
				 	// boardNum = 1; > boardNum의 값이 바뀜
		String boardWriter = loginId;
		
		System.out.println("내용을 입력하세요");
		String boardContent = scn.nextLine();
		
		Board board = new Board();  //VO클래스에 값을 세팅
		board.setBoardNum(boardNum);
		board.setBoardWriter(boardWriter);
		board.setBoardContent(boardContent);
		board.setOriginNum(boardNum); //boardNum의 값을 받기 때문에 originnum xxxx 
		
		BoardDAO boardDao = new BoardDAO();
		boardDao.insertComment(board);
		
		
	}

}
