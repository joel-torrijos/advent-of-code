package com.joeltorrijos.year2018.day1;

import static com.joeltorrijos.year2018.day1.FrequencyCounter.partOne;
import static com.joeltorrijos.year2018.day1.FrequencyCounter.partTwo;
import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

class Day1Test {
	
	private String fileName = "src/main/resources/2018/input2.txt";
	
	@Test
	void test() {
		assertEquals(Integer.valueOf(3), partOne(fileName));
	}
	
	@Test
	void partTwoTest() {
		assertEquals(Integer.valueOf(2), partTwo(fileName));		
	}

}
