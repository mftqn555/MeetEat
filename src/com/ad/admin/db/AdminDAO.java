package com.ad.admin.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class AdminDAO {
	
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private String sql = "";
	
	
	// ��񿬰�
	private Connection getCon() throws Exception{
		
		Context initCTX = new InitialContext();
		DataSource ds = (DataSource)initCTX.lookup("java:comp/env/jdbc/itwillbs7");
		con = ds.getConnection();
		
		return con;
	}
	
	// �ڿ�����
	public void CloseDB() {
		
		try {
			if(rs!=null) {
				rs.close();
			}
			if(pstmt!=null) {
				pstmt.close();
			}
			if(con!=null) {
				con.close();
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	// ȸ����� ��ȸ
	public ArrayList getMemberList(){
		ArrayList memberList = new ArrayList();

		try{
			con = getCon();
			sql = "select a.id, a.pw, a.nickname, a.phone, a.email, a.address, b.user_point, b.user_level, b.reported_count, b.ban_date " + 
					"from member a join member_manage b " + 
					"on a.id=b.id " + 
					"where a.id != 'admin'";
			pstmt = con.prepareStatement(sql);
			//8-3. ���� -> rs����
			rs = pstmt.executeQuery();
			while(rs.next()){
				AdminDTO adto = new AdminDTO();
				adto.setEmail(rs.getString("email"));
				adto.setId(rs.getString("id"));
				adto.setNickname(rs.getString("nickname"));
				adto.setPw(rs.getString("pw"));
				adto.setAddress(rs.getString("address"));
				adto.setPhone(rs.getString("phone"));
				adto.setUser_point(rs.getInt("user_point"));
				adto.setUser_level(rs.getInt("user_level"));
				adto.setReported_count(rs.getInt("reported_count"));
				adto.setBan_date(rs.getDate("ban_date"));
				
				//����Ʈ �� ĭ�� ȸ�� 1���� ���� ���� 
				memberList.add(adto);
			}
			System.out.println("memberList�˻��Ϸ�");	
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			CloseDB();
		}
		return memberList;
	}//end of getMemberList method
	
	
	// deleteMember(id, pass)
	public int deleteMember(String id, String pass) {
		int result = -1;
				
		try {
			// 1.2. ��񿬰�
			con = getCon();
			// 3. sql �ۼ�(select) & pstmt ��ü ����
			sql = "select pass from member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			// 4. sql ����
			rs = pstmt.executeQuery();
					
			// 5. ������ ó��
			if(rs.next()) { //������ ������
						
				if(pass.equals(rs.getString("pass"))) { // ����
					// 3. sql ���� & pstmt ��ü ����
					sql = "delete from member where id=?";
					pstmt = con.prepareStatement(sql);
							
					pstmt.setString(1, id);						
					// 4. sql ���� 
					result = pstmt.executeUpdate();
					System.out.println("ȸ������ �Ϸ�");
							
				}else {
					// ���̵�� �´µ� ��й�ȣ�� �߸���
					result = 0;
				}				
					
			}else {
				//������ ������
				result = -1;
			}
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				CloseDB();
			}
				
		return result;
	}			
	// deleteMember(id, pass) ��
	
	// getMemberList(startRow,pageSize)
		public ArrayList getMemberList(String col_name, String id_nick, int startRow, int pageSize){
			ArrayList memberList = new ArrayList();
				
			try {
				// 1,2 ��񿬰�
				con = getCon();
				// 3 sql �ۼ� & pstmt ��ü ����
				sql = "select SQL_CALC_FOUND_ROWS a.id, a.pw, a.nickname, a.phone, a.email, a.address, b.user_point, b.user_level, b.reported_count, b.ban_date " +
						"from member a join member_manage b " + 
						"on a.id=b.id " + 
						"where a.id != 'admin' " + 
						"and "+col_name+" = ? " +
						"order by id asc limit ?,?";
				pstmt = con.prepareStatement(sql);
					
				//?
				pstmt.setString(1, id_nick);
				pstmt.setInt(2, startRow-1); // ������-1  (���� ROW�ε��� ��ȣ)
				pstmt.setInt(3, pageSize); // ������ũ��  (�ѹ��� ��µǴ� ��)
					
				// 4 sql ����
				rs = pstmt.executeQuery();
				// 5 ������ó�� (�� 1���� ���� -> DTO 1�� -> ArrayList 1ĭ)
				while(rs.next()){
					//������ ������ ���� �� 1���� ������ �����ϴ� ��ü ����
					AdminDTO adto = new AdminDTO();
					adto.setEmail(rs.getString("email"));
					adto.setId(rs.getString("id"));
					adto.setNickname(rs.getString("nickname"));
					adto.setPw(rs.getString("pw"));
					adto.setAddress(rs.getString("address"));
					adto.setPhone(rs.getString("phone"));
					adto.setUser_point(rs.getInt("user_point"));
					adto.setUser_level(rs.getInt("user_level"));
					adto.setReported_count(rs.getInt("reported_count"));
					adto.setBan_date(rs.getDate("ban_date"));			

					memberList.add(adto);				
					
				}//while
					
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				CloseDB();
			}
				
			return memberList;
		}
		// getMemberList(startRow,pageSize)��
		
		
		// getMemberCount()
		public int getMemberCount(){
			int cnt = 0;
			
			try {
				// 1,2 ��񿬰�
				con = getCon();
				// 3 sql �ۼ�(select) & pstmt ��ü
				sql = "select found_rows()";
				pstmt = con.prepareStatement(sql);
				// 4 sql ����
				rs = pstmt.executeQuery();
				// 5 ������ ó��
				if(rs.next()){
					//������ ������ (�۰���)
					cnt = rs.getInt(1);	
				}			
				System.out.println(" DAO : ȸ���� ("+cnt+")");
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				CloseDB();
			}
			
			return cnt;
		}
		// getMemberCount() ��
		
	
	
	

}