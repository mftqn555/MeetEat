package com.bo.qna.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class QnaDAO {
	
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
	
	// insertQna(qdto)
	public void insertQna(QnaDTO qdto) {
		int qno = 0; // �۹�ȣ ����

		try {
			con = getCon();
			System.out.println("��񿬰�Ϸ�");

			// �۹�ȣ ���
			// 3. sql ���� & pstmt ��ü
			sql = "select max(qno) from qna";
			pstmt = con.prepareStatement(sql);

			// 4. sql ���� ����
			rs = pstmt.executeQuery();
			// 5. ������ ó��
			if (rs.next()) {
				// ������ �۹�ȣ(������ִ밪) + 1
				qno = rs.getInt(1) + 1;
				// getInt(�ε���) -> �÷��� ���� ����, ���࿡ SQL null�̸� 0����
			}
			
			System.out.println(" �� ��ȣ : " + qno);
			
			// ���޹��� �������� DB insert				
			// 3. sql �ۼ� (insert) & pstmt ��ü ����
			sql = "insert into qna(qno,id,title,content,readcount,"
					+ "re_ref,re_lev,re_seq,reg_date) "
					+ "values(?,?,?,?,?,?,?,?,now())";
			
			pstmt = con.prepareStatement(sql);
			
			// ?
			pstmt.setInt(1, qno);
			pstmt.setString(2, qdto.getId());
			pstmt.setString(3, qdto.getTitle());
			pstmt.setString(4, qdto.getContent());
			pstmt.setInt(5, 0);// ��ȸ�� 0���� �ʱ�ȭ
			pstmt.setInt(6, qno);  // re_ref �׷��ȣ
			pstmt.setInt(7, 0);   //re_lev ������
			pstmt.setInt(8, 0);   // re_seq ����

			
			// 4. sql ����
			pstmt.executeUpdate();
			
			System.out.println(" DAO : ���ۼ� �Ϸ�! ");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CloseDB();
		}
	}	
	// insertQna() ��
	
	// getQnaCount()
	public int getQnaCount(){
		int cnt = 0;
		
		try {
			// 1,2 ��񿬰�
			con = getCon();
			// 3 sql �ۼ�(select) & pstmt ��ü
			sql = "select count(*) from qna";
			pstmt = con.prepareStatement(sql);
			// 4 sql ����
			rs = pstmt.executeQuery();
			// 5 ������ ó��
			if(rs.next()){
				//������ ������ (�۰���)
				cnt = rs.getInt(1);	
			}
			
			System.out.println(" DAO : �۰��� ("+cnt+")");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CloseDB();
		}
		
		return cnt;
	}
	// getQnaCount() ��
	
	
	// getQnaList()
	public List getQnaList(){
	List qnaList = new ArrayList();
		
		try {
			// 1,2 ��񿬰�
			con = getCon();
			// 3 sql �ۼ� & pstmt ��ü ����
			sql = "select * from qna order by re_ref desc, re_seq asc";
			pstmt = con.prepareStatement(sql);
			// 4 sql ����
			rs = pstmt.executeQuery();
			// 5 ������ó�� (�� 1���� ���� -> DTO 1�� -> ArrayList 1ĭ)
			while(rs.next()){
				//������ ������ ���� �� 1���� ������ �����ϴ� ��ü ����
				QnaDTO qdto = new QnaDTO();
				
				qdto.setId(rs.getString("id"));
				qdto.setContent(rs.getString("content"));
				qdto.setReg_date(rs.getTimestamp("reg_date"));
				qdto.setQno(rs.getInt("qno"));
				qdto.setRe_lev(rs.getInt("re_lev"));
				qdto.setRe_ref(rs.getInt("re_ref"));
				qdto.setRe_seq(rs.getInt("re_seq"));
				qdto.setReadcount(rs.getInt("readcount"));
				qdto.setTitle(rs.getString("title"));
				
				// DTO ��ü�� ArrayList ��ĭ�� ����
				qnaList.add(qdto);				
				
			}//while
			
			System.out.println(" DAO : �� ���� ����Ϸ� "+qnaList.size());
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CloseDB();
		}
		
		return qnaList;
	}
	// getQnaList() ��
	
	// getQnaList(startRow,pageSize)
	public ArrayList getQnaList(int startRow,int pageSize){
		ArrayList qnaList = new ArrayList();
			
		try {
			// 1,2 ��񿬰�
			con = getCon();
			// 3 sql �ۼ� & pstmt ��ü ����
			// �� re_ref �ֽű� ����(��������), re_seq (��������)
			// DB�����͸� ���ϴ¸�ŭ�� ©�󳻱� : limit ������-1,������ũ�� 
			sql = "select a.qno, a.id, a.title, a.content, a.readcount, a.re_ref, a.re_lev, a.re_seq, a.reg_date, b.nickname "
					+ "from qna a join member b "
					+ "on a.id=b.id "
					+ "order by re_ref desc, re_seq asc limit ?,?";
			pstmt = con.prepareStatement(sql);
				
			//?
			pstmt.setInt(1, startRow-1); // ������-1  (���� ROW�ε��� ��ȣ)
			pstmt.setInt(2, pageSize); // ������ũ��  (�ѹ��� ��µǴ� ��)
				
			// 4 sql ����
			rs = pstmt.executeQuery();
			// 5 ������ó�� (�� 1���� ���� -> DTO 1�� -> ArrayList 1ĭ)
			while(rs.next()){
				//������ ������ ���� �� 1���� ������ �����ϴ� ��ü ����
				QnaDTO qdto = new QnaDTO();
					
				qdto.setId(rs.getString("id"));
				qdto.setContent(rs.getString("content"));
				qdto.setReg_date(rs.getTimestamp("reg_date"));
				qdto.setQno(rs.getInt("qno"));
				qdto.setRe_lev(rs.getInt("re_lev"));
				qdto.setRe_ref(rs.getInt("re_ref"));
				qdto.setRe_seq(rs.getInt("re_seq"));
				qdto.setReadcount(rs.getInt("readcount"));
				qdto.setTitle(rs.getString("title"));
				qdto.setNickname(rs.getString("nickname"));

				qnaList.add(qdto);				
				
			}//while
				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CloseDB();
		}
			
		return qnaList;
	}
	// getQnaList(startRow,pageSize)
	
	// updateReadcount(qno)
	public void updateReadcount(int qno){
		try {
			
			con = getCon();
			sql = "update qna set readcount = readcount+1 where qno=?";
			pstmt = con.prepareStatement(sql);
			
			//?
			pstmt.setInt(1, qno);

			pstmt.executeUpdate();
			
			System.out.println("DAO : ��ȸ�� 1����");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CloseDB();
		}
		
	}
	// updateReadcount(qno) ��
	
	
	// getQna(qno)
	public QnaDTO getQna(int qno){
		QnaDTO qdto = null;
		
		try {
			
			con = getCon();
			sql = "select * from qna where qno=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, qno);
			
			rs = pstmt.executeQuery();
			
			// 5. ������ó��
			if(rs.next()){
				qdto = new QnaDTO();
				
				qdto.setId(rs.getString("id"));
				qdto.setContent(rs.getString("content"));
				qdto.setReg_date(rs.getTimestamp("reg_date"));
				qdto.setQno(rs.getInt("qno"));
				qdto.setRe_lev(rs.getInt("re_lev"));
				qdto.setRe_ref(rs.getInt("re_ref"));
				qdto.setRe_seq(rs.getInt("re_seq"));
				qdto.setReadcount(rs.getInt("readcount"));
				qdto.setTitle(rs.getString("title"));
				
			}//if			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CloseDB();
		}
		
		return qdto;
	}
	// getQna(qno)
	
	
	// updateQna(qdto)
	public void updateQna(QnaDTO qdto){
		
		try {
			//1.2. ��񿬰�
			con = getCon();

			sql = "update qna set title=?,content=? "
							+ "where qno=?";
			pstmt = con.prepareStatement(sql);
					
			pstmt.setString(1, qdto.getTitle());
			pstmt.setString(2, qdto.getContent());
			pstmt.setInt(3, qdto.getQno());
					
			// 4. sql ����
			pstmt.executeUpdate();
	
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CloseDB();
		}
	}
	// updateQna(qdto)
	
	// deleteQna(qno)
	public void deleteQna(int qno){
		
		try {
			// 1.2. ��񿬰�
			con = getCon();
			// 3. sql
			sql ="delete from qna where re_ref=?";
			pstmt = con.prepareStatement(sql);
					
			pstmt.setInt(1, qno);
			// 4. sql ����
			pstmt.executeUpdate();				
			
			System.out.println(" DAO : QnA �� ���� �Ϸ�");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CloseDB();
		}		
	}
	// deleteQna(qno) ��
	

	// replyQna(qdto)
	public void replyQna(QnaDTO qdto) {
		int qno = 0;
		
		
		try {
			// ��۹�ȣ ���(num)
			// 1.2 DB ����
			con = getCon();
			// 3. sql �ۼ� & pstmt ��ü
			sql = "select max(qno) from qna";
			pstmt = con.prepareStatement(sql);
			
			// 4. sql ����
			rs = pstmt.executeQuery();
			
			// 5. ������ó��
			if(rs.next()) {
				qno = rs.getInt(1)+1;
			}
			System.out.println("DAO : ��� ��ȣ(bno): "+qno);
			
			// ��ۼ��� ���ġ (re_ref ���� �׷쿡�� re_seq������ ������ ū���� ���� �� re_seq�� 1����)
			
				// 3. sql �ۼ� & pstmt 
				sql = "update qna set re_seq = re_seq + 1 "
						+ "where re_ref=? and re_seq>?";
				
				pstmt = con.prepareStatement(sql);
				
				pstmt.setInt(1, qdto.getRe_ref()); // �θ���� ref
				pstmt.setInt(2, qdto.getRe_seq()); // �θ���� seq
				
				// 4. sql ����
				pstmt.executeUpdate();			
			// ��ۼ��� ���ġ (re_ref ���� �׷쿡�� re_seq������ ������ ū���� ���� �� re_seq�� 1����)
				
			// ��� ����(insert)
			// 3. sql �ۼ� (insert) & pstmt ��ü ����
			sql = "insert into qna(qno,id,title,content,readcount,"
					+ "re_ref,re_lev,re_seq,reg_date) " + "values(?,?,?,?,?,?,?,?,now())";

			pstmt = con.prepareStatement(sql);

			// ?
			pstmt.setInt(1, qno);
			pstmt.setString(2, qdto.getId());
			pstmt.setString(3, qdto.getTitle());
			pstmt.setString(4, qdto.getContent());
			pstmt.setInt(5, 0);// ��ȸ�� 0���� �ʱ�ȭ
			pstmt.setInt(6, qdto.getRe_ref()); // re_ref �׷��ȣ = �θ���� �׷��ȣ
			pstmt.setInt(7, qdto.getRe_lev()+1); // re_lev ������ => �θ�� lev + 1
			pstmt.setInt(8, qdto.getRe_seq()+1); // re_seq ���� => �θ�� seq + 1

			// 4. sql ����
			pstmt.executeUpdate();
			
			System.out.println("DAO �� �ۼ��Ϸ�");
				
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			CloseDB();
		}
		
	}
	// replyBoard(bto) ��
	
	
	// redeleteBoard(bno,re_seq,pass)
	public int redeleteBoard(int qno,int re_seq){
		int result = -1;
		
		try {
			// 1.2. ��񿬰�
			con = getCon();
			// 3. sql �ۼ� & pstmt ��ü ����
			sql = "select pass from hj_board where qno=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, qno);
			
			// 4. sql ����
			rs = pstmt.executeQuery();
			
			// 5. ������ ó��
			if(rs.next()){
				// 3. sql
				sql ="delete from qna where qno=? and re_seq=?";
				pstmt = con.prepareStatement(sql);
					
				pstmt.setInt(1, qno);
				pstmt.setInt(2, re_seq);
				// 4. sql ����
				pstmt.executeUpdate();
				result = 1;
			}else{
				result = -1;
			}			
			System.out.println(" DAO : ��� ���� �Ϸ�! "+result);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CloseDB();
		}		
		
		return result;
	}
	// redeleteBoard(qno,re_seq) ��
	

}