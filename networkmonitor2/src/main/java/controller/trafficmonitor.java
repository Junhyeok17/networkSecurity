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

@WebServlet("/trafficmonitor")
public class trafficmonitor extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		monitordao mydb = new monitordao();
		String trafficResult = "";
		try {
			trafficResult = mydb.searchNetTraffic();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mydb.Close();
		
		//response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(trafficResult);
        out.flush();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}
}
