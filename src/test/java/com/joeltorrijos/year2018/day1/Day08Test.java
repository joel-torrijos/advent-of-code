package com.joeltorrijos.year2018.day1;

import static com.joeltorrijos.year2018.day1.Day08.partOne;
import static com.joeltorrijos.year2018.day1.Day08.partTwo;
import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class Day08Test {
	private String fileName = "src/main/resources/2018/day08-test-input.txt";
		
	@Test
	void test() {
		assertEquals(138, partOne(fileName));
	}
	
	@Test
	void partTwoTest() {
		assertEquals(66, partTwo(fileName));		
	}
}
