package com.joeltorrijos.year2018.day13;

public class Day13 {
	
	public static void main(String[] args) {
//		String fileName = "src/main/resources/2018/day13-test-input3.txt";
		String fileName = "src/main/resources/2018/day13-input.txt";

		System.out.println(partOne(fileName));
		System.out.println(partTwo(fileName));
		
	}
	
	public static String partOne(String fileName) {
	
		TrackSimulation trackSim = new TrackSimulation(fileName);
		
		return trackSim.simulate();
	}
	
	
	public static String partTwo(String fileName) {

		TrackSimulation trackSim = new TrackSimulation(fileName);
		
		return trackSim.simulate2();
	}
	

}
