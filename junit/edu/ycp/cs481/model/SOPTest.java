package edu.ycp.cs481.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs481.model.SOP;

public class SOPTest{
	private SOP sop;
	
	@Before
	public void setup(){
		sop = new SOP();
	}
	
	@Test
	public void testSetID(){
		sop.setID(5);
		assertEquals(5, sop.getID());
		
		// Change ID
		sop.setID(1);
		
		// Test new ID
		assertEquals(1, sop.getID());
	}
	
	@Test
	public void testSetTitle(){
		sop.setTitle("How to Avoid Sexual Harassment");
		assertEquals("How to Avoid Sexual Harassment", sop.getTitle());
		
		// Change name
		sop.setTitle("How to Avoid Sexual Harassment (For Men)");
		
		// Test new name
		assertEquals("How to Avoid Sexual Harassment (For Men)", sop.getTitle());
	}
	
	@Test
	public void testSetDescription(){
		sop.setDescription("A how-to guide for men on avoiding sexually harassing women in the workplace environment.");
		assertEquals("A how-to guide for men on avoiding sexually harassing women in the workplace environment.", 
				sop.getDescription());
		
		// Change description
		sop.setDescription("A how-to guide for men on avoiding sexually harassing women in the workplace environment, "
				+ "Sixty-Ninth Edition");
		
		// Test new description
		assertEquals("A how-to guide for men on avoiding sexually harassing women in the workplace environment, "
				+ "Sixty-Ninth Edition", sop.getDescription());
	}
	
	@Test
	public void testSetPriority(){
		sop.setPriority(1);
		assertEquals(1, sop.getPriority());
		
		// Change priority
		sop.setPriority(100);
		
		// Test new priority
		assertEquals(100, sop.getPriority());
	}
	
	@Test
	public void testSetVersion(){
		sop.setVersion(2);
		assertEquals(2, sop.getVersion());
		
		//set a new revision
		sop.setVersion(4);
		
		//test the new revision 
		assertEquals(4, sop.getVersion());
		
	}
	
	@Test
	public void testSetAuthorID(){
		sop.setAuthorID(70);
		assertEquals(70, sop.getAuthorID());
		
		// Change Author ID
		sop.setAuthorID(25);
		
		// Test new Author ID
		assertEquals(25, sop.getAuthorID());
	}
	
	@Test
	public void testSetArchived(){
		sop.setArchived(false);
		assertEquals(false, sop.isArchived());
		
		sop.setArchived(false);
		assertEquals(false, sop.isArchived());
		
		sop.setArchived(true);
		assertEquals(true, sop.isArchived());
	}
	
	@Test
	public void testSetComplete(){
		sop.setComplete(false);
		assertEquals(false, sop.isComplete());
		
		sop.setComplete(false);
		assertEquals(false, sop.isComplete());
		
		sop.setComplete(true);
		assertEquals(true, sop.isComplete());
	}
	
}

