package com.mb.board.action;

// �������� �̵��Ҷ� �ʿ��� ������ �����ϴ� ��ü
// 1) ������ �̵�����(�ּ�)
// 2) ������ �̵����
public class ActionForward {
	
	private String path;
	private boolean isRedirect;
	//   true - sendRedirect��� 
	//   false - forward���
	
	// alt shift s + r
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isRedirect() { //get()
		return isRedirect;
	}
	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
	

}
