package com.me.member.action;

// �������� �̵��� �� �ʿ��� ������ �����ϴ� ��ü
// 1) ������ �̵�����(�ּ�) 
// 2) ������ �̵����
public class ActionForward {

	private String path;		// 1)
	private boolean isRedirect; // 2)
	// true - sendRedirect���
	// false - forward���
	
	// alt shift s + r
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isRedirect() {	// == get() booleanŸ���� get()�� �̷��� ���´�
		return isRedirect;
	}
	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
	
	
}