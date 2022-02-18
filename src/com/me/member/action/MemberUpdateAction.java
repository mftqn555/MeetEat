package com.me.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.me.member.db.MemberDAO;
import com.me.member.db.MemberDTO;

public class MemberUpdateAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {

		System.out.println(" M : MemberUpdateAction_execute() ȣ��");
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		// ���� üũ
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		ActionForward forward = new ActionForward();
		if(id == null) {
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;
		}
		
		// DAO ��ü ���� - id�� �ش��ϴ� ȸ������ ��������
		MemberDAO mdao = new MemberDAO();
		MemberDTO mdto = new MemberDTO();
		mdto = mdao.getMember(id);
		
		// �ش����� request ������ ����
		request.setAttribute("mdto", mdto);
		
		// DB���� ���޵� ������ ó������ �ٷ� view�������� ������ ��
		// request.setAttribute("mdto", mdao.getMember(id));
		 	// MemberDTO mdto = mdao.getMember(id); ��
			// request.setAttribute("mdto", mdto); �� ��ģ �ڵ�
		
		// ������ �̵�(updateForm.jsp)
		forward.setPath("./Member/updateForm.jsp");
		forward.setRedirect(false);
		
		return forward;
	}

	
}