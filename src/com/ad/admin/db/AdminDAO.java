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
	
	
	// 디비연결
	private Connection getCon() throws Exception{
		
		Context initCTX = new InitialContext();
		DataSource ds = (DataSource)initCTX.lookup("java:comp/env/jdbc/itwillbs7");
		con = ds.getConnection();
		
		return con;
	}
	
	// 자원해제
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
	
	// 회원목록 조회
	public ArrayList getMemberList(){
		ArrayList memberList = new ArrayList();

		try{
			con = getCon();
			sql = "select a.id, a.pw, a.nickname, a.phone, a.email, a.address, b.user_point, b.user_level, b.reported_count, b.ban_date " + 
					"from member a join member_manage b " + 
					"on a.id=b.id " + 
					"where a.id != 'admin'";
			pstmt = con.prepareStatement(sql);
			//8-3. 실행 -> rs저장
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
				
				//리스트 한 칸에 회원 1명의 정보 저장 
				memberList.add(adto);
			}
			System.out.println("memberList검색완료");	
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			CloseDB();
		}
		return memberList;
	}//getMemberList 끝
	
	// getMemberList(col_name, id_nick, startRow, pageSize)
	public ArrayList getMemberList(String col_name, String id_nick, int startRow, int pageSize){
		ArrayList memberList = new ArrayList();
			
		try {
			// 1,2 디비연결
			con = getCon();
			// 3 sql 작성 & pstmt 객체 생성
			sql = "select SQL_CALC_FOUND_ROWS a.id, a.pw, a.nickname, a.phone, a.email, a.address, b.user_point, b.user_level, b.reported_count, b.ban_date " +
					"from member a join member_manage b " + 
					"on a.id=b.id " + 
					"where a.id != 'admin' " + 
					"and "+col_name+" = ? " +
					"order by id asc limit ?,?";
			pstmt = con.prepareStatement(sql);
				
			//?
			pstmt.setString(1, id_nick);
			pstmt.setInt(2, startRow-1); // 시작행-1  (시작 ROW인덱스 번호)
			pstmt.setInt(3, pageSize); // 페이지크기  (한번에 출력되는 수)
				
			// 4 sql 실행
			rs = pstmt.executeQuery();
			// 5 데이터처리 (글 1개의 정보 -> DTO 1개 -> ArrayList 1칸)
			while(rs.next()){
				//데이터 있을때 마다 글 1개의 정보를 저장하는 객체 생성
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
	// getMemberList(col_name, id_nick, startRow, pageSize)끝
	
	
	// getMemberCount()
	public int getMemberCount(){
		int cnt = 0;
		
		try {
			// 1,2 디비연결
			con = getCon();
			// 3 sql 작성(select) & pstmt 객체
			sql = "select count(*) from member where id != 'admin'";
			pstmt = con.prepareStatement(sql);
			// 4 sql 실행
			rs = pstmt.executeQuery();
			// 5 데이터 처리
			if(rs.next()){
				//데이터 있을때 (글개수)
				cnt = rs.getInt(1);	
			}			
			System.out.println(" DAO : 회원수 ("+cnt+")");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CloseDB();
		}
		
		return cnt;
	}
	// getMemberCount() 끝
	
	
	
	
	// deleteMember(id, pw)
	public int deleteMember(String delid, String pw) {
		int result = -1;
		
		System.out.println(delid+ " " +pw);
				
		try {
			// 1.2. 디비연결
			con = getCon();
			// 3. sql 작성(select) & pstmt 객체 생성
			sql = "select pw from member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, delid);
			rs = pstmt.executeQuery();

			if(rs.next()) { //데이터 있을때
						
				if(pw.equals(rs.getString("pw"))) { 
					sql = "delete from member where id=?";
					pstmt = con.prepareStatement(sql);
							
					pstmt.setString(1, delid);	
					pstmt.executeUpdate();
					result = 1;
					System.out.println("회원삭제 완료");
							
				}else {
					//비밀번호가 잘못됨
					result = 0;
				}				
					
			}else {
				//데이터 없을때
				result = -1;
			}
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				CloseDB();
			}
				
		return result;
	}			
	// deleteMember(id, pw) 끝
	

}
