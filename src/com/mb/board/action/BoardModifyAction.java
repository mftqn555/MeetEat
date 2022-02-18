package com.mb.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mb.board.db.BoardDAO;
import com.mb.board.db.BoardDTO;

public class BoardModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("boardmodifyaction.java ����");
		
		int bno = Integer.parseInt(request.getParameter("bno"));
				
				
		BoardDAO dao = new BoardDAO();
		BoardDTO dto = new BoardDTO();
		
		System.out.println("ȣ��� �� ��ȣ : "+bno);
		
		request.setAttribute("dto", dao.getBoardDetail(bno));
		
		
		ActionForward forward = new ActionForward();
		forward.setPath("./board/modify.jsp");
		forward.setRedirect(false);		
		return forward;
	}

}