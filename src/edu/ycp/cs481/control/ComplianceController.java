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
		ArrayList<ComplianceIssue> issues =  PullComplianceIssues();
		ArrayList<ComplianceIssue> userissues = new ArrayList<ComplianceIssue>(); 
		return issues;
	}

	//pulls all compliance issues with 
	public ArrayList<ComplianceIssue> PullComplianceIssues() {
		List<User> allUsers = uc.searchForUsers(-1, -1, false, "", false, "", false, "", -1, -1);
		ArrayList<ComplianceIssue> issues = new ArrayList<ComplianceIssue>(); 
		ArrayList<SOP> sops = new ArrayList<SOP>();
		for(User u: allUsers) {
			sops = sc.searchForSOPs(-1, false, "", false, "", -1, -1, -1, u.getID(), -1);
			for(SOP s: sops) {
				ComplianceIssue i = new ComplianceIssue();
				i.setEmail(u.getEmail());
				i.setPriority(s.getPriority());
				i.setTitle(s.getTitle());
				issues.add(i);
			}
		}
		return issues;
	}
}
