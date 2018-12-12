package com.joeltorrijos.year2018.day1;

import static com.joeltorrijos.year2018.day1.Day12.partOne;
import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class Day12Test {
	private String fileName = "src/main/resources/2018/day12-test-input.txt";
		
	@Test
	void test() {
		assertEquals(325, partOne(fileName, 20));

	}
}
