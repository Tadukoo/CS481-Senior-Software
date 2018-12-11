package edu.ycp.cs481.control;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs481.model.ComplianceIssue;
import edu.ycp.cs481.model.SOP;
import edu.ycp.cs481.model.User;

public class ComplianceControllerTest{
	private ComplianceController c;
	private ArrayList<User> users;
	private ArrayList<ArrayList<SOP>> incompleteSOPsOfUsers;
	
	@Before
	public void setup(){
		c = new ComplianceController();
		
		UserController uc = new UserController();
		users = uc.searchForUsers(-1, -1, false, null, false, null, false, null, -1, -1);
		
		incompleteSOPsOfUsers = new ArrayList<ArrayList<SOP>>();
		SOPController sc = new SOPController();
		for(User u: users){
			ArrayList<SOP> sops = sc.searchForSOPs(-1, false, null, false, null, -1, -1, -1, u.getID(), -1, false);
			incompleteSOPsOfUsers.add(sops);
		}
	}
	
	@Test
	public void testPullComplianceIssuesOfUser(){
		for(int i = 0; i < users.size(); i++){
			User u = users.get(i);
			ArrayList<ComplianceIssue> issues = c.pullComplianceIssuesOfUser(u);
			ArrayList<SOP> incompleteSOPs = incompleteSOPsOfUsers.get(i);
			for(int j = 0; j < issues.size(); j++){
				SOP s = incompleteSOPs.get(j);
				ComplianceIssue issue = issues.get(j);
				assertEquals(s.getTitle(), issue.getTitle());
				assertEquals(s.getPriority(), issue.getPriority());
				assertEquals(u.getEmail(), issue.getEmail());
			}
		}
	}
	
	@Test
	public void testPullAllComplianceIssues(){
		ArrayList<ComplianceIssue> issues = c.pullAllComplianceIssues();
		int i = 0;
		for(int j = 0; j < users.size(); j++){
			User u = users.get(j);
			ArrayList<SOP> incompleteSOPs = incompleteSOPsOfUsers.get(j);
			for(SOP s: incompleteSOPs){
				ComplianceIssue issue = issues.get(i);
				assertEquals(s.getTitle(), issue.getTitle());
				assertEquals(s.getPriority(), issue.getPriority());
				assertEquals(u.getEmail(), issue.getEmail());
				i++;
			}
		}
	}
}
