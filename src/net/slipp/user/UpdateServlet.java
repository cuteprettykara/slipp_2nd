package net.slipp.user;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.beanutils.BeanUtilsBean;

import net.slipp.support.MyValidatorFactory;

@WebServlet("/users/update")
public class UpdateServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String sessionUserId = SessionUtils.getStringValue(session, LoginServlet.SESSION_USER_ID);
		
		if (sessionUserId == null) {
			response.sendRedirect("/");
			return;
		}
		
		User user = new User(); 
		try {
			BeanUtilsBean.getInstance().populate(user, request.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e1) {
			throw new ServletException(e1);
		}
		
		if (!user.isSameUser(sessionUserId)) {
			response.sendRedirect("/");
			return;
		}
		
		Validator validator = MyValidatorFactory.createValidator();
		Set<ConstraintViolation<User>> constraintViolations = validator.validate( user );
		if (constraintViolations.size() > 0) {
			request.setAttribute("user", user);
			request.setAttribute("isUpdate", true);
			String errorMessage = constraintViolations.iterator().next().getMessage();
			forwardJSP(request, response, errorMessage);
			return;
		}
		
		UserDAO userDao = new UserDAO();
		
		try {
			userDao.updateUser(user);
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
