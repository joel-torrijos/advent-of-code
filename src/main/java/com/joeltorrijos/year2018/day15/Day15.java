package com.joeltorrijos.year2018.day15;

public class Day15 {
	
	public static void main(String[] args) {
//		String fileName = "src/main/resources/2018/day13-test-input3.txt";
//		String fileName = "src/main/resources/2018/day15/day15-input.txt";
//		String fileName = "src/main/resources/2018/day15/day15-test-input2.txt";
//		String fileName = "src/main/resources/2018/day15/choose-target-test-input.txt";
		String fileName = "src/main/resources/2018/day15/shortest-path-input.txt";
//		String fileName = "src/main/resources/2018/day15/movement-test-input.txt";
//		String fileName = "src/main/resources/2018/day15/attack-test-input.txt";

		System.out.println(partOne(fileName));
		System.out.println(partTwo(fileName));
		
	}
	
	public static int partOne(String fileName) {
	
//		CombatSimulation combatSim = new CombatSimulation(fileName);
//		
//		return combatSim.simulate();
		
		CombatSimulation2 combatSim = new CombatSimulation2(fileName);
		
		return combatSim.simulate();
	}
	
	
	public static String partTwo(String fileName) {
//
//		TrackSimulation trackSim = new TrackSimulation(fileName);
		
		return "";
//		return trackSim.simulate2();
	}
	

}
