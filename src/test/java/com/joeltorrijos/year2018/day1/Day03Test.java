package com.joeltorrijos.year2018.day1;

import static com.joeltorrijos.year2018.day1.Day03.partOne;
import static com.joeltorrijos.year2018.day1.Day03.partTwo;
import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class Day03Test {
	private String fileName = "src/main/resources/2018/day03-test-input.txt";
		
	@Test
	void test() {
		assertEquals(4, partOne(fileName));
	}
	
	@Test
	void partTwoTest() {
		assertEquals("3", partTwo(fileName));		
	}
}
