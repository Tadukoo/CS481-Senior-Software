package edu.ycp.cs481.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ycp.cs481.control.UserController;
import edu.ycp.cs481.model.User;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet{
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		System.out.println("Login Servlet: doGet");
		HttpSession session = req.getSession();
		if(session.getAttribute("user_id") == null){
			// Grab success message from session data, put into request, and clear it
			req.setAttribute("successMessage", session.getAttribute("successMessage"));
			session.removeAttribute("successMessage");
			// Grab error message from session data, put into request, and clear it
			req.setAttribute("errorMessage", session.getAttribute("errorMessage"));
			session.removeAttribute("errorMessage");

			req.getRequestDispatcher("/login.jsp").forward(req, resp);
		}else{
			session.setAttribute("error", "You're already logged in!");
			resp.sendRedirect(req.getContextPath() + "/user_home");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		System.out.println("Login Servlet: doPost");

		String errorMessage = null;
		String email = null;
		String password = null;

		ArrayList<User> userSearch = null;
		int id = -1;

		UserController uc = new UserController();

		email = req.getParameter("email");
		password = req.getParameter("password");

		if(email == null || password == null || email.equals("") || password.equals("")){
			errorMessage = "Please specify both email and password"; 
		}else if(uc.findQuarantineUser(email)){
			errorMessage = "Account hasn't been verified!";
		}else{
			userSearch = uc.searchForUsers(-1, -1, false, email, false, null, false, null, -1, -1);
			if(userSearch == null || userSearch.size() == 0 || !uc.authenticate(password, userSearch.get(0).getPassword())){
				errorMessage = "Incorrect email or password!";
			}else{
				id = userSearch.get(0).getID();
				if(uc.isLockedOut(id)){
					errorMessage = "This account is currently locked out!";
				}
			}
		}

		if(errorMessage != null){
			req.setAttribute("errorMessage", errorMessage);
			req.getRequestDispatcher("/login.jsp").forward(req, resp);
		}else{
			HttpSession session = req.getSession();
			session.setAttribute("user_id", id);
			resp.sendRedirect(req.getContextPath() + "/user_home");
		}	
	}
}
