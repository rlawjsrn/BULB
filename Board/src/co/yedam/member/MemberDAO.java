package co.yedam.member;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO extends DAO {

	public boolean login(String memberId, String memberPw) {
		String sql = "SELECT * FROM member WHERE member_id = ? AND member_pw=?";
		connect();
		Member member = null;
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, memberId);
			psmt.setString(2, memberPw);
			rs = psmt.executeQuery();

			if (rs.next()) {
				return true;

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return false;
	}
}