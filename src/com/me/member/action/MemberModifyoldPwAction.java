package com.me.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.me.member.db.MemberDAO;

public class MemberModifyoldPwAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println(" M : MemberModifyoldPwAction_execute() ȣ��");
				
		// POST - �ѱ�
		request.setCharacterEncoding("UTF-8");
		MemberDAO mdao = new MemberDAO();
		int result = 0;
		
		// ���� üũ
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		if(id == null) {
			ActionForward forward = new ActionForward();
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;
		}
		
		// �Ķ���� ����
		String oldPw = request.getParameter("oldPw");
		String pw = request.getParameter("pw");
		
		System.out.println("oldPw: " + oldPw);
		System.out.println("Pw: " + pw);
	
		result = mdao.modifyPw(id, oldPw, pw);
		
		if(result == 1) {
			
			session.invalidate();
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('��й�ȣ�� ���������� ó���Ǿ����ϴ�. �α����������� ���ư��ϴ�');");
			out.print("location.href='./MemberLogin.me';");
			out.print("</script>");
			out.close();
			
			return null;
		} else if(result == -1){
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('��й�ȣ �����Դϴ�. ���� �������� ���ư��ϴ�.');");
			out.print("history.back();");
			out.print("</script>");
			out.close();
			
			return null;
		} else {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('������ �� ���� ������Դϴ�. ���� �������� ���ư��ϴ�.');");
			out.print("location.href='./Main.me';");
			out.print("</script>");
			out.close();
			
			return null;
		}
		
		
	}
}