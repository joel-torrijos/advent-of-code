package com.joeltorrijos.year2018;

import java.util.ArrayList;
import java.util.List;

public class Day14 {
	
	public static void main(String[] args) {

		System.out.println(partOne(320851));
		System.out.println(partTwo(320851));
		
	}
	
	public static String partOne(int input) {
		
		List<Integer> recipes = new ArrayList<>();

		recipes.add(3);
		recipes.add(7);
		
		int firstElf = 0;
		int secondElf = 1;
		
		int turn = 0;
		do {
			int newRecipe = recipes.get(firstElf) + recipes.get(secondElf);
			String newRecipeString = String.valueOf(newRecipe);
			
			newRecipeString.chars().map(i -> i - 48).forEach(i -> recipes.add(i));

			int newFirstElf = 1 + recipes.get(firstElf);
			firstElf = (newFirstElf +firstElf) % recipes.size();
			int newSecondElf = 1 + recipes.get(secondElf);
			secondElf = (newSecondElf + secondElf) % recipes.size();
	
			turn++;
		} while(recipes.size() <= (input+10));

		StringBuffer sb = new StringBuffer();
		for(int i = 0, j = 0; i < recipes.size() && j < 10; i++) {
			if(i < input) {
				
			} else {
				sb.append(recipes.get(i));
				j++;
			}
		}
		
		return new String(sb);
	}
	
	public static void prettyDisplay(int turn, List<Integer> recipes, int first, int second) {
		System.out.print("Turn " + turn + "\t");
		for(int i = 0; i < recipes.size(); i++) {
			if(i == first) {
				System.out.print("("+recipes.get(i)+") ");
			} else if (i == second) {
				System.out.print("["+recipes.get(i)+"] ");
			} else {
				System.out.print(recipes.get(i) + " ");
				
			}
		}
		System.out.println();
	}
	
	
	public static int partTwo(int input) {
		StringBuilder target = new StringBuilder();
		target.append(3);
		target.append(7);
		int firstElf = 0;
		int secondElf = 1;

		int turn = 0;
		do {
			int newRecipe = (target.charAt(firstElf) - '0') + (target.charAt(secondElf) - '0');
			if(newRecipe > 9) {
				target.append(String.valueOf(newRecipe / 10));
				target.append(String.valueOf(newRecipe % 10));
			} else {
				target.append(String.valueOf(newRecipe));
				
			}
			
			int newFirstElf = 1 + (target.charAt(firstElf) - '0');
			firstElf = (newFirstElf +firstElf) % target.length();
			int newSecondElf = 1 + (target.charAt(secondElf) - '0');
			secondElf = (newSecondElf + secondElf) % target.length();
			turn++;
		} while(turn <= 30000000);
		
		return target.indexOf(String.valueOf(input));
	}
	

}
