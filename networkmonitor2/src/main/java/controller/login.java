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
			    out.print("alert('로그인 시도 가능 횟수를 초과했습니다. 관리자에게 문의하세요.');");
			    out.print("location.href = 'login'");
			    out.print("</script>");	
			}
			else {
				if(result.equals("false")) {
					// 로그인 기록 로그 남기기
					mydb.insertAccessLog(id, clientIP, machine, "로그인 실패");
					// 로그인 실패 시 시도 횟수 증가
					mydb.updateLoginAttempt(id, "실패");
					mydb.Close();
				    PrintWriter out = response.getWriter();
				    out.print("<script>");
				    out.print("alert('계정 정보가 일치하지 않습니다.');");
				    out.print("location.href = 'login'");
				    out.print("</script>");	
				}
				else {
					// 로그인 기록 로그 남기기
					mydb.insertAccessLog(id, clientIP, machine, "로그인 성공");
					// 로그인 성공 시 시도 횟수 초기화
					mydb.updateLoginAttempt(id, "성공");
					mydb.Close();
					HttpSession session = request.getSession();
					session.setAttribute("id", id);
					session.setAttribute("adminflag", result);
					
				    PrintWriter out = response.getWriter();
				    out.print("<script>");
				    out.print("alert('로그인 성공.');");
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
