package com.mb.board.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mb.board.db.BoardDAO;

public class BoardSearchAction implements Action{

	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("UTF-8");
		
		request.setAttribute("check", "1");
		
		BoardDAO bdao = new BoardDAO();
		
		String col = request.getParameter("col");
		String value = request.getParameter("value");
		
		if(col == null || col == "") {
			col = "1";
		}
		
		if(value == null || value == "") {
			value = "1";
			col = "1";
		}
		
		System.out.println("col: " + col + " / " + "value: " + value);
		
		/* ����¡ ó�� */
		
		// �� �������� ��µ� �� ����
		int pageSize = 8;
	
		// �� ������ ���� ����
		String pageNum = request.getParameter("pageNum");
		if (pageNum == null) {
			pageNum = "1";
		}
	
		// ù���ȣ�� ���
		int currentPage = Integer.parseInt(pageNum);
		int startRow = (currentPage - 1) * pageSize + 1;
		
		// DB���� ����Ʈ �ޱ�, ��ü �� �� ��������
		ArrayList boardList = bdao.searchBoardList(col, value, startRow, pageSize);
		
		int cnt = bdao.getBoardCount();
		
		// ��ü �������� ���
		int pageCount = cnt / pageSize + (cnt % pageSize == 0 ? 0 : 1);

		// �� �������� ������ ��������
		int pageBlock = 5;

		// �� �������� ������ ������ �� ���۹�ȣ ���
		int startPage = ((currentPage - 1) / pageBlock) * pageBlock + 1;

		// �� �������� ������ ������ �� ����ȣ ���
		int endPage = startPage + pageBlock - 1;
		if (endPage > pageCount) {
			endPage = pageCount;
		}
		
		request.setAttribute("boardList", boardList);
		request.setAttribute("col", col);
		request.setAttribute("value", value);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("pageBlock", pageBlock);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("startPage", startPage);
		request.setAttribute("startRow", startRow);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("cnt", cnt);
		request.setAttribute("endPage", endPage);
		
		ActionForward forward = new ActionForward();
		forward = new ActionForward();
		forward.setPath("./board/list.jsp");
		forward.setRedirect(false);
		
		return forward;
	}
	
	
}