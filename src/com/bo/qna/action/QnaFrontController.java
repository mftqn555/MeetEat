package com.bo.qna.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class QnaFrontController extends HttpServlet{
	
	protected void doProcess(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("QnaFrontController_doProcess() ?���? ");
		// ?��?���?�? GET/POST방식 ?���??��?�� ?��출될?�� ?��?��?��?�� 메서?��
		
		// -----------------------1. �??�� 주소 계산 --------------------------		
		String requestURI = request.getRequestURI();
		System.out.println(" C : requestURI - "+requestURI);
		String ctxPath = request.getContextPath();
		System.out.println(" C : ctxPath - "+ctxPath);
		String command = requestURI.substring(ctxPath.length());
		System.out.println(" C : command - "+command);
		System.out.println(" C : 1. �??�� 주소 계산 ?��! ");
		// -----------------------1. �??�� 주소 계산 --------------------------
		// -----------------------2. �??�� 주소 매핑(처리) --------------------
		
		Action action = null;
		ActionForward forward = null;
		
		if(command.equals("/QnaList.bo")){
			System.out.println(" C : /QnaList.bo ?���?");
			action = new QnaListAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/QnaWrite.bo")) {
			System.out.println(" C : /QnaWrite.bo ?���?! ");
			
			forward = new ActionForward();
			forward.setPath("./qna/qnaWrite.jsp");
			forward.setRedirect(false);
			
		}else if(command.equals("/QnaAddAction.bo")) {
			System.out.println( "C : /QnaAddAction.bo ?���?");
			
			action = new QnaAddAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/QnaContent.bo")) {
			System.out.println( "C : /QnaContent.bo ?���?");
			
			action = new QnaContentAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/QnaUpdate.bo")) {
			System.out.println("C : /QnaUpdate.bo ?���?");
			
			action = new QnaUpdateAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/QnaUpdateProAction.bo")) {
			System.out.println("C : /QnaUpdateProAction.bo ?���?");
			
			action = new QnaUpdateProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/QnaDelete.bo")) {
			System.out.println("C : /QnaDelete.bo ?���?");
			
			action = new QnaDeleteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/QnaReply.bo")) {
			System.out.println(" C : /QnaReply.bo ?���? ");
			
			forward = new ActionForward();
			forward.setPath("./qna/qnaReply.jsp");
			forward.setRedirect(false);
		}else if(command.equals("/QnaReplyProAction.bo")) {
			System.out.println("C : /QnaReplyProAction.bo ?���?");
			
			action = new QnaReplyProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
		
		
		
	// -----------------------3. ?��?���? ?��?�� -----------------------------
	// ?��?���? ?��?��?��보�? ?��?��?��
	if(forward != null){
		if(forward.isRedirect()){ // true
			response.sendRedirect(forward.getPath());
			System.out.println(" C : ?��?���? 주소 - "+forward.getPath());
			System.out.println(" C : ?��?���? ?��?�� (sendRedirect) ");
		}else{ // false
			RequestDispatcher dis =
					request.getRequestDispatcher(forward.getPath());
			dis.forward(request, response);
			System.out.println(" C : ?��?���? 주소 - "+forward.getPath());
			System.out.println(" C : ?��?���? ?��?�� (forward) ");
		}
	}
	System.out.println(" C : 3. ?��?���? ?��?��?�� \n\n\n ");		
	// -----------------------3. ?��?���? ?��?�� -----------------------------
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("QnaFrontController_doGet() ?���?");
		// ?��?���?�? GET 방식?���? ?��출될 ?�� ?��?��?��?�� 메서?��
		doProcess(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("QnaFrontController_doPost() ?���?");
		// ?��?���?�? POST 방식?���? ?��출될 ?�� ?��?��?��?�� 메서?��
		doProcess(request, response);
	}

}
