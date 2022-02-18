package com.geo.action;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.geo.db.GeoDAO;


public class GeoListAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		System.out.println(" G :GeoListAction_execute() ȣ��");
		
	
		// * ��ġ ������ ��������(����)
		HttpSession session = request.getSession();

		// ���޵� �Ķ���� ����1
		//String latitude = request.getParameter("latitude");
		//String longitude = request.getParameter("longitude");
		
		// ���޵� �Ķ���� ����2 : ��Ŀ�� �������� �鷯�� �ϴµ�, �Ѵ� �޴� id ���� �ٸ���
		String latitude = request.getParameter("x");
		String longitude = request.getParameter("y");
		
		//���ĸ� ���� ����ó��
		session.setAttribute("latitude", latitude);
		session.setAttribute("longitude", longitude);
		

		// ��ġ������ üũ(��ġ������ ���� ��, Beforemain.do�� �̵�(X) -> �ϼ� ������ console ������� ����)
		ActionForward forward = new ActionForward();
		if(latitude == null){
			System.out.println("�ȳ��ϼ��� ListAction �Դϴ�. 12345678913");
			//forward.setPath("./BeforeMain.do");
			//forward.setRedirect(true);
			//return forward;
		   }		
	
		
		HashMap<String, Object> listOpt2 = new HashMap<String, Object>();
        listOpt2.put("latitude", latitude);
        listOpt2.put("longitude", longitude);

        GeoDAO dao = new GeoDAO();
        ArrayList list =  dao.getGeoList(listOpt2);

        request.setAttribute("geoList", list);
		
		
		
		
		
		
		
		
				
				// DAO ��ü�� ���� 
				//GeoDAO dao = new GeoDAO();
				//GeoDTO dto = dao.getMarkerList();
				//ArrayList geolist =  dao.getGeoList();
				//request.setAttribute("geoList", dao.getGeoList());
							
				//���� Ȯ��üũ
				System.out.println(latitude);
				System.out.println(longitude);


     // ������ �̵�- markerview �������� 
     		forward.setPath("./GeoView/AfterMain.jsp");
     		forward.setRedirect(false);		
     		return forward;
	} //execute

}