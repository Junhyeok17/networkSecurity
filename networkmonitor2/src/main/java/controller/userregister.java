package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import monitordao.monitordao;

@WebServlet("/userregister")
public class userregister extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");

		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id"); 
		String adminflag = (String)session.getAttribute("adminflag");
		
		if(adminflag.equals("Y")) {
	        response.sendRedirect("userregister.jsp");
		}
		else {
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('관리자만 접근할 수 있습니다.');");
			out.print("location.href = 'index'");
			out.print("</script>");	
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        System.out.println("post");
        String id = request.getParameter("userid");
        if(id==null) id="";
        String nickname = request.getParameter("nickname");
        if(nickname==null) nickname="";
        String usermail = request.getParameter("usermail");
        if(usermail==null) usermail="";
        String userpw = request.getParameter("userpw");
        if(userpw==null) userpw="";
        String adminflag = request.getParameter("adminflag");
        if(adminflag==null) adminflag="";
        
        if(id.equals("") || nickname.equals("") || usermail.equals("") || userpw.equals("")) {
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('모든 정보를 입력하세요.');");
			out.print("location.href = 'userregister'");
			out.print("</script>");	
			return;
        }
        
        String SALT = "monitor";
		String passwd = userpw + SALT;
	
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.reset();
			byte[] hashInBytes = md.digest(passwd.getBytes());
			StringBuilder sb = new StringBuilder(); 
			for (byte b : hashInBytes) { 
				sb.append(String.format("%02x", b)); 
			}
			passwd = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		monitordao mydb = new monitordao();
		try {
			mydb.registerMember(nickname, id, usermail, passwd, adminflag);
	        mydb.Close();
			
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('사용자 등록이 완료되었습니다.');");
			out.print("location.href = 'userregister'");
			out.print("</script>");	
		} catch (SQLException e) {
	        mydb.Close();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
