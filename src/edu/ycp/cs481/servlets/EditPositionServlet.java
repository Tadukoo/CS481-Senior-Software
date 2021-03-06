package edu.ycp.cs481.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ycp.cs481.model.EnumPermission;
import edu.ycp.cs481.model.Position;
import edu.ycp.cs481.model.SOP;
import edu.ycp.cs481.model.User;
import edu.ycp.cs481.control.PositionController;
import edu.ycp.cs481.control.SOPController;
import edu.ycp.cs481.control.UserController;

@SuppressWarnings("serial")
public class EditPositionServlet extends HttpServlet{
	
	private void loadPosition(HttpServletRequest req){
		// TODO: Error checking
		int posID = Integer.parseInt(req.getParameter("posID"));
		PositionController pc = new PositionController();
		Position pos = pc.searchForPositions(posID, false, null, false, null, -1).get(0);
		req.setAttribute("posID", pos.getID());
		req.setAttribute("title", pos.getTitle());
		req.setAttribute("priority", pos.getPriority());
		req.setAttribute("description", pos.getDescription());
		// TODO: Current Requirements
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		HttpSession session = req.getSession();
		if(session.getAttribute("user_id") == null){
			resp.sendRedirect(req.getContextPath() + "/login");
		}else{
			UserController uc = new UserController();
			int userID = (int) session.getAttribute("user_id");
			User current = uc.searchForUsers(userID, -1, false, "", false, "", false, "", -1, -1).get(0);
			req.setAttribute("currentemail", current.getEmail());
			
			if(uc.hasPermission(userID, EnumPermission.ALL) || uc.hasPermission(userID, EnumPermission.EDIT_POSITIONS)){
				loadPosition(req);
				req.getRequestDispatcher("/edit_position.jsp").forward(req, resp);
			}else{
				session.setAttribute("error", "You don't have permission to Edit Positions!");
				resp.sendRedirect(req.getContextPath() + "/user_home");
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String otherAction = req.getParameter("action");
		if(otherAction != null && otherAction.equalsIgnoreCase("logout")){
			UserController.logout(req);
			resp.sendRedirect("login");
			return;
		}
		
		int id = Integer.parseInt(req.getParameter("posID"));
		
		PositionController pc = new PositionController();
		String action = req.getParameter("doStuff");
		boolean goodUpdate = true;
		
		if(action.equalsIgnoreCase("deletePosition")){
			// TODO: Prompt instead of just doing it.
			// TODO: Ensure that links in database are gone first
			pc.removePosition(id);
			HttpSession session = req.getSession();
			session.setAttribute("success", "Deleted Position with id " + id);
			resp.sendRedirect(req.getContextPath() + "/user_home");
			return;
		}else if(action.equalsIgnoreCase("changeTitle")){
			String newTitle = req.getParameter("newTitle");
			String newTitleConfirm = req.getParameter("newTitleConfirm");
			
			if(newTitle == null || newTitle.equalsIgnoreCase("")){
				req.setAttribute("titleError", "Title can't be null!");
				goodUpdate = false;
			}
			if(!newTitle.equalsIgnoreCase(newTitleConfirm)){
				req.setAttribute("titleConfirmError", "Titles do not match!");
				goodUpdate = false; 
			}
			if(goodUpdate){
				pc.changeTitle(id, newTitle);
				req.setAttribute("successMessage", "Update title to " + newTitle + "!");
			}
		}else if(action.equalsIgnoreCase("changePriority")){
			String newPriorityStr = req.getParameter("newPriority");
			String newPriorityConfirmStr = req.getParameter("newPriorityConfirm");
			
			if(newPriorityStr == null || newPriorityStr.equalsIgnoreCase("")){
				req.setAttribute("priorityError", "Please enter a number!");
				goodUpdate = false;
			}
			if(!newPriorityStr.equalsIgnoreCase(newPriorityConfirmStr)){
				req.setAttribute("priorityConfirmError", "The priorities don't match!");
				goodUpdate = false;
			}
			if(goodUpdate){
				int newPriority = Integer.parseInt(newPriorityStr);
				if(newPriority <= 0 || newPriority > 10){
					req.setAttribute("priorityError", "Priority must be between 1 and 10!");
				}else{
					pc.changePriority(id, newPriority);
					req.setAttribute("successMessage", "Updated priority to " + newPriority + "!");
				}
			}
		}else if(action.equalsIgnoreCase("changeDescription")){
			String newDescription = req.getParameter("newDescription");
			pc.changeDescription(id, newDescription);
			req.setAttribute("successMessage", "Updated description!");
		}else if(action.equalsIgnoreCase("addSOP")){
			String sopIDStr = req.getParameter("sopID");
			int sopID = -1;
			ArrayList<SOP> sopResult = null;
			
			if(sopIDStr == null || sopIDStr.equalsIgnoreCase("")){
				req.setAttribute("sopError", "SOP ID can't be null!");
				goodUpdate = false;
			}else{
				sopID = Integer.parseInt(sopIDStr);
				SOPController sc = new SOPController();
				sopResult = sc.searchForSOPs(sopID, false, null, false, null, -1, -1, -1, -1, -1, null);
				if(sopResult == null || sopResult.size() == 0){
					req.setAttribute("sopError", "That SOP ID isn't used!");
					goodUpdate = false;
				}
			}
			
			if(goodUpdate){
				String title = sopResult.get(0).getTitle();
				pc.insertPositionSOP(id, sopID);
				req.setAttribute("successMessage", "Added " + title + " to Position!");
			}
		}
		loadPosition(req);
		req.getRequestDispatcher("/edit_position.jsp").forward(req, resp);
	}
}

