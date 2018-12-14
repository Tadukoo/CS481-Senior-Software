package edu.ycp.cs481.control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs481.db.Database;
import edu.ycp.cs481.model.EnumPermission;
import edu.ycp.cs481.model.Position;
import edu.ycp.cs481.model.SOP;
import edu.ycp.cs481.model.User;

public class UserControllerTest{
	private Position pos1, pos2, pos3, pos4, pos5;
	private SOP sop1, sop2;
	private User user1, user2, user3, user4; 
	private ArrayList<Position> positionList; 
	private ArrayList<SOP> sopList;
	private ArrayList<User> userList;
	private UserController uc;
	private Database db;
	
	@Before
	public void setUp(){
		positionList = new ArrayList<Position>(); 
		sopList = new ArrayList<SOP>(); 
		userList = new ArrayList<User>(); 
		
		pos1 = new Position(); 
		pos1.setID(2);
		pos1.setPriority(1);
		pos1.setTitle("Admin");
		
		pos2 = new Position(); 
		pos2.setID(1);
		pos2.setPriority(1);
		pos2.setTitle("CEO");
		
		pos3 = new Position(); 
		pos3.setID(3);
		pos3.setPriority(5);
		pos3.setTitle("IT");
		
		pos4 = new Position(); 
		pos4.setID(4);
		pos4.setPriority(9);
		pos4.setTitle("Intern");
		
		pos5 = new Position(); 
		pos5.setTitle("Manager");
		pos5.setID(5);
		pos5.setPriority(4);
		
		user1 = new User();
		user1.setEmail("Admin@google.com");
		user1.setPassword("DiveOnIn");
		user1.setFirstName("Rodger");
		user1.setLastName("Smith");
		user1.setID(12);
		user1.setPosition(pos1);
		
		user2 = new User();
		user2.setFirstName("Stan");
		user2.setLastName("Smith");
		user2.setEmail("rookie@email.com");
		user2.setPassword("bangBang");
		user2.setPosition(pos3);
		user2.setID(4);
		
		user3 = new User(); 
		user3.setEmail("failTest@@gmail.com");
		user3.setFirstName("");
		user3.setLastName("");
		user3.setPassword("");
		user3.setID(-1);
		user3.setPosition(pos4);
		
		user4 = new User(); 
		user4.setEmail("theBoss@tesla.com");
		user4.setPassword("POWER");
		user4.setFirstName("Elon");
		user4.setLastName("Musk");
		user4.setID(5);
		user4.setPosition(pos2);
		
		positionList.add(pos1);
		positionList.add(pos2);
		positionList.add(pos3);
		positionList.add(pos4);
		
		sop1 = new SOP(); 
		sop1.setTitle("Login");
		sop1.setID(1);
		sop1.setDescription("How to login");
		sop1.setPriority(7);
		sop1.setVersion(1);
		sop1.setAuthorID(user1.getID());
		
		sop2 = new SOP(); 
		sop2.setTitle("Logout");
		sop2.setDescription("How to logout");
		sop2.setPriority(7);
		sop2.setID(2);
		sop2.setVersion(2);
		sop2.setAuthorID(user1.getID());
		
		sopList.add(sop1);
		sopList.add(sop2);
		
		
		uc = new UserController();
		
		db = new Database();
	}
	
	@Test
	public void testPasswordHashingAndAuthentification(){
		String password = "passwordighr";
		String hashedPass = uc.hashPassword(password);
		assertNotEquals(password, hashedPass);
		assertTrue(uc.authenticate(password, hashedPass));
	}
	
	@Test
	public void testCRUDQuarantineUser() {
		// Test Insert
		uc.insertQuarantineUser("zhenry@ycp.edu", "password", "Chuck", "Norris");
		
		// Test Find
		assertTrue(uc.findQuarantineUser("zhenry@ycp.edu"));
		
		// Test Verify
		String token = uc.retrySendEmail("zhenry@ycp.edu");
		assertTrue(uc.verifyEmail("zhenry@ycp.edu", token));
		
		// Clean up newly verified user
		db.executeUpdate("Deleting User", "delete from User where email = 'zhenry@ycp.edu'");
		
		// Test Remove
		uc.insertQuarantineUser("zhenry@ycp.edu", "password", "Chuck", "Norris");
		uc.deleteQuarantineUser("zhenry@ycp.edu");
		assertFalse(uc.findQuarantineUser("zhenry@ycp.edu"));
	}
	
	@Test
	public void testCRUDUser() {
		// Test Insert
		uc.insertUser(true, "thegame@ycp.edu", "tester", "Paul", "Logan", false, false, 2);
		// Test searchForUsers
		User u = uc.searchForUsers(-1, -1, false, "thegame@ycp.edu", false, "Paul", false, "Logan", 2, -1).get(0);
		assertEquals(u.getEmail(), "thegame@ycp.edu");
		assertEquals(u.getFirstName(), "Paul");
		assertEquals(u.getLastName(), "Logan");
		assertEquals(u.getPosition().getID(), 2);
		// Test delete
		db.executeUpdate("Deleting User", "delete from User where email = 'thegame@ycp.edu'");
	}
	
	@Test
	public void testResetPassword() {
		
	}
	
	@Test
	public void testSubordinates() {
		// Inserts
		uc.addSubordinate(1, 2);
		
		// hasSubordinate
		assertTrue(uc.hasSubordinate(1, 2));
		
		// getManagersOfUser
		
		// Remove
		uc.removeSubordinate(1, 2);
	}
	
	@Test
	public void testClock() {
		// Clock In
		uc.clockIn(1);
		
		// isClockedIn
		assertTrue(uc.isClockedIn(1));
		
		// Clock Out
		uc.clockOut(1);
		
		// Clean up CompletedShift
		db.executeUpdate("Deleting Shift", "delete from CompletedShift where user_id = 1");
	}
	
	@Test
	public void testChangeEmail() {
		//uc.changeEmail();
	}
	
	@Test
	public void testChangePassword() {
		//uc.changePassword();
	}
	
	@Test
	public void testChangePosition() {
		//uc.changePosition(user3, newPositionID);
	}
	
	@Test 
	public void testHasPermission() {
		assertTrue(uc.hasPermission(1, EnumPermission.ALL));
	}
	
	@Test
	public void testIsLockedOut() {
		
	}
}