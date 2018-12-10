package com.joeltorrijos.year2018.day1;

import static com.joeltorrijos.year2018.day1.Day07a.partOne;
//import static com.joeltorrijos.year2018.day1.Day07.partTwo;
import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class Day07Test {
	private String fileName = "src/main/resources/2018/day07-test-input.txt";
		
	@Test
	void test() {
		assertEquals("CABDFE", partOne(fileName));
	}
	
	@Test
	void partTwoTest() {
//		assertEquals(16, partTwo(fileName));		
	}
}
