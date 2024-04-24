package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import monitordao.monitordao;

@WebServlet("/useractivate")
public class useractivate extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String activateId = request.getReader().readLine();
		String[] ids = activateId.split(",");
		monitordao mydb = new monitordao();
		for(int i=0;i<ids.length;i++) {
			try {
				mydb.updateUserStatus(ids[i]);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		mydb.Close();
	}
}
