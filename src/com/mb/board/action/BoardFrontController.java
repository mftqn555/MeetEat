package com.mb.board.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BoardFrontController extends HttpServlet{

	protected void doProcess(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("BoardFrontController_doProcess() ȣ��! ");
		// �������� GET/POST��� ������� ȣ��ɶ� ����Ǵ� �޼���
		// *.me - ȸ������ ó�� ����
		
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
		
		if(command.equals("/write.mb")){
			System.out.println(" C : write.mb ȣ��! ");
			
			forward = new ActionForward();
			forward.setPath("./board/write.jsp");
			forward.setRedirect(false);			
		}
		else if(command.equals("/BoardWriteAction.mb")){
			System.out.println(" C : /BoardWriteAction.mb ȣ��! ");
		
			action = new BoardWriteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(command.equals("/list.mb")){
			System.out.println(" C : /BoardListActiom.mb ȣ��! ");
			
			action = new BoardListAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(command.equals("/read.mb")){
			System.out.println(" C : /BoardReadActiom.mb ȣ��! ");
			
			action = new BoardReadAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		else if(command.equals("/BoardSearch.mb")){
			System.out.println(" C : /BoardSearch.mb ȣ��! ");
			
			action = new BoardSearchAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		else if(command.equals("/delete.mb")){
			System.out.println(" C : /BoardDeleteAction.me ȣ��! ");
			
			action = new BoardDeleteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/modify.mb")){
			System.out.println(" C : /BoardModifyAction.me ȣ��! ");
			
			action = new BoardModifyAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/modifyPro.mb")){
			System.out.println(" C : /BoardModifyProAction.me ȣ��! ");
			
			action = new BoardModifyProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/Main.mb")){
			System.out.println(" C : /main.mb ȣ��! ");
			
			action = new MainListAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
		System.out.println(" C : 2. ���� �ּ� ����(ó��) �� (�������̵�X)");
		// -----------------------2. ���� �ּ� ����(ó��) --------------------
		// -----------------------3. ������ �̵� -----------------------------
		// ������ �̵������� ������
		if(forward != null){
			if(forward.isRedirect()){ // true
				response.sendRedirect(forward.getPath());
				System.out.println(" C : ������ �ּ� - "+forward.getPath());
				System.out.println(" C : ������ �̵� (sendRedirect) ");
			}else{ // false
				RequestDispatcher dis =
						request.getRequestDispatcher(forward.getPath());
				dis.forward(request, response);
				System.out.println(" C : ������ �ּ� - "+forward.getPath());
				System.out.println(" C : ������ �̵� (forward) ");
			}
		}
		System.out.println(" C : 3. ������ �̵��� \n\n\n ");		
		// -----------------------3. ������ �̵� -----------------------------
	}
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("BoardFrontController_doGet() ȣ��! ");
		// �������� GET������� ȣ��ɶ� ����Ǵ� �޼���
		doProcess(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("BoardFrontController_doPost() ȣ��! ");
		// �������� POST������� ȣ��ɶ� ����Ǵ� �޼���
		doProcess(request, response);
	}

}