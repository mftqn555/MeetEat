package com.ad.admin.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class ReportDAO {

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


	/* �Ű���� */

	public void insertReport(String report_user, String reported_user, String report_content) {

		try {
			con = getCon();
			sql = "insert into report_manage(report_user, reported_user, report_content, report_date) values(?, ?, ?, now())";
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, report_user);
			pstmt.setString(2, reported_user);
			pstmt.setString(3, report_content);

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}

	}

	public ArrayList getReportList(int num) {

		ArrayList reportList = new ArrayList();

		try {
			con = getCon();

			sql = "SELECT * FROM report_view WHERE num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);

			rs = pstmt.executeQuery();

			while(rs.next()) {
				// ȸ�� 1���� ���� -> memberList�� ����
				ReportDTO rdto = new ReportDTO();
				rdto.setNum(rs.getInt(1));
				rdto.setReported_user(rs.getString(2));
				rdto.setReported_count(rs.getInt(3)); 
				rdto.setReport_content(rs.getString(4));
				rdto.setReport_user(rs.getString(5));
				rdto.setReport_date(rs.getDate(6));
				rdto.setBan_date(rs.getDate(7));

				// MemberDTO�� ������ ArrayList 1ĭ�� ����
				reportList.add(rdto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		System.out.println(" M : MemberDAO_getReportList() ���� �Ϸ�");
		return reportList;
	}

	public int getReportCount() {

		int result = 0;

		try {
			con = getCon();

			sql = "select found_rows()";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		System.out.println(" M : MemberDAO_getReportCount() ���� �Ϸ�");

		return result;
	}

	public ArrayList sortReport(String userSelect, String id, String option, String sort, int startRow, int pageSize) {

		ArrayList reportList = new ArrayList();

		try {
			con = getCon();
			sql = "SELECT SQL_CALC_FOUND_ROWS * "
					+ "FROM report_view "
					+ "WHERE "+userSelect+" = ? "
					+ "ORDER BY "+option+" "+sort+" "
					+ "LIMIT ?, ? ";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, id);
			pstmt.setInt(2, startRow-1);
			pstmt.setInt(3, pageSize);

			rs = pstmt.executeQuery();

			while(rs.next()) {

				ReportDTO rdto = new ReportDTO();
				rdto.setNum(rs.getInt(1));
				rdto.setReported_user(rs.getString(2));
				rdto.setReported_count(rs.getInt(3));
				rdto.setReport_content(rs.getString(4));
				rdto.setReport_user(rs.getString(5));
				rdto.setReport_date(rs.getDate(6));
				rdto.setBan_date(rs.getDate(7));

				reportList.add(rdto);
			}

			System.out.println(" M : MemberDAO_sortReport() ���� �Ϸ�");

			// ���� ��� ���ư����� Ȯ�ο�
			//System.out.println("sql: " + pstmt);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}

		return reportList;
	}

	public void banMember(String date, String reported_user) {

		try {
			con = getCon();
			sql = "update member_manage set ban_date=? where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, date);
			pstmt.setString(2, reported_user);

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}

	}

	public int banCancelMember(String reported_user) {

		int result = 0;

		try {
			con = getCon();
			sql = "select ban_date from member_manage where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, reported_user);

			rs = pstmt.executeQuery();

			if(rs.next()) {
				if(rs.getDate(1) == null) {
					result = 0;
				} else {
					sql = "update member_manage set ban_date=null where id=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, reported_user);

					pstmt.executeUpdate();

					result = 1;
				}
			} else {
				result = -1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return result;
	}


	/* �Ű���� */



}