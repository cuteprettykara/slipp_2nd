package net.slipp.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/users/login")
public class LoginServlet extends HttpServlet {
	public static final String SESSION_USER_ID = "userId";

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter(SESSION_USER_ID);
		String password = request.getParameter("password");
		
		try {
			User.login(userId, password);
			request.getSession().setAttribute(SESSION_USER_ID, userId);
			response.sendRedirect("/");
			
		} catch(UserNotFoundException e) {
			forwardJSP(request, response, "�������� �ʴ� ������Դϴ�. �ٽ� �α����ϼ���.");
			
		} catch(PasswordMismatchException e) {
			forwardJSP(request, response, "��й�ȣ�� Ʋ���ϴ�. �ٽ� �α��� �ϼ���..");
		} 
	}

	private void forwardJSP(HttpServletRequest request, HttpServletResponse response, String errorMessage)
			throws ServletException, IOException {
		request.setAttribute("errorMessage", errorMessage);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
		dispatcher.forward(request, response);
	}
}
