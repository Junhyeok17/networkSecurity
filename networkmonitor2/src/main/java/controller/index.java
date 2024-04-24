package controller;

import java.io.IOException;
import java.io.PrintWriter;
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
import monitordto.resourcedto;

@WebServlet("/index")
public class index extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");

		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id"); 
		String adminflag = (String)session.getAttribute("adminflag");
		
		if(id==null) {
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('먼저 로그인 해야 합니다.');");
			out.print("location.href = 'login'");
			out.print("</script>");	
			return;
		}

		monitordao mydb = new monitordao();
		ArrayList<resourcedto> cpuUsage = null;
		ArrayList<resourcedto> memoryUsage = null;
		ArrayList<resourcedto> diskUsage = null;
		try {
			cpuUsage = mydb.searchCPUResource();
			memoryUsage = mydb.searchMemoryResource();
			diskUsage = mydb.searchDiskResource();
			request.setAttribute("cpuUsage", cpuUsage);
			request.setAttribute("memoryUsage", memoryUsage);
			request.setAttribute("diskUsage", diskUsage);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");

		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id"); 
		String adminflag = (String)session.getAttribute("adminflag");
		
		if(id==null) {
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('먼저 로그인 해야 합니다.');");
			out.print("location.href = 'login'");
			out.print("</script>");	
			return;
		}

		monitordao mydb = new monitordao();
		ArrayList<resourcedto> cpuUsage = null;
		ArrayList<resourcedto> memoryUsage = null;
		ArrayList<resourcedto> diskUsage = null;
		try {
			cpuUsage = mydb.searchCPUResource();
			memoryUsage = mydb.searchMemoryResource();
			diskUsage = mydb.searchDiskResource();
			request.setAttribute("cpuUsage", cpuUsage);
			request.setAttribute("memoryUsage", memoryUsage);
			request.setAttribute("diskUsage", diskUsage);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);
	}
}
