package com.me.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Interface - ������ Ʋ���� ��� ���� ������ �� �� �ְ� �ϱ� ����.
// 			       �� �ſ� ���� ������ ������ �ǰ�, ���� ������ ȭ���� �ȴ�. 
// 		 	      ������ �޶���������, Ʋ-���� ������ ���𰡸� ���� �ſ� ������ ������ ����. �� ���� �ٷ� �������̽��� ����!
// ������ ������ �߻�޼ҵ带 �������̵���Ű�� ���ؼ�. 
public interface Action {
	// -> Model(DB ó������)������ �ʿ��� �� ����ϴ� ��ü
	// -> ó���ϴ� ������ ����(Ʋ)�� ������ �ο�
	// ���, �߻� �޼ҵ�
	
	// => execute �޼ҵ�� ���� �� request, response�� ���޹޾Ƽ� ó�� �Ŀ� ActionForward�� �����ϴ� �޼ҵ�
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception;
	
	// public int ABC(int a, int b); �� ���� ���
	
}