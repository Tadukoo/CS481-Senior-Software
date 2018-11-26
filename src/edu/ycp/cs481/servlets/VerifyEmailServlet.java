
package edu.ycp.cs481.servlets;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ycp.cs481.control.UserController;



@SuppressWarnings("serial")
public class VerifyEmailServlet extends HttpServlet{
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		System.out.println("Verify Email Servlet: doGet");

		
		String email = req.getParameter("email");
		String token = req.getParameter("token");
		UserController uc = new UserController();
		
		boolean verify = uc.verifyUser(email, token);
		HttpSession session = req.getSession();
		
		if(!verify){
			session.setAttribute("errorMessage", "Email verification failed");
		}else{
			session.setAttribute("successMessage", "Email successfully verified");
		}
        resp.sendRedirect(req.getContextPath() + "/login");
	}
}

