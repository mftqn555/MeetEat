package com.bo.qna.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bo.qna.db.QnaDAO;

public class QnaListAction implements Action{
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("M : QnaListAction?�� execute() ?���?");
		
		
		// ?��?��?��?��
		HttpSession session = request.getSession();
		String id = (String ) session.getAttribute("id");
		
		QnaDAO qdao = new QnaDAO();	
		
		/* ?��?���? 처리 */
		
		// ?�� ?��?���??�� 출력?�� �? 개수
		int pageSize = 5;
	
		// ?�� ?��?���? ?���? ?��?��
		String pageNum = request.getParameter("pageNum");
		if (pageNum == null) {
			pageNum = "1";
		}
	
		// 첫행번호�? 계산
		int currentPage = Integer.parseInt(pageNum);
		int startRow = (currentPage - 1) * pageSize + 1;
		
		// DB?��?�� 리스?�� 받기, ?���? ?�� ?�� �??��?���?
		ArrayList qnaList = qdao.getQnaList(startRow, pageSize);
		
		int cnt = qdao.getQnaCount();
		
		// ?���? ?��?���??�� 계산
		int pageCount = cnt / pageSize + (cnt % pageSize == 0 ? 0 : 1);

		// ?�� ?��?���??�� 보여�? ?��?���?블럭
		int pageBlock = 2;

		// ?�� ?��?���??�� 보여�? ?��?���? 블럭 ?��?��번호 계산
		int startPage = ((currentPage - 1) / pageBlock) * pageBlock + 1;

		// ?�� ?��?���??�� 보여�? ?��?���? 블럭 ?��번호 계산
		int endPage = startPage + pageBlock - 1;
		if (endPage > pageCount) {
			endPage = pageCount;
		}
		
		request.setAttribute("qnaList", qnaList);
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
		
		//request.setAttribute("qnaList", qdao.getQnaList(startRow,pageSize));
		forward = new ActionForward();
		forward.setPath("./qna/qnalist.jsp");
		forward.setRedirect(false);
		return forward;
		
	}

}
