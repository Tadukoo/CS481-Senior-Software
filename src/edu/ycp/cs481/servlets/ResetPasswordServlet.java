
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
public class ResetPasswordServlet extends HttpServlet{
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		System.out.println("Reset Password Servlet: doGet");


		String email = req.getParameter("email");
		String token = req.getParameter("token");
		UserController uc = new UserController();
		
		if(email != null && token != null) {
			if(!uc.resetPassword(email, token)){
				req.setAttribute("errorMessage", "Incorrect token");
			}else{
				req.setAttribute("goodToken", "true");
				System.out.println("Good token");
			}
		}

		req.getRequestDispatcher("/reset_password.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		System.out.println("Reset Password Servlet: doPost");
		
		String errorMessage = null;
		String email = null;
		ArrayList<User> user = null;

		String action = req.getParameter("doThings");

		
		UserController uc = new UserController();
		
		email = req.getParameter("email");
		

		if(action.equalsIgnoreCase("sendEmail")) {
			if(email == null || email.equals("")) {
				errorMessage = "Please specify an email"; 
			}else{
				boolean exists = uc.findQuarantineUser(email);
				user = uc.searchForUsers(-1, -1, false, email, false, null, false, null, -1, -1);
				if(exists){
					errorMessage = "Please verify your account first!";
				}else if(user.size() == 0){
					errorMessage = "No account exists with this email!";
				}else{
					uc.resetPasswordEmail(email);
				}
			}
			if(errorMessage != null){
				req.setAttribute("errorMessage", errorMessage);
			}else{
				req.setAttribute("sendEmailSuccess", "Please check your email for a link to reset your password");
			}
			req.getRequestDispatcher("/reset_password.jsp").forward(req, resp);
		}else if(action.equalsIgnoreCase("changePassword")){
			String newPassword = req.getParameter("newPassword");
			String newPasswordConfirm = req.getParameter("newPasswordConfirm");
			
			if(!newPassword.equals(newPasswordConfirm)){
				req.setAttribute("errorMessage", "Passwords don't match!");
				// Reset request parameters for another password change attempt
				req.setAttribute("email", email);
				req.setAttribute("goodToken", "true");
				req.getRequestDispatcher("/reset_password.jsp").forward(req, resp);
			}else{
				HttpSession session = req.getSession();
				uc.changeUserPassword(email, newPassword);
				// TODO: Remove reset password token from db table
				session.setAttribute("successMessage", "Successfully changed password! You may now login.");
				resp.sendRedirect(req.getContextPath() + "/login");
			}
		}
	}

}