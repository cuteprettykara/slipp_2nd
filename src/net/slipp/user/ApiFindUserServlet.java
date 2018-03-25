package net.slipp.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet("/api/users/find")
public class ApiFindUserServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("userId");
		
		if (userId == null) {
			response.sendRedirect("/");
			return;
		}
		
		UserDAO userDao = new UserDAO();
		try {
			User user = userDao.findByUserId(userId);
			if (user == null) {
				return;
			}
			
//			Gson gson = new Gson();
			final GsonBuilder builder = new GsonBuilder();
		    builder.excludeFieldsWithoutExposeAnnotation();
		    final Gson gson = builder.create();
		    
			String jsonData = gson.toJson(user);
			
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(jsonData);
			
		} catch (SQLException e) {
			
		}
	}
}
