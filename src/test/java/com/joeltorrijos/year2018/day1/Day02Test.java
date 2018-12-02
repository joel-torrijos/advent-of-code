package com.joeltorrijos.year2018.day1;

import static com.joeltorrijos.year2018.day1.Day02.partOne;
import static com.joeltorrijos.year2018.day1.Day02.partTwo;
import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class Day02Test {
	private String fileName = "src/main/resources/2018/day02-test-input.txt";
//	private String fileName2 = "src/main/resources/2018/day02-test-input.txt";
		
	@Test
	void test() {
		assertEquals(12, partOne(fileName));
	}
	
	@Test
	void partTwoTest() {
		fileName = "src/main/resources/2018/day02-test-input2.txt";
		assertEquals("fgij", partTwo(fileName));		
	}
}
