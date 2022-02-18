package com.ad.admin.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ad.admin.db.AdminDAO;

public class AdminListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println(" M : MemberListAction_execute() ");
		
		// ��������
		HttpSession session = request.getSession();
		String id = (String ) session.getAttribute("id");
		
		ActionForward forward = new ActionForward();
		if(id == null || !id.equals("admin")){
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;
		}
		
		// �Ķ����, �ʱⰪ ����
		String col_name = request.getParameter("col_name");
		String id_nick = request.getParameter("id_nick");
		
		if(col_name == null || col_name == "") {
			col_name = "1";
		} 
		
		if(id_nick == null || id_nick == "") {
			id_nick = "1";
			col_name = "1";
		}
		
		
		System.out.println("col_name: " + col_name + " / id_nick: " + id_nick );
		
		
		
		// DAO ��ü ����
		AdminDAO adao = new AdminDAO();
		
		/* ����¡ ó�� */
		
		// �� �������� ��µ� ȸ�� ��
		int pageSize = 10;
	
		// �� ������ ���� ����
		String pageNum = request.getParameter("pageNum");
		if (pageNum == null) {
			pageNum = "1";
		}
	
		// ù���ȣ�� ���
		int currentPage = Integer.parseInt(pageNum);
		int startRow = (currentPage - 1) * pageSize + 1;
		
		// DB���� ����Ʈ �ޱ�, ��ü �� �� ��������
		ArrayList memberList = adao.getMemberList(col_name, id_nick, startRow, pageSize);
		
		int cnt = adao.getMemberCount();
		
		// ��ü �������� ���
		int pageCount = cnt / pageSize + (cnt % pageSize == 0 ? 0 : 1);

		// �� �������� ������ ��������
		int pageBlock = 2;

		// �� �������� ������ ������ �� ���۹�ȣ ���
		int startPage = ((currentPage - 1) / pageBlock) * pageBlock + 1;

		// �� �������� ������ ������ �� ����ȣ ���
		int endPage = startPage + pageBlock - 1;
		if (endPage > pageCount) {
			endPage = pageCount;
		}
		
		request.setAttribute("memberList", memberList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("pageBlock", pageBlock);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("startPage", startPage);
		request.setAttribute("startRow", startRow);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("cnt", cnt);
		request.setAttribute("endPage", endPage);

		forward = new ActionForward();		
		forward.setPath("./admin/memberlist.jsp");
		forward.setRedirect(false);
		return forward;
	}

}