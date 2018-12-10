package com.joeltorrijos.year2018.day1;

import static com.joeltorrijos.year2018.day1.Day09.partOne;
import static com.joeltorrijos.year2018.day1.Day09.partTwo;
import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class Day09Test {
//	private String fileName = "src/main/resources/2018/day09-test-input.txt";
		
	@Test
	void test() {
//		assertEquals(32, partOne(9, 25));
//		assertEquals(8317, partOne(10, 1618));
//		assertEquals(2764, partOne(17, 1104));
//		assertEquals(54718, partOne(21, 6111));
//		assertEquals(37305, partOne(30, 5807));
	}
	
	@Test
	void testTwo() {
		assertEquals(32, partTwo(9, 25));
		assertEquals(8317, partTwo(10, 1618));
		assertEquals(2764, partTwo(17, 1104));
		assertEquals(54718, partTwo(21, 6111));
		assertEquals(37305, partTwo(30, 5807));		
	}
}
