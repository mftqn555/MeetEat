package com.ad.admin.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ad.admin.db.ReportDAO;


public class AdminBanCancelProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		ActionForward forward = new ActionForward();
		if(id == null || !id.equals("admin")) {
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;
		}
		
		String reported_user = request.getParameter("reported_user");
		
		ReportDAO rdao = new ReportDAO();
		int result = rdao.banCancelMember(reported_user);
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		if(result == 1) {
			out.print("<script>");
			out.print("alert('�������� �Ϸ�');");
			out.print("location.href = document.referrer; "); // �ڷΰ��� + ���ΰ�ħ
			out.print("</script>");
			out.close();
		} else if (result == 0) {
			out.print("<script>");
			out.print("alert('�����Ǿ� ���� ���� ���̵��Դϴ�.');");
			out.print("location.href = document.referrer; "); 
			out.print("</script>");
			out.close();
		} else {
			out.print("<script>");
			out.print("alert('����!.');");
			out.print("location.href = document.referrer; ");
			out.print("</script>");
			out.close();
		}
		return null;
		
	}
	
}
