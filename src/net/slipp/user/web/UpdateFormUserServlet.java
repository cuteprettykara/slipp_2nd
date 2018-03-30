package net.slipp.user.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.SessionUtils;
import net.slipp.user.User;
import net.slipp.user.UserDAO;

@WebServlet("/users/updateForm")
public class UpdateFormUserServlet extends HttpServlet {
	static final Logger logger = LoggerFactory.getLogger(UpdateFormUserServlet.class);
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userId = SessionUtils.getStringValue(session, LoginServlet.SESSION_USER_ID);

		if (userId == null) {
			response.sendRedirect("/");
			return;
		}
		
		logger.debug("User Id: {}", userId);
		
		UserDAO userDao = new UserDAO();
		User user = null;
		
		user = userDao.findByUserId(userId);
		request.setAttribute("user", user);
		request.setAttribute("isUpdate", true);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/form.jsp");
		dispatcher.forward(request, response);
		
	}
}
