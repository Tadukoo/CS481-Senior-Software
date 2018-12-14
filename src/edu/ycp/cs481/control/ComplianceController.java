package edu.ycp.cs481.control;

import edu.ycp.cs481.model.User;

import java.util.List;
import java.util.ArrayList;
import edu.ycp.cs481.control.UserController;
import edu.ycp.cs481.control.SOPController;
import edu.ycp.cs481.model.ComplianceIssue;
import edu.ycp.cs481.model.SOP;

public class ComplianceController{


	public ArrayList<ComplianceIssue> pullComplianceIssuesOfUser(User u){
		ArrayList<ComplianceIssue> userIssues = new ArrayList<ComplianceIssue>();
		SOPController sc = new SOPController(); 
		ArrayList<SOP> sops = sc.searchForSOPs(-1, false, "", false, "", -1, -1, -1, u.getID(), -1, false);
		for(SOP s: sops){
			ComplianceIssue i = new ComplianceIssue();
			i.setEmail(u.getEmail());
			i.setPriority(s.getPriority());
			i.setTitle(s.getTitle());
			userIssues.add(i);
		}
		return userIssues;

	}

	public ArrayList<ComplianceIssue> pullAllComplianceIssues(){
		UserController uc = new UserController();
		List<User> allUsers = uc.searchForUsers(-1, -1, false, "", false, "", false, "", -1, -1);
		ArrayList<ComplianceIssue> issues = new ArrayList<ComplianceIssue>(); 
		for(User u: allUsers){
			issues.addAll(pullComplianceIssuesOfUser(u));
		}
		return issues;
	}
}
