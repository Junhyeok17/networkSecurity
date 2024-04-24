package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import monitordao.monitordao;
import monitordto.memberdto;

@WebServlet("/changepassword")
public class changepassword extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");

		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id"); 
		String adminflag = (String)session.getAttribute("adminflag");

		if(id==null) {
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('���� �α��� �ؾ� �մϴ�.');");
			out.print("location.href = 'login'");
			out.print("</script>");	
			return;
		}

        String curpw = request.getParameter("curpw");
        if(curpw==null) curpw="";
        String newpw = request.getParameter("newpw");
        if(newpw==null) newpw="";
        
        if(curpw.equals("") || newpw.equals("")) {
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('��й�ȣ�� ��� �Է��ϼ���.');");
			out.print("location.href = 'usersearch'");
			out.print("</script>");	
        }
        

        String SALT = "monitor";
		String hashedcurpw = curpw + SALT;
	
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.reset();
			byte[] hashInBytes = md.digest(hashedcurpw.getBytes());
			StringBuilder sb = new StringBuilder(); 
			for (byte b : hashInBytes) { 
				sb.append(String.format("%02x", b)); 
			}
			hashedcurpw = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String hashednewpw = newpw + SALT;

		try {
			md = MessageDigest.getInstance("SHA-256");
			md.reset();
			byte[] hashInBytes = md.digest(hashednewpw.getBytes());
			StringBuilder sb = new StringBuilder(); 
			for (byte b : hashInBytes) { 
				sb.append(String.format("%02x", b)); 
			}
			hashednewpw = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		monitordao mydb = new monitordao();
		try {
			boolean result = mydb.changePassword(id, hashedcurpw, hashednewpw);
			if(result) {
				PrintWriter out = response.getWriter();
				out.print("<script>");
				out.print("alert('��й�ȣ�� ����Ǿ����ϴ�.');");
				out.print("location.href = 'usersearch'");
				out.print("</script>");	
			}
			else {
				PrintWriter out = response.getWriter();
				out.print("<script>");
				out.print("alert('���� ��й�ȣ�� ��ġ���� �ʽ��ϴ�.');");
				out.print("location.href = 'usersearch'");
				out.print("</script>");	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
