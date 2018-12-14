package edu.ycp.cs481.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ComplianceIssueTest{
	private ComplianceIssue c;
	
	@Before
	public void setup(){
		c = new ComplianceIssue();
	}
	
	@Test
	public void testGetAndSetTitle(){
		c.setTitle("Some SOP");
		assertEquals("Some SOP", c.getTitle());
		
		c.setTitle("Some Other SOP");
		assertEquals("Some Other SOP", c.getTitle());
	}
	
	@Test
	public void testGetAndSetPriority(){
		c.setPriority(5);
		assertEquals(5, c.getPriority());
		
		c.setPriority(10);
		assertEquals(10, c.getPriority());
	}
	
	@Test
	public void testGetAndSetEmail(){
		c.setEmail("derp@derpykins.com");
		assertEquals("derp@derpykins.com", c.getEmail());
		
		c.setEmail("plop@grabage.com");
		assertEquals("plop@grabage.com", c.getEmail());
	}
}
