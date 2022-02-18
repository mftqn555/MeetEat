package com.me.member.action;

import java.io.File;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.me.member.db.MemberDAO;
import com.me.member.db.MemberDTO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class MemberJoinAction implements Action{
	// ȸ������ ó��

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(" M : MemberJoinAction_execute() ȣ��!");
		
		// �ѱ� ó��
		request.setCharacterEncoding("UTF-8");
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String checkPw = request.getParameter("checkPw");
		String nickname = request.getParameter("nickname");
		String phone = request.getParameter("phone");
		String email1 = request.getParameter("email1");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String detailAddress = request.getParameter("detailAddress");
		String extraAddress = request.getParameter("extraAddress");
		String profile_image = "";

		if(id == null || id.equals("") || pw == null || pw.equals("") || checkPw == null || checkPw.equals("") 
				|| nickname == null || nickname.equals("") || phone == null || phone.equals("") 
				|| email1 == null || email1.equals("")|| email == null || email.equals("") 
				|| address == null || address.equals("") || detailAddress == null || detailAddress.equals("")){
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('��� ������ �Է��ϼ���.');");
			out.print("history.back();");
			out.print("</script>");
			out.close();
			return null;
		}
		if(!(pw.equals(checkPw))) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('��й�ȣ�� ���� ��ġ���� �ʽ��ϴ�.');");
			out.print("history.back();");
			out.print("document.join.checkPw.focus();");
			out.print("</script>");
			out.close();
			return null;
		}
		System.out.println(" M : MemberJoinAction - ��� ���� ���������� �Է¿Ϸ��");
		
		// DTO ��ü ����, ���޵� ������ ����
		MemberDTO mdto = new MemberDTO();
		mdto.setId(id);
		mdto.setPw(pw);
		mdto.setNickname(nickname);
		mdto.setPhone(phone);
		mdto.setEmail(email1+"@"+email);
		mdto.setAddress(address+" "+detailAddress+extraAddress);
		
		System.out.println(" M : "+mdto.toString());
		
		// DAO ��ü ����
		// ���޵� ������ DB�� ����
		MemberDAO mdao = new MemberDAO();
		int result = mdao.insertMember(mdto);
		System.out.println(" M : ȸ������ ���� �Ϸ�");
		
		if(result == 1) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('ȸ�������� ���������� ó���Ǿ����ϴ�. ó��ȭ������ �ǵ��ư��ϴ�.');");
			out.print("location.href='./Main.me';");
			out.print("</script>");
			out.close();
			return null;
		} else {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('�̹� �����ϴ� ȸ���Դϴ�. ����ȭ������ �ǵ��ư��ϴ�.');");
			out.print("history.back();");
			out.print("</script>");
			out.close();
			return null;
		}
	}
	
	
	
}