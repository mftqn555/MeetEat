package com.geo.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class GeoFrontController extends HttpServlet{

	protected void doProcess(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GeoFrontController_doProcess() ȣ��! ");
		// �������� GET/POST��� ������� ȣ��ɶ� ����Ǵ� �޼���
		// *.do - ����, �Խ��� ó�� ����
		
		// -----------------------1. ���� �ּ� ��� --------------------------
		//�����ּ� ��������
		//String url = request.getRequestURL()+"";
		// http://localhost:8088/Model2/test1234.me
		// ��������:// ������ : ��Ʈ��ȣ / ������Ʈ�� / �����ּ�
		//String uri = request.getRequestURI(); 
		// /Model2/test1234.me
		// ������Ʈ��/ �����ּ�
		//System.out.println("url : "+url);
		//System.out.println("uri : "+uri);
		
		String requestURI = request.getRequestURI();
		System.out.println(" C : requestURI - "+requestURI);
		String ctxPath = request.getContextPath();
		System.out.println(" C : ctxPath - "+ctxPath);
		String command = requestURI.substring(ctxPath.length());
		System.out.println(" C : command - "+command);
		System.out.println(" C : 1. ���� �ּ� ��� ��! ");
		// -----------------------1. ���� �ּ� ��� --------------------------
		// -----------------------2. ���� �ּ� ����(ó��) --------------------
		
		Action action = null;
		ActionForward forward = null;
		
		
		
		
		
		
		
		
		if(command.equals("/BeforeMain.do")){
			System.out.println(" G : /BeforeMain.do ȣ��! ");
			// ��ġ���� �Է� �� ������ ���������. (DBx -> ȭ��(View-jsp) ���) 
			
			forward = new ActionForward();
			forward.setPath("./GeoView/BeforeMain.jsp");
			forward.setRedirect(false);		
			
			
		}else if(command.equals("/roadname.do")){
			System.out.println(" G : /roadname.do ȣ��! ");
			//���� : ��ġ������ �������� �˻� (roadname.do) -> ��ǥ�� ���� ������ 1000���� �ٹ��� ��� ��ǥ�� ������ ��Ŀ�� ǥ�� -> Aftermain���� �̵��� ��� ����� ����Ʈ�� �����ش�.			
		
			forward = new ActionForward();
			forward.setPath("./GeoView/roadname.jsp");
			forward.setRedirect(false);	
		}
		else if(command.equals("/markerview.do")){
			System.out.println(" G : /markerview.do ȣ��! ");
			//���� : ��ġ������ �������� �˻� (roadname.do) -> ��ǥ�� ���� ������ 1000���� �ٹ��� ��� ��ǥ�� ������ ��Ŀ�� ǥ�� -> Aftermain���� �̵��� ��� ����� ����Ʈ�� �����ش�.			
		
			forward = new ActionForward();
			forward.setPath("./GeoView/markerview.jsp");
			forward.setRedirect(false);	
		}
		else if(command.equals("/GeoMarkerAction.do")){
			System.out.println(" G : /GeoMarkerAction.do ȣ��! ");
			// ���� : ���� ���� ��ġ������ ���� �����ؼ�  ����� ����� ��ǥ�� �ش��ϴ� ��Ŀ�� ǥ���Ѵ�
			
			action = new GeoMarkerAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/AfterMain.do")){
			// ������������ �̵��ؼ� �������� ����Ʈ�� �����ش� ( ��� ��ȸ? / ����? )
			
			forward = new ActionForward();
			forward.setPath("./GeoView/AfterMain.jsp");
			forward.setRedirect(false);			
		}
		else if(command.equals("/GeoListAction.do")){
			// ������������ �̵��ؼ� �������� ����Ʈ�� �����ش� ( ��� ��ȸ? / ����? )
			
			action = new GeoListAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
				}	
		}
		
		
		
		


		
		
		
		
		
		
		
		System.out.println(" G : 2. ���� �ּ� ����(ó��) �� (�������̵�X)");
		// -----------------------2. ���� �ּ� ����(ó��) --------------------
		// -----------------------3. ������ �̵� -----------------------------
		// ������ �̵������� ������
		if(forward != null){
			if(forward.isRedirect()){ // true
				response.sendRedirect(forward.getPath());
				System.out.println(" G : ������ �ּ� - "+forward.getPath());
				System.out.println(" G : ������ �̵� (sendRedirect) ");
			}else{ // false
				RequestDispatcher dis =
						request.getRequestDispatcher(forward.getPath());
				dis.forward(request, response);
				System.out.println(" G : ������ �ּ� - "+forward.getPath());
				System.out.println(" G : ������ �̵� (forward) ");
			}
		}
		System.out.println(" G : 3. ������ �̵��� \n\n\n ");		
		// -----------------------3. ������ �̵� -----------------------------
	}
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GeoFrontController_doGet() ȣ��! ");
		// �������� GET������� ȣ��ɶ� ����Ǵ� �޼���
		doProcess(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GeoFrontController_doPost() ȣ��! ");
		// �������� POST������� ȣ��ɶ� ����Ǵ� �޼���
		doProcess(request, response);
	}

}
