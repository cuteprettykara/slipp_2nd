package net.slipp.user;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import net.slipp.support.MyValidatorFactory;

/**
 * Servlet implementation class SaveUserServlet
 */
@WebServlet("/users/save")
public class CreateUserServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		request.setCharacterEncoding("UTF-8");
		
		String userId = request.getParameter("userId");	// 4~12�� ����
		String password = request.getParameter("password");
		String name = request.getParameter("name");		// 2~10�� ����
		String email = request.getParameter("email");

		User user = new User(userId, password, name, email);
		Validator validator = MyValidatorFactory.createValidator();
		Set<ConstraintViolation<User>> constraintViolations = validator.validate( user );
		if (constraintViolations.size() > 0) {
			request.setAttribute("user", user);
			String errorMessage = constraintViolations.iterator().next().getMessage();
			forwardJSP(request, response, errorMessage);
			return;
		}
		
		UserDAO userDao = new UserDAO();
		
		try {
			userDao.addUser(user);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		response.sendRedirect("/");
	}
	
	private void forwardJSP(HttpServletRequest request, HttpServletResponse response, String errorMessage)
			throws ServletException, IOException {
		request.setAttribute("errorMessage", errorMessage);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/form.jsp");
		dispatcher.forward(request, response);
	}

}