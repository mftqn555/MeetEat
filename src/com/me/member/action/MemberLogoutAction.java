package com.me.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MemberLogoutAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		System.out.println(" M : MemberLogoutAction_execute() ȣ��");
		
		// �������� �ʱ�ȭ
		HttpSession session = request.getSession();
		session.invalidate();
		
		// ���� �������� �̵�(main������)
		// alertâ ����� �̵� : java����  js �̿� -> contentType���� �ٲ۴�
		// response�� ����� �� �ִ� ��� ����
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		out.print("<script>");
		out.print("alert('���������� �α׾ƿ��Ǿ����ϴ�. ó��ȭ������ ���ư��ϴ�.');");
		out.print("location.href='./Main.me';");
		out.print("</script>");
		out.close();
		
		return null;
	}

	
}