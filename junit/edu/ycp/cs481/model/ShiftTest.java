package edu.ycp.cs481.model;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;

import org.junit.Before;
import org.junit.Test;

public class ShiftTest{
	private Shift shift;
	private Timestamp in, out;
	
	@Before
	public void setup(){
		in = new Timestamp(1543172760);
		out = new Timestamp(1543183572);
		shift = new Shift(in, out, 3);
	}
	
	@Test
	public void testGetTimeIn(){
		assertEquals(in, shift.getTimeIn());
	}
	
	@Test
	public void testGetTimeOut(){
		assertEquals(out, shift.getTimeOut());
	}
	
	@Test
	public void testGetHours(){
		assertEquals(3, shift.getHours());
	}
}
