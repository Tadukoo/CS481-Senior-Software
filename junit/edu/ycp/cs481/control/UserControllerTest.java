package edu.ycp.cs481.control;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;

import edu.ycp.cs481.model.Position;
import edu.ycp.cs481.model.SOP;
import edu.ycp.cs481.model.User;
import edu.ycp.cs481.control.UserController;

public class UserControllerTest {
	private Position pos1, pos2, pos3, pos4;
	private SOP sop1, sop2;
	private User user1, user2; 
	private List<Position> positionList; 
	private List<SOP> sopList;
	private UserController uc;
	
	@Before
	public void setUp(){
		positionList = new ArrayList<Position>(); 
		sopList = new ArrayList<SOP>(); 
		
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
		
		user1 = new User();
		user1.setEmail("Admin@google.com");
		user1.setFirstname("Rodger");
		user1.setLastname("Smith");
		user1.setUserID(12);
		user1.setPosition(pos1);
		user1.setAdminFlag(true);
		
		user2 = new User();
		user2.setFirstname("Stan");
		user2.setLastname("Smith");
		user2.setEmail("rookie@email.com");
		user2.setAdminFlag(false);
		
		positionList.add(pos1);
		positionList.add(pos2);
		positionList.add(pos3);
		positionList.add(pos4);
		
		sop1 = new SOP(); 
		sop1.setName("Login");
		sop1.setID(1);
		sop1.setDescription("How to login");
		sop1.setPriority(7);
		sop1.setRevision(1);
		sop1.setAuthorID(user1.getUserID());
		sop1.setPositionsAffected(positionList);
		
		sop2 = new SOP(); 
		sop2.setName("Logout");
		sop2.setDescription("How to logout");
		sop2.setPriority(7);
		sop2.setID(2);
		sop2.setRevision(2);
		sop2.setAuthorID(user1.getUserID());
		sop2.setPositionsAffected(positionList);
		
		sopList.add(sop1);
		sopList.add(sop2);
		
		
		uc = new UserController();
	}
	
	//@Test
	public void testValidateEmail() {
		// TODO:assertEquals(false, controller.validateEmail(u1.getEmail()));
	}
	
	
}
