package edu.ycp.cs481.model;
 
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs481.model.Position;
import edu.ycp.cs481.model.SOP;

public class PositionTest{
	private Position position;
	private ArrayList<SOP> requirements1, requirements2;
	private SOP sop1, sop2, sop3, sop4;
	
	@Before
	public void setUp(){
		position = new Position();
		
		sop1 = new SOP();
		sop1.setTitle("Derp I'm an SOP");
		sop1.setPriority(2);
		sop1.setComplete(false);
		
		sop2 = new SOP();
		sop2.setTitle("I'm another SOP");
		sop2.setDescription("wahteve");
		sop2.setComplete(true);
		
		sop3 = new SOP();
		sop3.setTitle("What a Surprise, I'm an SOP!");
		sop3.setArchived(true);
		sop3.setComplete(true);
		
		sop4 = new SOP();
		sop4.setTitle("Derp SOP");
		sop4.setComplete(false);
		
		requirements1 = new ArrayList<SOP>();
		requirements1.add(sop1);
		requirements1.add(sop2);
		requirements1.add(sop3);
		requirements1.add(sop4);
		
		requirements2 = new ArrayList<SOP>();
		requirements2.add(sop3);
		requirements2.add(sop1);
	}
	
	@Test
	public void testSetID(){
		position.setID(52);
		assertEquals(52, position.getID());
		position.setID(96);
		assertEquals(96, position.getID());
	}
	
	@Test
	public void testSetTitle(){
		position.setTitle("Carts Clerk");
		assertEquals("Carts Clerk", position.getTitle());
		position.setTitle("Front End Manager");
		assertEquals("Front End Manager", position.getTitle());
	}
	
	@Test
	public void testSetDescription(){
		position.setDescription("Pushes carts around...");
		assertEquals("Pushes carts around...", position.getDescription());
		position.setDescription("Controls all the cashiers. Mwahaha!");
		assertEquals("Controls all the cashiers. Mwahaha!", position.getDescription());
	}
	
	@Test
	public void testSetPriorit(){
		position.setPriority(10);
		assertEquals(10, position.getPriority());
		position.setPriority(5);
		assertEquals(5, position.getPriority());
	}
	
	@Test
	public void testSetRequirements(){
		position.setRequirements(requirements1);
		assertEquals(requirements1.size(), position.getRequirements().size());
		assertEquals(sop1, position.getRequirements().get(0));
		assertEquals(sop2, position.getRequirements().get(1));
		assertEquals(sop3, position.getRequirements().get(2));
		assertEquals(sop4, position.getRequirements().get(3));
		
		position.setRequirements(requirements2);
		assertEquals(requirements2.size(), position.getRequirements().size());
		assertEquals(sop3, position.getRequirements().get(0));
		assertEquals(sop1, position.getRequirements().get(1));
	}
	
	@Test
	public void testGetCompletedSOPs(){
		position.setRequirements(requirements1);
		ArrayList<SOP> completed = position.getCompletedSOPs();
		assertEquals(2, completed.size());
		assertEquals(sop2, completed.get(0));
		assertEquals(sop3, completed.get(1));
	}
	
	@Test
	public void testGetIncompleteSOPs(){
		position.setRequirements(requirements1);
		ArrayList<SOP> incomplete = position.getIncompleteSOPs();
		assertEquals(2, incomplete.size());
		assertEquals(sop1, incomplete.get(0));
		assertEquals(sop4, incomplete.get(1));
	}
}