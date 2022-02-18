package com.me.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.me.member.db.MemberDAO;
import com.me.member.db.MemberDTO;

public class MemberDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		System.out.println(" M : MemberDeleteAction_execute() ȣ��");
		
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		String pw = request.getParameter("pw");
		
		MemberDAO mdao = new MemberDAO();
		int result = mdao.deleteMember(id, pw);
		
		// js
		if(result == 0) {
			response.setContentType("text/html; charset=UTF-8"); 
			PrintWriter out = response.getWriter();	
			out.print("<script>");
			out.print("alert('��й�ȣ�� Ʋ���ϴ�');");
			out.print("history.back();");
			out.print("</script>");
			out.close();
			
			return null; 
		} 
		else if(result == -1) { // ���� X
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('������ �������� �ʽ��ϴ�');");
			out.print("history.back();");
			out.print("</script>");
			out.close();
			
			return null;
		}
		// ������ �̵�
		session.invalidate();
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print("<script>");
		out.print("alert('���������� Ż��Ǿ����ϴ�. ó��ȭ������ �ǵ��ư��ϴ�.');");
		out.print("location.href='./Main.me';");
		out.print("</script>");
		out.close();
		
		return null;
	
	}


}