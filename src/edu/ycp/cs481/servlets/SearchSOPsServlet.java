package edu.ycp.cs481.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ycp.cs481.control.SOPController;
import edu.ycp.cs481.control.UserController;
import edu.ycp.cs481.model.EnumPermission;

import edu.ycp.cs481.model.SOP;
import edu.ycp.cs481.model.User;

@SuppressWarnings("serial")
public class SearchSOPsServlet extends HttpServlet{
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		HttpSession session = req.getSession();
		if(session.getAttribute("user_id") == null){
			resp.sendRedirect(req.getContextPath() + "/login");
		}else{

			int CurrentuserID = (int) session.getAttribute("user_id");

			// Get Parameters
			String userIDStr = req.getParameter("userID");

			String posIDStr = req.getParameter("posID");
			int userID = (userIDStr != null)?Integer.parseInt(userIDStr):-1;
			int posID = (posIDStr != null)?Integer.parseInt(posIDStr):-1;

			
			// Permission checks
			UserController uc = new UserController();
			User current = uc.searchForUsers(userID, -1, false, "", false, "", false, "", -1, -1).get(0);

			int theirID = (int) session.getAttribute("user_id");
			if(userID == -1 && posID == -1 && 
					!(uc.hasPermission(theirID, EnumPermission.SEARCH_SOPS) || uc.hasPermission(theirID, EnumPermission.ALL))){
				session.setAttribute("error", "You don't have permission to search SOPs!");
				resp.sendRedirect("/user_home");
			}else if(posID != -1 && 
					!(uc.hasPermission(theirID, EnumPermission.VIEW_REQUIREMENTS) || uc.hasPermission(theirID, EnumPermission.ALL))){
				session.setAttribute("error", "You don't have permission to view Position requirements!");
				resp.sendRedirect("/user_home");
			}else if(userID != -1 && theirID != userID && 
					!((uc.hasPermission(theirID, EnumPermission.HAVE_SUBORDINATES) && !uc.hasSubordinate(theirID, userID))
							|| uc.hasPermission(theirID, EnumPermission.ALL))){
				session.setAttribute("error", "You're either not a manager or don't have user with ID " + userID + 
						" as a subordinate!");
				resp.sendRedirect("/user_home");
			}else{
				// Get SOPs List
				SOPController sc = new SOPController();
				ArrayList<SOP> sops = sc.searchForSOPs(-1, false, null, false, null, -1, -1, -1, userID, posID, null);
				
				// Set those attributes!
				req.setAttribute("sops", sops);
				req.setAttribute("userID", userID);
				req.setAttribute("posID", posID);
				req.setAttribute("theirSOPs", userID == theirID);
				req.setAttribute("currentemail", current.getEmail());
				// Set default page and display size
				req.setAttribute("page", 0);
				req.setAttribute("displaySize", 10);
				req.getRequestDispatcher("/search_sops.jsp").forward(req, resp);
			}

		}
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String action = req.getParameter("action");
		if(action != null && action.equalsIgnoreCase("logout")){
			UserController.logout(req);
			resp.sendRedirect("login");
			return;
		}
		
		String changePage = req.getParameter("changePage");
		String changeDisplaySize = req.getParameter("changeDisplaySize");
		int currentDisplaySize = Integer.parseInt(req.getParameter("displaySize"));
		
		if(changePage != null && !changePage.equalsIgnoreCase("")){
			int currentPage = Integer.parseInt(req.getParameter("page"));
			if(changePage.equalsIgnoreCase("prev")){
				req.setAttribute("page", currentPage - 1);
			}else if(changePage.equalsIgnoreCase("next")){
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
		
		String idStr = req.getParameter("sopID");
		String priorityStr = req.getParameter("priority");
		String versionStr = req.getParameter("version");
		String authorIDStr = req.getParameter("authorID");
		String userIDStr = req.getParameter("userID");
		String posIDStr = req.getParameter("posID");
		
		int id = idStr.equalsIgnoreCase("")?-1:Integer.parseInt(idStr);
		String title = req.getParameter("title");
		String description = req.getParameter("description");
		int priority = priorityStr.equalsIgnoreCase("")?-1:Integer.parseInt(priorityStr);
		int version = versionStr.equalsIgnoreCase("")?-1:Integer.parseInt(versionStr);
		int authorID = authorIDStr.equalsIgnoreCase("")?-1:Integer.parseInt(authorIDStr);
		int userID = userIDStr.equalsIgnoreCase("")?-1:Integer.parseInt(userIDStr);
		int posID = posIDStr.equalsIgnoreCase("")?-1:Integer.parseInt(posIDStr);
		
		SOPController sc = new SOPController();
		ArrayList<SOP> sops = sc.searchForSOPs(id, true, title, true, description, priority, version, authorID, userID, posID, null);
		
		req.setAttribute("sops", sops);
		
		// Push parameters back so User doesn't have to retype them
		req.setAttribute("sopID", idStr);
		req.setAttribute("title", title);
		req.setAttribute("description", description);
		req.setAttribute("priority", priorityStr);
		req.setAttribute("version", versionStr);
		req.setAttribute("authorID", authorIDStr);
		req.setAttribute("userID", userID);
		req.setAttribute("posID", posID);
		
		// Redo theirSOPs
		HttpSession session = req.getSession();
		int theirID = (int) session.getAttribute("user_id");
		req.setAttribute("theirSOPs", theirID == userID);
		
		req.getRequestDispatcher("/search_sops.jsp").forward(req, resp);
	}
}
