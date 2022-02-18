package com.geo.action;


import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.geo.db.GeoDAO;
import com.geo.db.GeoDTO;



public class GeoMarkerAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		System.out.println(" G :GeoMarkerAction_execute() ȣ��");

		// ��ġ ������ ��������(����)
		HttpSession session = request.getSession();

		//���޵� �Ķ���� ����1
		String latitude = request.getParameter("latitude");
		String longitude = request.getParameter("longitude");
		
		session.setAttribute("latitude", latitude);
		session.setAttribute("longitude", longitude);
		
		/* ���޵� �Ķ���� ����
		String latitude = (String)session.getAttribute("latitude");
		String longitude = (String)session.getAttribute("longitude");
		*/
		

		// ��ġ������ üũ(��ġ������ ���� ��, Beforemain.do�� �̵�)
		//ActionForward forward = new ActionForward();
		if(latitude == null){
			System.out.println("�ȳ��ϼ��� MarkerAction �Դϴ�. 12345678913");
			//forward.setPath("./BeforeMain.do");
			//forward.setRedirect(true);
			//return forward;
		}		
		
		
		HashMap<String, Object> listOpt = new HashMap<String, Object>();
        listOpt.put("latitude", latitude);
        listOpt.put("longitude", longitude);

        GeoDAO dao = new GeoDAO();
        ArrayList list =  dao.getMarkerList(listOpt);

        request.setAttribute("geomList", list);

		

		
		//���� ���Ȯ��
		System.out.println(latitude);
		System.out.println(longitude);
			
		
		
		
     // ������ �̵�- markerview �������� 
		    ActionForward forward = new ActionForward();
     		forward.setPath("./GeoView/markerview.jsp");
     		forward.setRedirect(false);		
     		return forward;
     		
     		
	} //execute

}