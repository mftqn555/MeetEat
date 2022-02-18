package com.me.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.me.member.db.MemberDAO;
import com.me.member.db.MemberDTO;

public class MemberInfoAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		System.out.println(" M : MemberInfoAction_execute() ȣ��");
		
		// ID ������ ��������
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		ActionForward forward = new ActionForward();
		if(id == null) {
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;
		}
		
		// DAO ��ü ���� - ID�� �ش��ϴ� ����� ������ ��� ��������
		MemberDAO mdao = new MemberDAO();
		MemberDTO mdto = mdao.getMember(id);
		
		// view�������� ������ �����ϱ� ���ؼ� DTO ���� ����(request ������ ���� - �������̶� �� ��Ʈ)
		request.setAttribute("mdto", mdto);
		
		// info.jsp �������� �̵�
		forward.setPath("./Member/info.jsp");
		forward.setRedirect(false);
		
		return forward;
	}

	
}