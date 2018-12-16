package com.joeltorrijos.year2018.day15;

import static com.joeltorrijos.year2018.day15.Day15.partOne;
import static com.joeltorrijos.year2018.day15.Day15.partTwo;
import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

class Day15Test {
	
	@Test
	void test() {
		String fileName = "src/main/resources/2018/day15/day15-test-input2.txt";
//		assertEquals(36334, partOne(fileName));
		fileName = "src/main/resources/2018/day15/day15-test-input3.txt";
//		assertEquals(39514, partOne(fileName));
		fileName = "src/main/resources/2018/day15/day15-test-input4.txt";
//		assertEquals(27755, partOne(fileName));
//		fileName = "src/main/resources/2018/day15/day15-test-input5.txt";
//		assertEquals(28944, partOne(fileName));
		fileName = "src/main/resources/2018/day15/day15-test-input6.txt";
		assertEquals(18740, partOne(fileName));
	}
	
	@Test
	void partTwoTest() {
//		String fileName = "src/main/resources/2018/day13-test-input2.txt";
//		assertEquals("6,4", partTwo(fileName));		
	}

}
