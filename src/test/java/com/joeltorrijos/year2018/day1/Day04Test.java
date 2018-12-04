package com.joeltorrijos.year2018.day1;

import static com.joeltorrijos.year2018.day1.Day04.partOne;
import static com.joeltorrijos.year2018.day1.Day04.partTwo;
import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class Day04Test {
	private String fileName = "src/main/resources/2018/day04-test-input.txt";
		
	@Test
	void test() {
//		assertEquals(4, partOne(fileName));
	}
	
	@Test
	void partTwoTest() {
		assertEquals(4455, partTwo(fileName));		
	}
}
