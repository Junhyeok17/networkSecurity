package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import monitordao.monitordao;

@WebServlet("/logout")
public class logout extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		session.invalidate();

		monitordao mydb = new monitordao();
		String clientIP = getClientIP(request);
		String machine = InetAddress.getLocalHost().getHostName();
			
		try {
			mydb.insertAccessLog(id, clientIP, machine, "로그아웃");
			mydb.Close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PrintWriter out = response.getWriter();
		out.print("<script>");
		out.print("alert('로그아웃되었습니다.');");
		out.print("location.href = 'login'");
		out.print("</script>");	
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
