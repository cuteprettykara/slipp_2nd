package net.slipp.user;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/users/updateForm")
public class UpdateFormUserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userId = SessionUtils.getStringValue(session, LoginServlet.SESSION_USER_ID);

		if (userId == null) {
			response.sendRedirect("/");
			return;
		}
		
		System.out.println("User Id: " + userId);
		
		UserDAO userDao = new UserDAO();
		User user = null;
		
		try {
			user = userDao.findByUserId(userId);
			request.setAttribute("user", user);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/form.jsp");
			dispatcher.forward(request, response);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}
}
