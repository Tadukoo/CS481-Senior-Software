package edu.ycp.cs481.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class DBFormatTest{
	private StringBuilder name, sql;
	
	@Before
	public void setup(){
		name = new StringBuilder("Derpity doodle dee");
		sql = new StringBuilder("Derp I'm a SQL statement");
		assertEquals("Derpity doodle dee", name.toString());
		assertEquals("Derp I'm a SQL statement", sql.toString());
	}
	
	@Test
	public void testAddConditionalAndToQuery(){
		DBFormat.addConditionalAndToQuery(true, name, sql);
		
		assertEquals("Derpity doodle dee and ", name.toString());
		assertEquals("Derp I'm a SQL statement and ", sql.toString());
	}
	
	@Test
	public void testAddConditionalAndToQueryNoAdd(){
		DBFormat.addConditionalAndToQuery(false, name, sql);
		
		assertEquals("Derpity doodle dee", name.toString());
		assertEquals("Derp I'm a SQL statement", sql.toString());
	}
	
	@Test
	public void testAddConditionalIntToQuery(){
		boolean done = DBFormat.addConditionalIntToQuery(false, name, sql, "derp", 56);
		
		assertEquals("Derpity doodle deederp of 56", name.toString());
		assertEquals("Derp I'm a SQL statementderp = 56", sql.toString());
		assertTrue(done);
	}
	
	@Test
	public void testAddConditionalIntToQueryNoAdd(){
		boolean done = DBFormat.addConditionalIntToQuery(false, name, sql, "plop", -1);
		
		assertEquals("Derpity doodle dee", name.toString());
		assertEquals("Derp I'm a SQL statement", sql.toString());
		assertFalse(done);
	}
	
	@Test
	public void testAddConditionalFullStringToQuery(){
		boolean done = DBFormat.addConditionalStringToQuery(false, name, sql, false, "derp", "whatevs");
		
		assertEquals("Derpity doodle deederp of whatevs", name.toString());
		assertEquals("Derp I'm a SQL statementderp = 'whatevs'", sql.toString());
		assertTrue(done);
	}
	
	@Test
	public void testAddConditionalPartialStringToQuery(){
		boolean done = DBFormat.addConditionalStringToQuery(false, name, sql, true, "plop", "somethin");
		
		assertEquals("Derpity doodle deeplop with somethin", name.toString());
		assertEquals("Derp I'm a SQL statementplop like '%somethin%'", sql.toString());
		assertTrue(done);
	}
	
	@Test
	public void testAddConditionalStringToQueryNoAddNull(){
		boolean done = DBFormat.addConditionalStringToQuery(true, name, sql, false, "derp", null);
		
		assertEquals("Derpity doodle dee", name.toString());
		assertEquals("Derp I'm a SQL statement", sql.toString());
		assertFalse(done);
	}
	
	@Test
	public void testAddConditionalStringToQueryNoAddEmpty(){
		boolean done = DBFormat.addConditionalStringToQuery(true, name, sql, true, "plop", "");
		
		assertEquals("Derpity doodle dee", name.toString());
		assertEquals("Derp I'm a SQL statement", sql.toString());
		assertFalse(done);
	}
	
	@Test
	public void testAddJunctionStringToQuery(){
		DBFormat.addJunctionStringToQuery(true, name, sql, "derp.plz = plop.stat");
		
		assertEquals("Derpity doodle dee and derp.plz = plop.stat", name.toString());
		assertEquals("Derp I'm a SQL statement and derp.plz = plop.stat", sql.toString());
	}
	
	@Test
	public void testFormatInsertStatement(){
		String stmt = DBFormat.formatInsertStatement("Derp", new String[]{"eh", "plop", "capstone"}, 
				new String[]{"5", "nah", "gross"});
		
		assertEquals("insert into Derp (eh, plop, capstone) select '5', 'nah', 'gross';", stmt);
	}
	
	@Test
	public void testFormatInsertStatementWithTrue(){
		String stmt = DBFormat.formatInsertStatement("Plop", new String[]{"whatevs"}, new String[]{"true"});
		
		assertEquals("insert into Plop (whatevs) select true;", stmt);
	}
	
	@Test
	public void testFormatInsertStatementWithFalse(){
		String stmt = DBFormat.formatInsertStatement("Plop", new String[]{"whatevs"}, new String[]{"false"});
		
		assertEquals("insert into Plop (whatevs) select false;", stmt);
	}
	
	@Test
	public void testFormatInsertStatementWithSHA(){
		String stmt = DBFormat.formatInsertStatement("Plop", new String[]{"whatevs"}, new String[]{"SHA('deokergiernbtrb')"});
		
		assertEquals("insert into Plop (whatevs) select SHA('deokergiernbtrb');", stmt);
	}
}
