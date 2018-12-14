package edu.ycp.cs481.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import edu.ycp.cs481.model.EnumPermission;
import edu.ycp.cs481.model.Position;
import edu.ycp.cs481.model.SOP;
import edu.ycp.cs481.model.User;

public class InitialData {
	private ArrayList<String> roleList;
	private HashMap<Integer, ArrayList<Integer>> rolePermissions;
	private ArrayList<Position> posList;
	private Position p1, p2, p3;
	private ArrayList<User> userList;
	private User u1, u2, u3;
	private ArrayList<Integer> roleS;
	private ArrayList<SOP> sopList;	
	private SOP s1, s2, s3, s4, s5, s6;
	private HashMap<Integer, ArrayList<Integer>> userSOPs;
	
	public InitialData() {
		roleList = new ArrayList<String>();
		rolePermissions = new HashMap<Integer, ArrayList<Integer>>();
		posList = new ArrayList<Position>();
		userList = new ArrayList<User>();
		roleS = new ArrayList<Integer>();
		sopList = new ArrayList<SOP>();
		
		// Roles //
		roleList.add("Normies");
		roleList.add("Manager");
		roleList.add("Admin");
		
		// Role Permissions
		ArrayList<Integer> managerPerms = new ArrayList<Integer>();
		managerPerms.add(EnumPermission.SEARCH_USERS.getID());
		managerPerms.add(EnumPermission.HAVE_SUBORDINATES.getID());
		managerPerms.add(EnumPermission.EDIT_USERS.getID());
		managerPerms.add(EnumPermission.SEARCH_SOPS.getID());
		managerPerms.add(EnumPermission.EDIT_SOPS.getID());
		rolePermissions.put(2, managerPerms);
		
		ArrayList<Integer> adminPerms = new ArrayList<Integer>();
		adminPerms.add(EnumPermission.ALL.getID());
		rolePermissions.put(3, adminPerms);
		
		p1 = new Position();
		p1.setID(1);
		p1.setTitle("Admin");
		p1.setDescription("Can view/modify all aspects of the system.");
		p1.setPriority(1);
		
		p2 = new Position();
		p2.setID(2);
		p2.setTitle("User");
		p2.setDescription("This is used as a temporary position for those without one.");
		p2.setPriority(3);
		
		p3 = new Position();
		p3.setID(3);
		p3.setTitle("Test");
		p3.setDescription("This will be changed yesterday");
		p3.setPriority(4);
		
		posList.add(p1);
		posList.add(p2);
		posList.add(p3);
		
		u1 = new User();
		u1.setID(1);
		u1.setEmail("realtadukoo@gmail.com");
		u1.setPassword("my_password");
		u1.setFirstName("Logan");
		u1.setLastName("Ferree");
		u1.setLockedOut(false);
		u1.setArchived(false);
		u1.setPosition(p1);
		
		u2 = new User();
		u2.setID(2);
		u2.setEmail("zhenry@ycp.edu");
		u2.setPassword("his_password");
		u2.setFirstName("Zachary");
		u2.setLastName("Heneree");
		u2.setLockedOut(false);
		u2.setArchived(false);
		u2.setPosition(p2);
		
		u3 = new User();
		u3.setID(3);
		u3.setEmail("rvincent@ycp.edu");
		u3.setPassword("garbage");
		u3.setFirstName("Ryan");
		u3.setLastName("Vincent");
		u3.setLockedOut(true);
		u3.setArchived(true);
		u3.setPosition(p2);
		
		userList.add(u1);
		userList.add(u2);
		userList.add(u3);
		
		roleS.add(3);
		roleS.add(1);
		roleS.add(2);
		
		s1 = new SOP();
		s1.setID(1);
		s1.setTitle("How to Avoid Sexual Harassment");
		s1.setDescription("A how-to guide for Ryan on avoiding sexually harassing women in the workplace environment.");
		s1.setPriority(1);
		s1.setVersion(1);
		s1.setAuthorID(u1.getID());
		s1.setArchived(false);
		
		s2 = new SOP();
		s2.setID(2);
		s2.setTitle("How to Change a Light Bulb");
		s2.setDescription("A how-to to guide for young men and boys on the changing of the bulbs");
		s2.setPriority(1);
		s2.setVersion(1);
		s2.setAuthorID(u1.getID());
		s2.setArchived(false);
		
		s3 = new SOP();
		s3.setID(3);
		s3.setTitle("How to write a JUnit test");
		s3.setDescription("Do it the night before the demo");
		s3.setPriority(2);
		s3.setVersion(1);
		s3.setAuthorID(u2.getID());
		s3.setArchived(false);
		
		s4 = new SOP();
		s4.setID(4);
		s4.setTitle("How to get Arrested");
		s4.setDescription("Break da law!");
		s4.setPriority(5);
		s4.setVersion(5);
		s4.setAuthorID(u2.getID());
		s4.setArchived(false);
		
		s5 = new SOP();
		s5.setID(5);
		s5.setTitle("Fire Safety Guidelines");
		s5.setDescription("Don''t upset Smokey!");
		s5.setPriority(1);
		s5.setVersion(3);
		s5.setAuthorID(u2.getID());
		s5.setArchived(false);
		
		s6 = new SOP();
		s6.setID(6);
		s6.setTitle("How to Survive a Bear Attack");
		s6.setDescription("Ya don''t!");
		s6.setPriority(3);
		s6.setVersion(2);
		s6.setAuthorID(u2.getID());
		s6.setArchived(false);
		
		sopList.add(s1);
		sopList.add(s2);
		sopList.add(s3);
		sopList.add(s4);
		sopList.add(s5);
		sopList.add(s6);
		
		ArrayList<SOP> reqList = new ArrayList<SOP>();
		reqList.add(s1);
		reqList.add(s2);
		reqList.add(s3);
		p1.setRequirements(reqList);
		p2.setRequirements(reqList);
		
		userSOPs = new HashMap<Integer, ArrayList<Integer>>();
		ArrayList<Integer> u2Reqs = new ArrayList<Integer>();
		u2Reqs.add(4);
		u2Reqs.add(6);
		userSOPs.put(2, u2Reqs);
		ArrayList<Integer> u3Reqs = new ArrayList<Integer>();
		u3Reqs.add(5);
		userSOPs.put(3, u3Reqs);
	}

	public ArrayList<String> getInitialRoles(){
		return roleList;
	}
	
	public HashMap<Integer, ArrayList<Integer>> getRolePermissions(){
		return rolePermissions;
	}

	public ArrayList<User> getInitialUsers(){
		return userList;
	}
	
	public ArrayList<Position> getInitialPositions(){
		return posList;
	}
	
	public ArrayList<SOP> getInitialSOPs(){
		return sopList;
	}
	
	public ArrayList<EnumPermission> getInitialPermissions(){
		return new ArrayList<EnumPermission>(Arrays.asList(EnumPermission.values()));
	}
	
	public HashMap<Integer, ArrayList<Integer>> getUserSOPs(){
		return userSOPs;
	}
}