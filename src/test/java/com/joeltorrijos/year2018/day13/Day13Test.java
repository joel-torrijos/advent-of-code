package com.joeltorrijos.year2018.day13;

import static com.joeltorrijos.year2018.day13.Day13.partOne;
import static com.joeltorrijos.year2018.day13.Day13.partTwo;
import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

class Day13Test {
	
	@Test
	void test() {
		String fileName = "src/main/resources/2018/day13-test-input.txt";
		assertEquals("7,3", partOne(fileName));
	}
	
	@Test
	void partTwoTest() {
		String fileName = "src/main/resources/2018/day13-test-input2.txt";
		assertEquals("6,4", partTwo(fileName));		
	}

}
