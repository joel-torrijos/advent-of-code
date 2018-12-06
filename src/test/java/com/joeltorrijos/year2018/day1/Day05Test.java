package com.joeltorrijos.year2018.day1;

import static com.joeltorrijos.year2018.day1.Day05.partOne;
import static com.joeltorrijos.year2018.day1.Day05.partTwo;
import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class Day05Test {
	private String fileName = "src/main/resources/2018/day05-test-input.txt";
		
	@Test
	void test() {
		assertEquals(10, partOne(fileName));
	}
	
	@Test
	void partTwoTest() {
		assertEquals(4, partTwo(fileName));		
	}
}
