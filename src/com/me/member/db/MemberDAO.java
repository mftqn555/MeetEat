package com.me.member.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {
	// ȸ�������� ó���ϴ� DAO
	
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private String sql = "";
	
	// ��� ����
	private Connection getCon() throws Exception{
		Context initCTX = new InitialContext();
		DataSource ds = (DataSource) initCTX.lookup("java:comp/env/jdbc/itwillbs7");
		con = ds.getConnection();
		
		return con;
	}
	
	// �ڿ� ����
	public void closeDB() {
			try {
				if(rs != null) { rs.close(); }
				if(pstmt != null) { pstmt.close(); }
				if(con != null)  { con.close(); }
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	// ���̵� �˻�
	// idCheck(id)
	public int idCheck(String id) {
		
		int result = 0;
		
		try {
			con = getCon();
			
			sql = "select * from member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next() || id.equals("")) {
				result =  0; // �̹� �����ϴ� ȸ��
			} else {
				result =  1; // ���� ������ ȸ�� ���̵�
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		System.out.println(" M : MemberDAO_idCheck() ���� ");
		System.out.println(" M : ������ - "+result);
		return result;
	}
	// idCheck(id)
	
	// �г��� �˻�
	// nicknameCheck(nickname)
	public int nicknameCheck(String nickname) {
	
		int result = 0;
		
		try {
			con = getCon();
			
			sql = "select nickname from member where nickname=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, nickname);
			rs = pstmt.executeQuery();
			if(rs.next() || nickname.equals("")) {
				result =  0; // �̹� �����ϴ� �г���
			} else {
				result =  1; // ���� ������ �г���
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		System.out.println(" M : MemberDAO_nicknameCheck() ���� ");
		System.out.println(" M : ������ - "+result);
		return result;
	}
	// nicknameCheck(nickname)

	// ȸ������ 
	// insertMember(mdto)
	public int insertMember(MemberDTO mdto) {

		int result = 0;
		
		try {
			con = getCon();
			
			sql = "insert into member values(?,?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mdto.getId());
			pstmt.setString(2, mdto.getPw());
			pstmt.setString(3, mdto.getNickname());
			pstmt.setString(4, mdto.getPhone());
			pstmt.setString(5, mdto.getEmail());
			pstmt.setString(6, mdto.getAddress());
			pstmt.setString(7, mdto.getProfile_image());
			result = pstmt.executeUpdate();	// 1 
			
			sql = "insert into member_manage(id) values(?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mdto.getId());
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		System.out.println(" M : MemberDAO_insertMember() ����");
		System.out.println(" M : result - "+result);
		return result;
	}
	// insertMember(mdto)
	
	// loginCheck(id,pw)
	public int loginCheck(String id, String pw){
		
		int result = -1;
		
		try {
			con = getCon();				
			sql = "select pw, ban_date, now() from member_view where id=?";
			pstmt = con.prepareStatement(sql);				
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
		
			if(rs.next()){
				// ȸ������ ����
				if(pw.equals(rs.getString("pw"))){
					// �������� Ȯ��
					Date ban_date = rs.getDate(2);
					Date now = rs.getDate(3);
					
					if(ban_date == null || ban_date.equals("")) {
						result = 1;
					} else if(ban_date.compareTo(now) > 0) {
						result = -2;						
					} else {
						result = 1;
					}
					
				}else{
				// ȸ�������� ����, ��й�ȣ �ٸ�
					result = 0;
				}				
			}else{	
				// ȸ������ ����
				result = -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		System.out.println(" M : MemberDAO_loginCheck() ���� ");
		System.out.println(" M : ������ : "+result);
		
		return result;
	}
	// loginCheck(id,pw)	
	
	// ���̵� ã��
	// findId(email)
	public String findId(String email) {
			
		String findId = ""; // �ʱ�ȭ
			
		try {
			con = getCon();
			
			sql = "select id, email from member where email=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				findId = rs.getString("id");
			} else {
				findId = "-1";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		System.out.println(" M : MemberDAO_findId() ���� ");
		System.out.println(" M : ������ : "+findId);
		
		return findId;
	}
	// findId(email)
	
	// getMember(id)
	public MemberDTO getMember(String id){
		
		MemberDTO mdto = null;
		
		try {
			con = getCon();
			sql = "select * from member_view where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if(rs.next()){
				mdto = new MemberDTO();
				
				mdto.setId(rs.getString("id"));
				mdto.setPw(rs.getString("pw"));
				mdto.setPhone(rs.getString("phone"));
				mdto.setEmail(rs.getString("email"));
				mdto.setProfile_image(rs.getString("profile_image"));
				mdto.setAddress(rs.getString("address"));
				mdto.setNickname(rs.getString("nickname"));
				mdto.setUser_level(rs.getInt("user_level"));
				mdto.setUser_point(rs.getInt("user_point"));
				mdto.setBan_date(rs.getDate("ban_date"));
				mdto.setReported_count(rs.getInt("reported_count"));
			} else {
				mdto = null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		System.out.println(" M : MemberDAO_getMember() ���� ");
		System.out.println(" M : ȸ������ : "+mdto);		
		return mdto;
	}
	// getMember(id)


	// updateMember(mdto)
	public int updateMember(MemberDTO mdto) {
		int result = -1;
	
		try {
			con = getCon();
			sql = "select pw from member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mdto.getId());
			rs = pstmt.executeQuery();

			if(rs.next()) { 
				if(mdto.getPw().equals(rs.getString("pw"))) { 
					// ����
					sql = "update member set phone=?, email=?, address=?, nickname=?, profile_image=? where id=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, mdto.getPhone());
					pstmt.setString(2, mdto.getEmail());
					pstmt.setString(3, mdto.getAddress());
					pstmt.setString(4, mdto.getNickname());
					pstmt.setString(5, mdto.getProfile_image());
					pstmt.setString(6, mdto.getId());
					
					result = pstmt.executeUpdate();
					// -> sql ������ �������� �� ������ �� row�� ����
					// result = 1;
				} else {
					// ��й�ȣ ����
					result = 0;
				}
			} else { 
				// ������ ���� ��
				result = -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		System.out.println(" M : MemberDAO_updateMember() ����Ϸ�");
		System.out.println(" M : ������ - "+result);
		return result;
	}
	// updateMember(mdto) END

	// ������ ���� ��������
	// getProfile(id)
	public String getProfile(String id) {
		
		try {
			con = getCon();
			sql = "select profile_image from member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString("profile_image").equals("") || rs.getString("profile_image") == null) {
					return "http://localhost:8088/MeetEat/upload/member/NoImage.png";
				}
				return "http://localhost:8088/MeetEat/"+rs.getString("profile_image");
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		System.out.println(" M : MemberDAO_getprofile() ����Ϸ�");
		return "http://localhost:8088/MeetEat/upload/member/NoImage.png";
	}
	// getProfile(id)
	
	// deleteMember(id,pw)
	public int deleteMember(String id, String pw) {
		
		int result = 0;
		
		try {
			con = getCon();
			
			sql = "select pw from member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(pw.equals(rs.getString("pw"))) {
					// ����
					sql = "delete from member where id=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, id);
					result = pstmt.executeUpdate();
				} else {
					// ��й�ȣ ����
					result = 0;
				} 
			} else {
				// ���� X
				result = -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		System.out.println(" M : MemberDAO_deleteMember() ����Ϸ�");
		System.out.println(" M : ������ - "+result);
		return result;
	}
	// deleteMember(id,pw)

	// getMemberList()
	public ArrayList getMemberList() {
		
		ArrayList memberList = new ArrayList();
		
		try {
			con = getCon();
			
			sql = "select * from member_view";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				// ȸ�� 1���� ���� -> memberList�� ����
				MemberDTO mdto = new MemberDTO();
				mdto.setId(rs.getString("id"));
				mdto.setPw(rs.getString("pw"));
				mdto.setPhone(rs.getString("phone"));
				mdto.setEmail(rs.getString("email"));
				mdto.setProfile_image(rs.getString("profile_image"));
				mdto.setAddress(rs.getString("address"));
				mdto.setNickname(rs.getString("nickname"));
				mdto.setUser_level(rs.getInt("user_level"));
				mdto.setUser_point(rs.getInt("user_point"));
				mdto.setBan_date(rs.getDate("ban_date"));
				mdto.setReported_count(rs.getInt("reported_count"));
				
				// MemberDTO�� ������ ArrayList 1ĭ�� ����
				memberList.add(mdto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		System.out.println(" M : MemberDAO_getMemberList() ���� �Ϸ�");
		return memberList;
	}
	// getMemberList()

	// ȸ�� ��й�ȣ ã��
	// findPw(id, email)
	public int findPw(String id, String email) {
		
		int result = 0;
		
		try {
			con = getCon();
			
			sql = "select id, email from member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(email.equals(rs.getString("email"))) {
					result = 1;
				} else {
					// �̸��� ����
					result = 0;
				} 
			} else {
				// ���� X
				result = -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		System.out.println(" M : MemberDAO_findPw() ���� �Ϸ�");
		System.out.println(" M : ���� ��� : "+result);
		return result;
	}
	
	public String findPw(String id) {
			
		String result = "";
		
		try {
			con = getCon();
			
			sql = "select pw from member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getString("pw");
			} else {
				result = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		System.out.println(" M : MemberDAO_findPw() ���� �Ϸ�");
		System.out.println(" M : ���� ��� : "+result);
		return result;
	}
	
	
	// findPw(id, email)

	// �ӽ� ��� ����
	// updatePw(id, pw, email)
	public int updatePw(String id, String pw, String email) {
		
		int result = 0; 
		
		try {
			con = getCon();
			
			sql = "select id,email from member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(email.equals(rs.getString("email"))) {
					
					sql = "update member set pw=? where id=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, pw);
					pstmt.setString(2, id);
					result = pstmt.executeUpdate();
				} else {
					// �̸����� �ٸ�
					result = 0;
				} 
			} else {
				// ���� X
				result = -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		System.out.println(" M : Member_updatePw() ���� �Ϸ�");
		System.out.println(" M : ���� ��� : "+result);
		return result;
		
	}
	// updatePw(id, pw, email)

	// �н����� ����
	// modifyPw(id, oldPw, pw)
	public int modifyPw(String id, String oldPw, String pw) {
		
		int  result = 0;
		
		try {
			con = getCon();
			
			sql = "select id, pw from member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(oldPw.equals(rs.getString("pw"))) {
					sql = "update member set pw=? where id=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, pw);
					pstmt.setString(2, id);
					pstmt.executeUpdate();
					
					result = 1;
				} else {
					// ��й�ȣ ��ġX
					result = 0;
				} 
			} else {
				result = -1;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		System.out.println(" M : Member_modifyPw() ���� �Ϸ�");
		System.out.println(" M : ���� ���  : "+result);
		return result;
	}
	
	public void insertReport(String report_user, String reported_user, String report_content) {
		
		try {
			// �Ű��� ����
			con = getCon();
			sql = "insert into report_manage(report_user, reported_user, report_content, report_date) values(?, ?, ?, now())";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, report_user);
			pstmt.setString(2, reported_user);
			pstmt.setString(3, report_content);
			
			pstmt.executeUpdate();
			
			// �����Ű�Ƚ�� ������Ʈ
			sql = "update member_manage "
					+ "set reported_count = (SELECT count(reported_user) FROM report_manage WHERE reported_user = ?) "
					+ "where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, reported_user);
			pstmt.setString(2, reported_user);
			
			pstmt.executeUpdate();
			
			// ������¥ ������Ʈ
			sql = "select reported_count from member_manage where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, reported_user);
			
			rs = pstmt.executeQuery();
			
			String banSql = "";
			
			if(rs.next()) {
				if(rs.getInt(1) == 3) {
					banSql="update member_manage set ban_date=DATE_ADD(now(), INTERVAL 30 DAY) where id=?";
				} else if(rs.getInt(1) == 5) {
					banSql="update member_manage set ban_date=DATE_ADD(now(), INTERVAL 100 DAY) where id=?";
				} else if(rs.getInt(1) == 7) {
					banSql="update member_manage set ban_date=DATE_ADD(now(), INTERVAL 100 YEAR) where id=?";
				}
				
				pstmt = con.prepareStatement(banSql);
				pstmt.setString(1, reported_user);
				pstmt.executeUpdate();
				
			} else {
				System.out.println("������¥ ������Ʈ ����");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
	}

	
	
}