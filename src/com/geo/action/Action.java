package com.geo.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {
	// => Model(DBó������)������ �ʿ��Ҷ� ����ϴ� ��ü
	// =>  ó���ϴ� ������ ����(Ʋ)�� ������ �ο� 
	// ���,�߻�޼���
	
	// => execute�޼���� ����� request,response�� ���޹޾Ƽ�
	//   ó���Ŀ� ActionForward�� �����ϴ� �޼���
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception;
	
	//public int ABC(int a, int b);

}
