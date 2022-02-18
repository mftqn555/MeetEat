package com.me.chat.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChatController extends HttpServlet{
	private static final long serialVersionUID = 1L;
    
	public void init(ServletConfig config) 
			throws ServletException {}

	protected void service(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		//ServletContext application = request.getServletContext();	//	���� ��ü
		
		//	������ ������ ���� ���� �ð� ���� ����. ���� ��ü�̴�.
		HttpSession session = request.getSession();					 
		
		//	1. �������� �����ϱ� ���� url�� �м��ϰ� �з��ؼ� ����
		String url = request.getRequestURI();
		System.out.println("url : " +url);
		String contextPath = request.getContextPath();
		System.out.println("contextPath : " +contextPath);
		String command = url.substring(contextPath.length());
		System.out.println(" C : command - " +command);
		System.out.println(" C : 1. ���� �ּ� ��� �Ϸ�");
		
		//	�� ���������� ä�� ��ư�� Ŭ���Ǹ�
		if (command.equals("/chatpage.chat")) {
			// 2. �з��� url�� �����ϰ� �����ϴ� �۾�
			RequestDispatcher dispatcher = request.getRequestDispatcher("./Chat/chatpage.jsp");
					
			//	3. �������� �о��ִ� �۾�
			dispatcher.forward(request, response);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		service(request, response);

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		service(request, response);
	}
	
}