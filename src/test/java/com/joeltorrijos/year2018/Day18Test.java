package com.joeltorrijos.year2018;

import static com.joeltorrijos.year2018.Day18.partOne;
import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class Day18Test {
	private String fileName = "src/main/resources/2018/day18/day18-test-input.txt";
	
	@Test
	void test() {
		assertEquals(1147, partOne(fileName, 10));
	}
	
}
