package com.mb.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {
	// => Model(DB처리?��?��)?��?��?�� ?��?��?��?�� ?��?��?��?�� 객체
	// =>  처리?��?�� ?��?��?�� ?��?��(??)?�� 강제�? �??�� 
	// ?��?��,추상메서?��
	
	// => execute메서?��?�� ?��?��?�� request,response�? ?��?��받아?��
	//   처리?��?�� ActionForward�? 리턴?��?�� 메서?��
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception;
	
	//public int ABC(int a, int b);

}
