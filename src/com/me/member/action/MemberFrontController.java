package com.me.member.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MemberFrontController extends HttpServlet{
										// HttpServlet�� ����� �� request, response�� �����´�.
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		System.out.println("MemberFrontController_doProcess() ȣ��!");
		// �������� GET/POST��� ������� ȣ��� �� ����Ǵ� �޼ҵ�
		// *.me(Ȯ����) - ������Ʈ �߿��� ȸ������ ó���� �ش��ϴ� ����
		
		// ---------------------------1. ���� �ּ� ��� (*.me, *.net, *.com ...)--------------------------------------
		
		String requestURI = request.getRequestURI();
		System.out.println(" C : requestURI - "+requestURI);
		//������Ʈ ��θ� �����ͼ� ����
		String ctxPath = request.getContextPath(); 
		System.out.println(" C : ctxPath - "+ctxPath);
		// requestURI���� ������Ʈ��(.)�� ������ ������ �ּ� ��������
		// ���� �ּҿ��� �ݵ�� '/'�� ���ԵǾ� �־�� �Ѵ�!
		String command = requestURI.substring(ctxPath.length());
		System.out.println(" C : command - "+command);
		System.out.println(" C : 1. ���� �ּ� ��� �Ϸ�");
		
		// ---------------------------1. ���� �ּ� ��� (*.me, *.net, *.com ...)--------------------------------------
		// ---------------------------2. ���� �ּ� ���� (ó��)-------------------------------------------------------
		
		Action action = null;
		ActionForward forward = null;
		
		// ���� ������(view) .me -> .jsp : false
		if(command.equals("/Main.me")) {
			forward = new ActionForward();
			forward.setPath("./Main/main.jsp");
			forward.setRedirect(false);
		} 
		// �α��� ������(view) .me -> .jsp : false
		else if(command.equals("/MemberLogin.me")) {
			forward = new ActionForward();
			forward.setPath("./Member/loginForm.jsp");
			forward.setRedirect(false);
		}
		// �α��� Pro������(model) : MemberLoginAction ��ü ����
		else if(command.equals("/MemberLoginAction.me")) {
			action = new MemberLoginAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// ȸ�� ���� ������(view) .me -> .jsp : false
		else if(command.equals("/MemberJoin.me")) {	
			forward = new ActionForward();
			forward.setPath("./Member/joinForm.jsp");	
			forward.setRedirect(false);
		}
		// ȸ������ Pro(model & view) .me -> .me : true
		else if(command.equals("/MemberJoinAction.me")) {
			action = new MemberJoinAction();
			try {
				forward = action.execute(request, response); 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// �α׾ƿ�(DB ���� X, view X) js�� main.me�� �̵�
		else if(command.equals("/MemberLogout.me")) {
			action = new MemberLogoutAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// ȸ������ Ȯ��(DB ���� O, view O) 
		else if(command.equals("/MemberInfo.me")) {
			// MemberInfoAction() ��ü ����
			action = new MemberInfoAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// ȸ������ ����(DB ����, view(EL ǥ����))
		else if(command.equals("/MemberUpdate.me")) {
			// MemberUpdateAction() ��ü ����
			action = new MemberUpdateAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// ȸ������ ����Pro(DB ó��, view�� �̵�(main.jsp))
		else if(command.equals("/MemberUpdateProAction.me")) {
			// MemberUpdateProAction() ��ü ����
			action = new MemberUpdateProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// ȸ�� Ż��(view) : false
		else if(command.equals("/MemberDelete.me")) {
			forward = new ActionForward();
			forward.setPath("./Member/deleteForm.jsp");
			forward.setRedirect(false);
		}
		// ȸ�� Ż��(DB O, view X) : js�� main.me�� �̵� 
		else if(command.equals("/MemberDeleteAction.me")) {
			action = new MemberDeleteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// ������ ȸ����� (DB O, view O)
		else if(command.equals("/MemberList.me")) {			
			action = new MemberListAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// ���̵� �ߺ� üũ(DB O, view X) js
		else if(command.equals("/MemberIdCheck.me")) {
			action = new MemberIdCheckAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// �г��� �ߺ� �˻�(DB O, view X) js
		else if(command.equals("/MemberNicknameCheck.me")) {
			action = new MemberNicknameCheckAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		///// ���
		else if (command.equals("/MemberReport.me")) {
			action = new MemberReportAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		else if (command.equals("/MemberReportAction.me")) {
			action = new MemberReportProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		// �н����� ã�� ������(view) .me -> .jsp (false)
		else if(command.equals("/MemberFindPwAction.me")) {
			forward = new ActionForward();
			forward.setPath("./Member/findPwForm.jsp");
			forward.setRedirect(false);
		}
		
		// �н����� ã��Pro(DB O, view O) - �н����� ���� ������(view) .me -> .jsp(false)
		else if(command.equals("/MemberFindPwProAction.me")) {
			action = new MemberFindPwProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// �ӽ��н����� ����������(view) - .me -> .jsp(false)
		else if(command.equals("/MemberModifytempPw.me")) {
			forward = new ActionForward();
			forward.setPath("./Member/changePw.jsp");
			forward.setRedirect(false);
		}
		// �ӽ��н����� ���� Pro(DB O, view x) 
		else if(command.equals("/MemberModifytempPwAction.me")) {
			action = new MemberModifytempPwAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// id ã�� ������(view O) .me -> .jsp (false)
		else if(command.equals("/MemberFindIdAction.me")) {
			forward = new ActionForward();
			forward.setPath("./Member/findIdForm.jsp");
			forward.setRedirect(false);
		}
		// id Pro������(DB O, view O)
		else  if(command.equals("/MemberFindIdProAction.me")) {
	 		action = new MemberFindIdProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// pw ���� ������
		else if(command.equals("/MemberModifyoldPw.me")) {
			forward = new ActionForward();
			forward.setPath("./Member/modifyoldPw.jsp");
			forward.setRedirect(false);
		}
		
		else if(command.equals("/MemberModifyoldPwAction.me")) {
			action = new MemberModifyoldPwAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		else if(command.equals("/MemberLoginCheckAction.me")) {
			action = new MemberLoginCheckAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
		//// ���

		System.out.println(" C : 2. ���� �ּ� ��Ī(ó��) �� (������ �̵� ���� ���� ��)");
		
		// ---------------------------2. ���� �ּ� ���� (ó��)-------------------------------------------------------
		// ---------------------------3. ������ �̵�---------------------------------------------------------------
		
		// ������ �̵������� ���� ���� �̵�.
		if(forward != null) {
			// if���� ���� ��!
			if(forward.isRedirect()) { // true
				response.sendRedirect(forward.getPath());
				System.out.println(" C : ������ �ּ� - "+forward.getPath());
				System.out.println(" C : ������ �̵� (sendRedirect)");
			} else { // false
				RequestDispatcher dis = request.getRequestDispatcher(forward.getPath());
				dis.forward(request, response);
				System.out.println(" C : ������ �ּ� - "+forward.getPath());
				System.out.println(" C : ������ �̵� (forward)");
			}
			
		}
		System.out.println(" C : 3. ������ �̵� �Ϸ�  \n\n\n");
		
		// ---------------------------3. ������ �̵�---------------------------------------------------------------

		
	
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("MemberFrontController_doGet() ȣ��!");
		// �������� GET������� ȣ��� �� ����Ǵ� �޼ҵ�
	
		doProcess(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		System.out.println("MemberFrontController_doPost() ȣ��!");
		// �������� POST������� ȣ��� �� ����Ǵ� �޼ҵ�
		
		doProcess(request, response);
	}

	
}