package edu.ycp.cs481.control;

import edu.ycp.cs481.model.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;
import java.util.ArrayList;
import edu.ycp.cs481.control.UserController;
import edu.ycp.cs481.db.Database;
import edu.ycp.cs481.control.SOPController;
import edu.ycp.cs481.model.ComplianceIssue;
import edu.ycp.cs481.model.Position;
import edu.ycp.cs481.model.SOP;

public class ComplianceController {
	private Database db = new Database();
	private static User u; 
	private static Position p;
	private static PositionController pc = new PositionController(); 
	private static UserController uc = new UserController();
	private static SOPController sc = new SOPController(); 

	public ArrayList<ComplianceIssue> GetIssueswithUser(User u) {
		ArrayList<ComplianceIssue> issues = null;
		return issues;
	}

	//time to do the actual testing and checking of peoples training histories  
	public ArrayList<ComplianceIssue> PullComplianceIssues() {
		List<User> allUsers = uc.searchForUsers(-1, -1, false, "", false, "", false, "", 0, -1);
		for(User u: allUsers) {
			
		}
		return null;
	}
}
