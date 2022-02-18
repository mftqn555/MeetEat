package com.me.member.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.me.member.db.MemberDAO;

public class MemberListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		System.out.println(" M : MemberListAction_execute() ȣ��");
		
		// ���� ���� & ������ ����
		HttpSession session =  request.getSession();
		String id = (String) session.getAttribute("id");
		
		ActionForward forward = new ActionForward();
		if(id == null || !id.equals("admin")) {
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;
		}
		
		// DAO ��ü ����
		// getMemberList() - ��ü ȸ�� ��� ��������
		MemberDAO mdao = new MemberDAO();
		ArrayList memberList = mdao.getMemberList();
		
		// request ������ ���� ����
		request.setAttribute("memberList", memberList);
		
		// ./Member/list.jsp �� ���
		forward = new ActionForward();
		forward.setPath("./Member/list.jsp");
		forward.setRedirect(false);
	
		return forward;
	}

}