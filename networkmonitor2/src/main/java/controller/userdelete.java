package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import monitordao.monitordao;

@WebServlet("/userdelete")
public class userdelete extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String activateId = request.getReader().readLine();
		String[] ids = activateId.split(",");
		monitordao mydb = new monitordao();
		for(int i=0;i<ids.length;i++) {
			try {
				mydb.deleteMember(ids[i]);
			} catch (SQLException e) {
				mydb.Close();
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		mydb.Close();
	}
}
