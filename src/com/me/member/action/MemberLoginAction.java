package com.me.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.me.member.db.MemberDAO;


public class MemberLoginAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println(" M : MemberLoginAction_execute() ȣ��");
		
		// �ѱ� ó��
		request.setCharacterEncoding("UTF-8");
		
		// ���޵� �Ķ���� ����
		String paramId = request.getParameter("inputId");
		String paramPw = request.getParameter("inputPw");
		
		String inputId = (paramId == null ? "" : paramId);
		String inputPw = (paramPw == null ? "" : paramPw);
		
		System.out.println("inputId: " + inputId + "\n inputPw: " + inputPw);
		
		MemberDAO mdao = new MemberDAO();
		int result = mdao.loginCheck(inputId, inputPw);
		
		if(result == 0) {
			return null;	
		} 
		else if(result == -1) {
			return null;
		}

		
		// ó�� ����� ���� ������ �̵� -> �ڹٽ�ũ��Ʈ ���(�ڹٿ��� �ڹٽ�ũ��Ʈ��?)
		// ���� �� ����(�α��� ID)
		HttpSession session = request.getSession();
		session.setAttribute("id", inputId);
		
		// ������ �̵� - ActionForward
		ActionForward forward = new ActionForward();
		forward.setPath("./Main.me");
		forward.setRedirect(true);
		return forward;
	}		
}