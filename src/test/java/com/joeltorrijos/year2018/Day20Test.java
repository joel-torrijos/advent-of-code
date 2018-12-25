package com.joeltorrijos.year2018;

import static com.joeltorrijos.year2018.day20.Day20.partOne;
import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class Day20Test {
		
	@Test
	void test() {
		assertEquals(3, partOne("^WNE$"));
		assertEquals(10, partOne("^ENWWW(NEEE|SSE(EE|N))$"));
		assertEquals(18, partOne("^ENNWSWW(NEWS|)SSSEEN(WNSE|)EE(SWEN|)NNN$"));
		assertEquals(3, partOne("^WNE$"));
	}
	
}
