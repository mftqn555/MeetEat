package com.ad.admin.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ad.admin.db.ReportDAO;

public class AdminReportSortAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ReportDAO rdao = new ReportDAO();
		
		/* ������ ó�� �����ߴ��� Ȯ�ο� ��*/
		request.setAttribute("check", "1");
		
		/* �Ķ���� */
		String paramOption = request.getParameter("option");
		String paramUser = request.getParameter("userSelect");
		String paramId = request.getParameter("id");
		String paramSort = request.getParameter("sort");
	
		/* �ʱⰪ ����*/
		String option = (paramOption == null ? "report_date" : paramOption);
		String userSelect = (paramUser == null ? "1" : paramUser);
		String id = (paramId == null ? "1" : paramId);
		String sort = (paramSort == null ? "desc" : paramSort);
		
		/* where�� �ʿ���� �� �º�,�캯 1�� ó�� */
		id = (id == "" ? "1" : id);
		userSelect = (id == "1" ? "1" : userSelect); 
		
		/* ����¡ ó�� */
		
		// �� �������� ��µ� �� ����
		int pageSize = 5;
	
		// �� ������ ���� ����
		String pageNum = request.getParameter("pageNum");
		if (pageNum == null) {
			pageNum = "1";
		}
	
		// ù���ȣ�� ���
		int currentPage = Integer.parseInt(pageNum);
		int startRow = (currentPage - 1) * pageSize + 1;
		
		// DB���� ����Ʈ �ޱ�, ��ü �� �� ��������
		ArrayList reportList = rdao.sortReport(userSelect, id, option, sort, startRow, pageSize);
		
		int cnt = rdao.getReportCount();
		
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
		
		request.setAttribute("reportList", reportList);
		request.setAttribute("option", option);
		request.setAttribute("userSelect", userSelect);
		request.setAttribute("id", id);
		request.setAttribute("sort", sort);
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
		forward.setPath("./admin/reportManage.jsp");
		forward.setRedirect(false);
		
		return forward;
	}
	
}
