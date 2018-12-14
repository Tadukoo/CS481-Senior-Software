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
public class EditUserServlet extends HttpServlet{
	
	public void loadUser(HttpServletRequest req){
		// TODO: Error checking
		int userID = Integer.parseInt(req.getParameter("userID"));
		UserController uc = new UserController();
		User user = uc.searchForUsers(userID, -1, false, null, false, null, false, null, -1, -1).get(0);
		req.setAttribute("userID", user.getID());
		req.setAttribute("employeeID", user.getEmployeeID());
		req.setAttribute("email", user.getEmail());
		req.setAttribute("firstName", user.getFirstName());
		req.setAttribute("lastName", user.getLastName());
		req.setAttribute("archived", user.isArchived());
		req.setAttribute("lockedOut", user.isLockedOut());
		// TODO: SOPs
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		HttpSession session = req.getSession();
		if(session.getAttribute("user_id") == null){
			resp.sendRedirect(req.getContextPath() + "/login");
		}else{
			int userID = (int) session.getAttribute("user_id");
			UserController uc = new UserController();
			User current = uc.searchForUsers(userID, -1, false, "", false, "", false, "", -1, -1).get(0);
			req.setAttribute("currentemail", current.getEmail());
			
			if(uc.hasPermission(userID, EnumPermission.EDIT_USERS) || uc.hasPermission(userID, EnumPermission.ALL)){
				loadUser(req);
				req.getRequestDispatcher("/edit_user.jsp").forward(req, resp);
			}else{
				session.setAttribute("error", "You don't have permission to Edit Users!");
				resp.sendRedirect(req.getContextPath() + "/user_home");
			}
		}
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		int id = Integer.parseInt(req.getParameter("userID"));
		
		UserController uc = new UserController();
		String action = req.getParameter("doStuff");
		boolean goodUpdate = true;
		
		if(action.equalsIgnoreCase("archiveUser")){
			uc.archiveUser(id);
			req.setAttribute("successMessage", "Archived User!");
		}else if(action.equalsIgnoreCase("unarchiveUser")){
			uc.unarchiveUser(id);
			req.setAttribute("successMessage", "Unarchived User!");
		}else if(action.equalsIgnoreCase("lockoutUser")){
			uc.lockout(id);
			req.setAttribute("successMessage", "Locked out User!");
		}else if(action.equalsIgnoreCase("overrideLockout")){
			uc.overturnLockout(id);
			req.setAttribute("successMessage", "Overturned Lockout!");
		}else if(action.equalsIgnoreCase("changeFirstName")){
			String newFirstName = req.getParameter("newFirstName");
			String newFirstNameConfirm = req.getParameter("newFirstNameConfirm");
			
			if(newFirstName == null || newFirstName.equalsIgnoreCase("")){
				req.setAttribute("firstNameError", "First Name can't be null!");
				goodUpdate = false;
			}
			if(!newFirstName.equalsIgnoreCase(newFirstNameConfirm)){
				req.setAttribute("firstNameConfirmError", "First Names do not match!");
				goodUpdate = false; 
			}
			if(goodUpdate){
				uc.changeFirstName(id, newFirstName);
				req.setAttribute("successMessage", "Updated First Name to " + newFirstName + "!");
			}
		}else if(action.equalsIgnoreCase("changeLastName")){
			String newLastName = req.getParameter("newLastName");
			String newLastNameConfirm = req.getParameter("newLastNameConfirm");
			
			if(newLastName == null || newLastName.equalsIgnoreCase("")){
				req.setAttribute("lastNameError", "Last Name can't be null!");
				goodUpdate = false;
			}
			if(!newLastName.equalsIgnoreCase(newLastNameConfirm)){
				req.setAttribute("lastNameConfirmError", "Last Names don't match!");
				goodUpdate = false;
			}
			if(goodUpdate){
				uc.changeLastName(id, newLastName);
				req.setAttribute("successMessage", "Updated Last Name to " + newLastName + "!");
			}
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
				uc.assignSOP(id, sopID);
				req.setAttribute("successMessage", "Added " + title + " to User!");
			}
		}
		loadUser(req);
		req.getRequestDispatcher("/edit_user.jsp").forward(req, resp);
	}
}
