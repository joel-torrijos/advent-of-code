package com.joeltorrijos.year2018;

import static com.joeltorrijos.year2018.Day06.partOne;
import static com.joeltorrijos.year2018.Day06.partTwo;
import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class Day06Test {
	private String fileName = "src/main/resources/2018/day06-test-input.txt";
		
	@Test
	void test() {
		assertEquals(17, partOne(fileName));
	}
	
	@Test
	void partTwoTest() {
		assertEquals(16, partTwo(fileName));		
	}
}
