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
import monitordto.memberdto;

@WebServlet("/usersearch")
public class usersearch extends HttpServlet {
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

		String tpage = request.getParameter("tpage");
		if(tpage==null) tpage="1";
		
		monitordao mydb = new monitordao();
		try {
			ArrayList<memberdto> list = mydb.searchAllMember(Integer.parseInt(tpage));
			String paging = mydb.getUserPageNumber(Integer.parseInt(tpage));
			mydb.Close();
			request.setAttribute("page", paging);
			request.setAttribute("list", list);
			mydb.Close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("/usersearch.jsp");
		dispatcher.forward(request, response);
	}
}
