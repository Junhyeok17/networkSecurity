package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import monitordao.monitordao;

@WebServlet("/connectionmonitor")
public class connectionmonitor extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		monitordao mydb = new monitordao();
		String networkstatus = "";
		try {
			networkstatus = mydb.searchNetStatus();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mydb.Close();
		System.out.println(networkstatus);
        PrintWriter out = response.getWriter();
        out.print(networkstatus);
        out.flush();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}
}
