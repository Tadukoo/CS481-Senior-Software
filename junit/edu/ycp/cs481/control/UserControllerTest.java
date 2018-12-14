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
	private UserController uc;
	private Database db;
	
	@Before
	public void setUp(){
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
		// 
		
		//
		
		//
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
	public void testChange() {
		// Position
		uc.changePosition(1, 1);
		
		// Password
		uc.changePassword(1, "test");
		
		// Email
		uc.changeEmail(1, "test@ycp.edu");
		
		// First Name
		uc.changeFirstName(1, "Carlos");
		
		// Last Name
		uc.changeLastName(1, "Solrac");
		
		// Test all of our values
		User u = uc.searchForUsers(-1, -1, false, null, false, null, false, null, -1, -1).get(0);
		assertEquals(u.getPosition().getID(), 1);
		assertTrue(uc.authenticate("test", u.getPassword()));
		assertEquals(u.getEmail(), "test@ycp.edu");
		assertEquals(u.getFirstName(), "Carlos");
		assertEquals(u.getLastName(), "Solrac");
	}
	
	@Test 
	public void testHasPermission() {
		assertTrue(uc.hasPermission(1, EnumPermission.ALL));
	}
	
	@Test
	public void testIsLockedOut() {
		
	}
}