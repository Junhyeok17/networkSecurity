package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
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

@WebServlet("/login")
public class login extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
		response.sendRedirect("login.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        
		String id = request.getParameter("id");
		if(id==null) id="";
		String password = request.getParameter("password");
		if(password==null) password="";

        String SALT = "monitor";
		String passwd = password + SALT;
	
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
		String result = null;
		try {
			System.out.println("passwd : "+passwd);
			result = mydb.checkPassword(id, passwd);
			String clientIP = getClientIP(request);
			String machine = InetAddress.getLocalHost().getHostName();
			
			System.out.println("result : "+result);
			if(result.equals("full")) {
				mydb.Close();
			    PrintWriter out = response.getWriter();
			    out.print("<script>");
			    out.print("alert('�α��� �õ� ���� Ƚ���� �ʰ��߽��ϴ�. �����ڿ��� �����ϼ���.');");
			    out.print("location.href = 'login'");
			    out.print("</script>");	
			}
			else {
				if(result.equals("false")) {
					// �α��� ��� �α� �����
					mydb.insertAccessLog(id, clientIP, machine, "�α��� ����");
					// �α��� ���� �� �õ� Ƚ�� ����
					mydb.updateLoginAttempt(id, "����");
					mydb.Close();
				    PrintWriter out = response.getWriter();
				    out.print("<script>");
				    out.print("alert('���� ������ ��ġ���� �ʽ��ϴ�.');");
				    out.print("location.href = 'login'");
				    out.print("</script>");	
				}
				else {
					// �α��� ��� �α� �����
					mydb.insertAccessLog(id, clientIP, machine, "�α��� ����");
					// �α��� ���� �� �õ� Ƚ�� �ʱ�ȭ
					mydb.updateLoginAttempt(id, "����");
					mydb.Close();
					HttpSession session = request.getSession();
					session.setAttribute("id", id);
					session.setAttribute("adminflag", result);
					
				    PrintWriter out = response.getWriter();
				    out.print("<script>");
				    out.print("alert('�α��� ����.');");
				    out.print("location.href = 'index'");
				    out.print("</script>");	
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String getClientIP(HttpServletRequest request) {
	    String ip = request.getHeader("X-Forwarded-For");

	    if (ip == null) {
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if (ip == null) {
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if (ip == null) {
	        ip = request.getHeader("HTTP_CLIENT_IP");
	    }
	    if (ip == null) {
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
	    }
	    if (ip == null) {
	        ip = request.getRemoteAddr();
	    }
	    if(ip.equals("0:0:0:0:0:0:0:1") || ip.equals("127.0.0.1")) {
	    	try {
				ip = InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    return ip;
	}
}
