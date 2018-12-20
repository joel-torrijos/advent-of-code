package com.joeltorrijos.year2018.day16;

import java.util.function.BiFunction;

public class Opcode {
	
	public static Void addr(int[] instructions, int[] registers) { 
		registers[instructions[3]] = registers[instructions[1]] + registers[instructions[2]];
		return null;
	
	}
	
	public static Void addi(int[] instructions, int[] registers) {
		registers[instructions[3]] = registers[instructions[1]] + instructions[2]; 
		return null; 
	}
	
	
	public static Void mulr(int[] instructions, int[] registers) { 
		registers[instructions[3]] = registers[instructions[1]] * registers[instructions[2]]; 
		return null; 
	}
	
	public static Void muli(int[] instructions, int[] registers) {
		registers[instructions[3]] = registers[instructions[1]] * instructions[2]; 
		return null; 
	}
		
	public static Void banr(int[] instructions, int[] registers) { 
		registers[instructions[3]] = registers[instructions[1]] & registers[instructions[2]]; 
		return null; 
	}
		
	public static Void bani(int[] instructions, int[] registers) { 
		registers[instructions[3]] = registers[instructions[1]] & instructions[2]; 
		return null; 
	}
		
	public static Void borr(int[] instructions, int[] registers) {
		registers[instructions[3]] = registers[instructions[1]] | registers[instructions[2]]; 
		return null; 
	}
		
	public static Void bori(int[] instructions, int[] registers) {
		registers[instructions[3]] = registers[instructions[1]] | instructions[2]; 
		return null; 
	}
	
	public static Void setr(int[] instructions, int[] registers) { 
		registers[instructions[3]] = registers[instructions[1]]; 
		return null; 
	}
		
	public static Void seti(int[] instructions, int[] registers) { 
		registers[instructions[3]] = instructions[1]; 
		return null;
	}
		
	public static Void gtir(int[] instructions, int[] registers) { 
		if(instructions[1] > registers[instructions[2]]) registers[instructions[3]] = 1;
		else registers[instructions[3]] = 0; 
		return null; 
	}
		
	public static Void gtri(int[] instructions, int[] registers) { 
		if(registers[instructions[1]] > instructions[2]) registers[instructions[3]] = 1;
		else registers[instructions[3]] = 0; 
		return null; 
	}
		
	public static Void gtrr(int[] instructions, int[] registers) { 
		if(registers[instructions[1]] > registers[instructions[2]]) registers[instructions[3]] = 1;
		else registers[instructions[3]] = 0; 
		return null; 
	}
		
	public static Void eqir(int[] instructions, int[] registers) { 
		if(instructions[1] == registers[instructions[2]]) registers[instructions[3]] = 1;
		else registers[instructions[3]] = 0; 
		return null; 
	}
		
	public static Void eqri(int[] instructions, int[] registers) { 
		if(registers[instructions[1]] == instructions[2]) registers[instructions[3]] = 1;
		else registers[instructions[3]] = 0; 
		return null;
	}
			
	public static Void eqrr(int[] instructions, int[] registers) { 
		if(registers[instructions[1]] == registers[instructions[2]]) registers[instructions[3]] = 1;
		else registers[instructions[3]] = 0; 
		return null; 
	}

}
