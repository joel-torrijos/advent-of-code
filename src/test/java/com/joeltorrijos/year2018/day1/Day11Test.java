package com.joeltorrijos.year2018.day1;

import static com.joeltorrijos.year2018.day1.Day11.partOne;
import static com.joeltorrijos.year2018.day1.Day11.partTwo;
import static com.joeltorrijos.year2018.day1.Day11.partTwoSumTable;
import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class Day11Test {
		
	@Test
	void test() {
		assertEquals("33,45", partOne(18));
		assertEquals("21,61", partOne(42));
	}
	
	@Test
	void testTwo() {
		assertEquals("90,269,16", partTwo(18));
		assertEquals("232,251,12", partTwo(42));
	}
	
	@Test
	void testTwoSumTable() {
		assertEquals("90,269,16", partTwoSumTable(18));
		assertEquals("232,251,12", partTwoSumTable(42));
		
	}
}
