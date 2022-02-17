package com.me.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.me.member.db.MemberDAO;

public class MemberModifyoldPwAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println(" M : MemberModifyoldPwAction_execute() 호출");
				
		// POST - 한글
		request.setCharacterEncoding("UTF-8");
		MemberDAO mdao = new MemberDAO();
		int result = 0;
		
		// 세션 체크
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		if(id == null) {
			ActionForward forward = new ActionForward();
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;
		}
		
		// 파라미터 저장
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
			out.print("alert('비밀번호가 정상적으로 처리되었습니다. 로그인페이지로 돌아갑니다');");
			out.print("location.href='./MemberLogin.me';");
			out.print("</script>");
			out.close();
			
			return null;
		} else if(result == -1){
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('비밀번호 오류입니다. 이전 페이지로 돌아갑니다.');");
			out.print("history.back();");
			out.print("</script>");
			out.close();
			
			return null;
		} else {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('접근할 수 없는 사용자입니다. 메인 페이지로 돌아갑니다.');");
			out.print("location.href='./Main.me';");
			out.print("</script>");
			out.close();
			
			return null;
		}
		
		
	}
}
