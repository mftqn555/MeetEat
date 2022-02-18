package com.me.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.me.member.db.MemberDAO;

public class MemberModifytempPwAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println(" M : MemberModifytempPwAction_execute() ȣ��");
		
		// POST - �ѱ�
		request.setCharacterEncoding("UTF-8");
		
		// ���� üũ
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		ActionForward forward = new ActionForward();
		if(id == null) {
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;
		}
		
		// �Ķ���� ����
		String oldPw = request.getParameter("oldPw");
		String pw = request.getParameter("pw");
		
		// DB ����
		MemberDAO mdao = new MemberDAO();
		int result = mdao.modifyPw(id, oldPw, pw);
		
		// ����� ���� �̵�
		if(result == 1) {
			
			session.invalidate();
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('��й�ȣ�� ���������� ó���Ǿ����ϴ�.');");
			out.print("location.href='./MemberLogin.me';");
			out.print("</script>");
			out.close();
			
			return null;
		} else {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('������ �� ���� ������Դϴ�. ó�� �������� �ǵ��ư��ϴ�.');");
			out.print("location.href='./Main.me';");
			out.print("</script>");
			out.close();
			
			return null;
		}
		
	}
}