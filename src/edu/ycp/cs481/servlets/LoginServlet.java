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
			req.getRequestDispatcher("/login.jsp").forward(req, resp);
		}else{
			session.setAttribute("error", "You're already logged in!");
			resp.sendRedirect(req.getContextPath() + "/user_home");
		}
		// remove these on redirect so they don't stay persistent forever
		session.removeAttribute("resetPasswordSuccess");
		session.removeAttribute("verifyEmailSuccess");
		session.removeAttribute("verifyEmailFail");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		System.out.println("Login Servlet: doPost");
		
		String errorMessage = null;
		String email = null;
		String password = null;
		ArrayList<User> user = null;
		
		UserController uc = new UserController();
		
		email = req.getParameter("email");
		password = req.getParameter("password");
		
		if(email == null || password == null || email.equals("") || password.equals("")) {
			errorMessage = "Please specify both email and password"; 
		}else{
			user = uc.searchForUsers(-1, -1, false, email, false, null, false, null, -1, -1);
			boolean quarantineExists = uc.findQuarantineUser(email);
			if(user == null || user.size() == 0 || !uc.authenticate(user.get(0), password)){
				errorMessage = "Incorrect email or password";
			}
			if(quarantineExists) {
				errorMessage = "Please verify your account before logging in";
			}
			if(user.size() > 0 && uc.isLockedOut(user.get(0).getID())) {
				errorMessage = "This account is currently locked out";
			}
		}
		if(errorMessage != null){
			req.setAttribute("errorMessage", errorMessage);
			req.getRequestDispatcher("/login.jsp").forward(req, resp);
		}else{
			User u = user.get(0);
			HttpSession session = req.getSession();
			session.setAttribute("user_id", u.getID());
			resp.sendRedirect(req.getContextPath() + "/user_home");
		}	
	}
}
