package net.slipp.user;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SaveUserServlet
 */
@WebServlet("/users/save")
public class SaveUserServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String email = request.getParameter("email");

		User user = new User(userId, password, name, email);
//		Database.addUser(user);
		
		UserDAO userDao = new UserDAO();
		
		try {
			userDao.addUser(user);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		response.sendRedirect("/");
	}

}
