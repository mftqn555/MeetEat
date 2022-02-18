package com.me.member.action;

import java.io.File;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.me.member.db.MemberDAO;
import com.me.member.db.MemberDTO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class MemberUpdateProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		System.out.println(" M : MemberUpdateProAction_execute() ȣ��");
		
		// ���� üũ
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		ActionForward forward = new ActionForward();
		if(id == null) {
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;
			// �� �޼ҵ尡 ���� ���� Controller�� forward�� ����ȴ�.
		}
		
		// �ѱ� ó��
		request.setCharacterEncoding("UTF-8");
				
		// ���� ó��
		// �������� ������ localhost �ڿ� �ٴ� ��ġ
		String path = "/upload/member/";
		String realPath = request.getServletContext().getRealPath(path);
		System.out.println(" M : ������ ���� ���� ��� - "+realPath);
		
		// ���� �뷮 ����
		int maxSize = 10 * 1024 * 1024;
		
		// ���� ���� ���ε� ó��
		MultipartRequest multi = null;
		
		try {
			multi = new MultipartRequest(request, realPath, maxSize, "UTF-8", new DefaultFileRenamePolicy());
		} catch(Exception e) {
			response.setContentType("text/html; charset=UTF-8"); 
			PrintWriter out = response.getWriter();	
			out.print("<script>");
			out.print("alert('�̹��� ũ�Ⱑ 10MB�� �ʰ��մϴ�');");
			out.print("history.back();");
			out.print("</script>");
			out.close();
			
			return null; 
		}
		
		String fileName = "";
		File file = multi.getFile("profile_image");
		if(file != null) {
			String ext = file.getName().substring(file.getName().lastIndexOf(".")+1);
			if(ext.equals("jpg") || ext.equals("png") || ext.equals("gif")) {
				String prev = new MemberDAO().getMember(id).getProfile_image();
				File prevFile = new File(realPath+"/"+prev);
				if(prevFile.exists()) {
					prevFile.delete();
				}
				fileName = file.getName();
			} else {
				if(file.exists()) {
					file.delete();
				}
				response.setContentType("text/html; charset=UTF-8"); 
				PrintWriter out = response.getWriter();	
				out.print("<script>");
				out.print("alert('�̹��� ���ϸ� ���ε� �����մϴ�!');");
				out.print("history.back();");
				out.print("</script>");
				out.close();
				
				return null; 
			}
		}
		
		// �Ѿ���� ������ �ޱ�	
		String profile_image = multi.getFilesystemName("profile_image");
		String pw = multi.getParameter("pw");
		String nickname = multi.getParameter("nickname");
		String phone = multi.getParameter("phone");
		String email1 = multi.getParameter("email1");
		String email = multi.getParameter("email");
		String address = multi.getParameter("address");
		String detailAddress = multi.getParameter("detailAddress");
		String extraAddress = multi.getParameter("extraAddress");
		System.out.println(" M : profile_image : "+profile_image);
		
		if(id == null || id.equals("") || pw == null || pw.equals("")
				|| nickname == null || nickname.equals("") || phone == null || phone.equals("") 
				|| email1 == null || email1.equals("")|| email == null || email.equals("") 
				|| address == null || address.equals("")){
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('��� ������ �Է��ϼ���.');");
			out.print("history.back();");
			out.print("</script>");
			out.close();
			return null;
		}
		if(profile_image == null || profile_image.equals("")) {
			profile_image = "/upload/member/NoImage.png";
			System.out.println(" M : profile_image : "+profile_image);
		}
		
		
		// ���� ���������� ���޵� ���� ����(DTO)
		MemberDTO mdto = new MemberDTO();
		mdto.setProfile_image(path+profile_image);
		mdto.setId(id);
		mdto.setPw(pw);
		mdto.setNickname(nickname);
		mdto.setPhone(phone);
		mdto.setEmail(email1+"@"+email);
		mdto.setAddress(address+" "+detailAddress+extraAddress);
		
		System.out.println(" M : "+mdto.toString());
		
		// DAO ��ü ���� 
		MemberDAO mdao = new MemberDAO();
		int result = mdao.updateMember(mdto);
		System.out.println(" M : ȸ������ ���� �Ϸ�");
		
		if(result == 0) { // ��� ����
			response.setContentType("text/html; charset=UTF-8"); 
			PrintWriter out = response.getWriter();	
			out.print("<script>");
			out.print("alert('��й�ȣ�� �ٸ��ϴ�');");
			out.print("history.back();");
			out.print("</script>");
			out.close();
			
			return null; 
		} 
		else if(result == -1) { // ���� X
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('������ �������� �ʽ��ϴ�');");
			out.print("history.back();");
			out.print("</script>");
			out.close();
			
			return null;
		}
		
		// result == 1
		// ������
		// ������ �̵�
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print("<script>");
		out.print("alert('ȸ������ ������ �Ϸ�Ǿ����ϴ�. ó�� ȭ������ �ǵ��ư��ϴ�.');");
		out.print("location.href='./Main.me';");
		out.print("</script>");
		out.close();
		
		return null;
	}

}
