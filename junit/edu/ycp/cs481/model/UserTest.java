package edu.ycp.cs481.model;


import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs481.model.Position;
import edu.ycp.cs481.model.User;

public class UserTest{
	private User user;

	@Before
	public void setUp(){
		user = new User();
	}
	
	@Test
	public void testSetID(){
		user.setID(25);
		assertEquals(25, user.getID());
		user.setID(192);
		assertEquals(192, user.getID());
	}
	
	@Test
	public void testSetEmployeeID(){
		user.setEmployeeID(42);
		assertEquals(42, user.getEmployeeID());
		user.setEmployeeID(87);
		assertEquals(87, user.getEmployeeID());
	}
	
	@Test
	public void testSetEmail(){
		user.setEmail("realtadukoo@gmail.com");
		assertEquals("realtadukoo@gmail.com", user.getEmail());
		user.setEmail("zack@ycp.edu");
		assertEquals("zack@ycp.edu", user.getEmail());
	}
	
	@Test
	public void testSetPassword(){
		user.setPassword("my_password");
		assertEquals("my_password", user.getPassword());
		user.setPassword("ryan's_password");
		assertEquals("ryan's_password", user.getPassword());
	}
	
	@Test
	public void testSetFirstName(){
		user.setFirstName("Logan");
		assertEquals("Logan", user.getFirstName());
		user.setFirstName("Zack");
		assertEquals("Zack", user.getFirstName());
	}
	
	@Test
	public void testSetLastName(){
		user.setLastName("Kcaz");
		assertEquals("Kcaz", user.getLastName());
		user.setLastName("Ferree");
		assertEquals("Ferree", user.getLastName());
	}
	
	@Test
	public void testSetPosition(){
		Position pos = new Position();
		pos.setTitle("Some lame position");
		
		user.setPosition(pos);
		assertEquals(pos, user.getPosition());
		
		Position pos2 = new Position();
		pos2.setTitle("Another lame position");
		pos2.setID(5);
		
		user.setPosition(pos2);
		assertEquals(pos2, user.getPosition());
	}
	
	@Test
	public void setLockedOut(){
		user.setLockedOut(false);
		assertEquals(false, user.isLockedOut());
		
		user.setLockedOut(false);
		assertEquals(false, user.isLockedOut());
		
		user.setLockedOut(true);
		assertEquals(true, user.isLockedOut());
	}
	
	@Test
	public void setArchived(){
		user.setArchived(true);
		assertEquals(true, user.isArchived());
		
		user.setArchived(false);
		assertEquals(false, user.isArchived());
		
		user.setArchived(false);
		assertEquals(false, user.isArchived());
	}
}
