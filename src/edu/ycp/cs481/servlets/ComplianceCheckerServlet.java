package edu.ycp.cs481.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ycp.cs481.control.ComplianceController;
import edu.ycp.cs481.control.UserController;
import edu.ycp.cs481.model.ComplianceIssue;
import edu.ycp.cs481.model.EnumPermission;
import edu.ycp.cs481.model.User;

@SuppressWarnings("serial")
public class ComplianceCheckerServlet extends HttpServlet{

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		HttpSession session = req.getSession();
		if(session.getAttribute("user_id") == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
		}
		else{
			UserController uc = new UserController();
			int userID = (int) session.getAttribute("user_id");
			
			if(uc.hasPermission(userID, EnumPermission.ALL)){
				// Only admins with full permissions can go here ^
				ComplianceController cc = new ComplianceController();

				User current = uc.searchForUsers(userID, -1, false, "", false, "", false, "", -1, -1).get(0);

				ArrayList<ComplianceIssue> issues = cc.pullAllComplianceIssues();

				req.setAttribute("issues", issues);
				req.setAttribute("displaySize", 10);
				req.setAttribute("page", 0);
				req.setAttribute("email", current.getEmail());
				req.getRequestDispatcher("/compliance_checker.jsp").forward(req, resp);	
			}else{
				session.setAttribute("error", "Sorry, you don't have permission to do that.");
				resp.sendRedirect(req.getContextPath() + "/user_home");
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String changePage = req.getParameter("changePage");
		String changeDisplaySize = req.getParameter("changeDisplaySize");
		int currentDisplaySize = Integer.parseInt(req.getParameter("displaySize"));
		HttpSession session = req.getSession();
		String action = req.getParameter("action");
		boolean loggedIn = true;
		if(action != null && action.equalsIgnoreCase("logout")){
			UserController.logout(req);
			loggedIn = false;
			resp.sendRedirect("login");
			return;
		}
		if(changePage != null && !changePage.equalsIgnoreCase("")){
			int currentPage = Integer.parseInt(req.getParameter("page"));
			if(changePage.equalsIgnoreCase("prev")){
				req.setAttribute("page", currentPage - 1);
			}
			else if(changePage.equalsIgnoreCase("next")){
				req.setAttribute("page", currentPage + 1);
			}
		}else{
			req.setAttribute("page", 0);
		}

		if(changeDisplaySize != null && !changeDisplaySize.equalsIgnoreCase("")){
			req.setAttribute("displaySize", Integer.parseInt(changeDisplaySize));
		}else{
			req.setAttribute("displaySize", currentDisplaySize);
		}
		
		// Reload issues and page
		// Seems getting issues back from the jsp (e.g. req.getParameter("issues")) doesn't work right for this
		ComplianceController cc = new ComplianceController();
		ArrayList<ComplianceIssue> issues = cc.pullAllComplianceIssues();
		req.setAttribute("issues", issues);
		req.getRequestDispatcher("/compliance_checker.jsp").forward(req, resp);	
	}
}


