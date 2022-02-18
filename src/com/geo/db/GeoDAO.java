package com.geo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.geo.db.GeoDTO;




public class GeoDAO {
	// ��ġ������ ó���ϴ� DAO

	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private String sql = "";

	// ��񿬰�
	private Connection getCon() throws Exception {
		Context initCTX = new InitialContext();
		DataSource ds = (DataSource) initCTX.lookup("java:comp/env/jdbc/itwillbs7");
		con = ds.getConnection();
		return con;
	}

	// �ڿ�����
	public void CloseDB() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	
			// getMarkerList() : ��ġ�������� �ٹ� 100���� ���� ����� ���ؿ��� �������� ���. (������ : 1Ű�� ����)
						public ArrayList getMarkerList(HashMap<String, Object> listOpt)
						{
							// �������� �迭
							ArrayList geomList = new ArrayList();
							String latitude = (String)listOpt.get("latitude");
							String longitude = (String)listOpt.get("longitude");
							
							
							try {
								// 1.2. ��񿬰�
								con = getCon();
								// 3. sql �ۼ� & pstmt ��ü����
								// �ϴ� 1Ű�� ������ ��� ��ȸ(���浵�� �̾ƿ´�)
								sql = "SELECT latitude, longitude FROM (SELECT latitude, longitude, ( 6371 * acos( cos( radians("+latitude+") ) * cos( radians( latitude) ) * cos( radians( longitude ) - radians("+ longitude +") ) + sin( radians("+ latitude +") ) * sin( radians(latitude) ) ) ) AS distance FROM board)DATA WHERE DATA.distance < 1";					
								
								pstmt = con.prepareStatement(sql);					
								
								// 4. sql ����
								rs = pstmt.executeQuery();
								// 5. ������ ó��
								
								while(rs.next()){
									// ��Ŀ �ϳ��� ���� ����
									GeoDTO dto = new GeoDTO();
									dto.setLatitude(rs.getString("latitude"));
									dto.setLongitude(rs.getString("longitude"));
									
									// MemberBean�� ������ => ArrayList 1ĭ�� ����
									geomList.add(dto);
								}
								System.out.println("��� ��Ŀ ��ȸ �Ϸ�!");

							} catch (Exception e) {
								e.printStackTrace();
							} finally {
								CloseDB();
							}
							return geomList;
						}
						// getMarkerList()
			
			

	
						// getGeoList() : ��ġ�������� �ٹ� 100���� ���� ����� ���ؿ��� �������� ���. (������ : 1Ű�� ����)
						public ArrayList getGeoList(HashMap<String, Object> listOpt2)
						{
							//�������� �迭
							ArrayList geoList = new ArrayList();
							String latitude = (String)listOpt2.get("latitude");
							String longitude = (String)listOpt2.get("longitude");
							
							try {
								// 1.2. ��񿬰�
								con = getCon();
								// 3. sql �ۼ� & pstmt ��ü����
								sql = "SELECT * FROM (SELECT *, ( 6371 * acos( cos( radians("+latitude+") ) * cos( radians( latitude) ) * cos( radians( longitude ) - radians("+ longitude +") ) + sin( radians("+ latitude +") ) * sin( radians(latitude) ) ) ) AS distance FROM board)DATA WHERE DATA.distance < 1";					
								
								pstmt = con.prepareStatement(sql);
								
								// 4. sql ����
								rs = pstmt.executeQuery();
								// 5. ������ ó��
								
								while(rs.next()){
									// ����Ʈ �ϳ��� ���� ����
									GeoDTO dto = new GeoDTO();
									dto.setLatitude(rs.getString("latitude"));
									dto.setLongitude(rs.getString("longitude"));
									dto.setContent(rs.getString("content"));
									dto.setBno(rs.getInt("bno"));
									dto.setFood_category(rs.getString("food_category"));
									dto.setWhen_name(rs.getString("when_name"));
									dto.setId(rs.getString("id"));
									dto.setWhere_name(rs.getString("where_name"));
									dto.setWrite_time(rs.getTimestamp("write_time"));
									dto.setUpload_image(rs.getString("upload_image"));
									
									// MemberBean�� ������ => ArrayList 1ĭ�� ����
									geoList.add(dto);
								}
								System.out.println("��� ����Ʈ ��ȸ �Ϸ�!");
							} catch (Exception e) {
								e.printStackTrace();
							} finally {
								CloseDB();
							}
							return geoList;
						}
						// getGeoList()
		
		

}
