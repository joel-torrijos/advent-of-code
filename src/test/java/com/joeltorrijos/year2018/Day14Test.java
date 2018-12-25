package com.joeltorrijos.year2018;

import static com.joeltorrijos.year2018.Day14.partOne;
import static com.joeltorrijos.year2018.Day14.partTwo;
import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class Day14Test {
		
	@Test
	void test() {
		assertEquals("5158916779", partOne(9));
		assertEquals("0124515891", partOne(5));
		assertEquals("9251071085", partOne(18));
		assertEquals("5941429882", partOne(2018));
	}
	
	
}
