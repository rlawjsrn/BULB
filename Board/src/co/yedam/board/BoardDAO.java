package co.yedam.board;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO extends DAO {

	public void deleteBoard(int board) {
		String sql = "delete from board where board_num=? ";
		connect();

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, board);

			int r = psmt.executeUpdate();
			System.out.println("삭제완료:" + r);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

	}

	public void updateBoard(Board board) {
		String sql = "update board set board_title=?, board_writer=?, "//
				+ "board_createdate=sysdate, board_content=? "//
				+ "where board_num=?";
		connect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, board.getBoardTitle());
			psmt.setString(2, board.getBoardWriter());
			psmt.setString(3, board.getBoardContent());
			psmt.setInt(4, board.getBoardNum());

			int r = psmt.executeUpdate();
			System.out.println("수정완료" + r);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	public Board getBoard(int boardNum) {
		String sql = "select * from board where board_num = ?";
		Board board = null;
		connect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, boardNum);
			rs = psmt.executeQuery();
			if (rs.next()) {
				board = new Board();
				board.setBoardWriter(rs.getString("board_writer"));
				board.setBoardNum(rs.getInt("board_num"));
				board.setBoardContent(rs.getString("board_content"));
				board.setBoardTitle(rs.getString("board_title"));
				board.setBoardCreatedate(rs.getString("board_createdate"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return board;
	}

	public List<Board> searchBoard(Board board) {
		String sql = "select * from board where 1=1 ";
		if (!board.getBoardWriter().equals("")) {
			sql += "and board_writer like'%'||?||'%'";
		} 
		if (!board.getBoardTitle().equals("")) {
			sql += "and board_title like '%'||?||'%'";
		}
		if(board.getBoardNum() != 0) {
			sql += "and board_num = ?";
		}

		List<Board> boardList = new ArrayList<Board>();
		connect();
		
		int cnt = 0;
		try {
			psmt = conn.prepareStatement(sql);
			if (!board.getBoardWriter().equals("")) { // 조회조건:작성자
				psmt.setString(++cnt, board.getBoardWriter());
			}
			if (!board.getBoardTitle().equals("")) {
				psmt.setString(++cnt, board.getBoardTitle());
			} 
			if (board.getBoardNum() != 0) {
				psmt.setInt(++cnt, board.getBoardNum());
			}
			rs = psmt.executeQuery(); // 조회결과 -> ResultSet
			while (rs.next()) {
				Board br = new Board();
				br.setBoardContent(rs.getString("board_content"));
				br.setBoardTitle(rs.getString("board_title"));
				br.setBoardWriter(rs.getString("board_writer"));
				br.setBoardNum(rs.getInt("board_num"));
				br.setBoardCreatedate(rs.getString("board_createdate"));

				boardList.add(br);
			}
		} catch (SQLException e) {
			e.printStackTrace(); 
		} finally {
			disconnect();
		}
		return boardList; // 조회된 결과를 List컬렉션에 담아서 반환.
	}
	

	public void insertBoard (Board board) { 
		String seqsql = "select BOARD_NUM_SEQ.nextval from dual";
		String sql = " insert into board(board_num, board_title, board_writer, board_content, board_createdate)"
				+ "values(?,?,?,?,sysdate)";
		connect();
		try {
			psmt = conn.prepareStatement(seqsql);
			rs = psmt.executeQuery();
			if (rs.next()) {
				int seq = rs.getInt(1); //결과의 첫번째 컬럼값을 가져와라

				psmt = conn.prepareStatement(sql);
				psmt.setInt(1, seq);
				psmt.setString(2, board.getBoardTitle());
				psmt.setString(3, board.getBoardWriter());
				psmt.setString(4, board.getBoardContent());

				int r = psmt.executeUpdate();
				System.out.println("입력완료" + r);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
	
	public void insertComment(Board board) {  //댓글기능
		String sql = " insert into board(board_num, board_title, board_writer, board_content, board_createdate, origin_num)"
				+ "values(?,?,?,?,sysdate,?)";
		connect();
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			if (rs.next()) { 
				psmt.setInt(1, board.getBoardNum());
				psmt.setString(2, "");
				psmt.setString(3, board.getBoardWriter());
				psmt.setString(4, board.getBoardContent());
				psmt.setInt(5, board.getOriginNum());


				int r = psmt.executeUpdate();
				System.out.println("등록완료" + r);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
}
