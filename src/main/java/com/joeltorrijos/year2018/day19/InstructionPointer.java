package com.joeltorrijos.year2018.day19;

import java.util.Arrays;

public class InstructionPointer {
	
	private int[] registers;
	private int boundRegister;
	

	public InstructionPointer(int boundRegister) {
		registers = new int[6];
		Arrays.fill(registers, 0);
		
		this.boundRegister = boundRegister;
	}

	public int[] getRegisters() {
		return registers;
	}

	public void setRegisters(int[] registers) {
		this.registers = registers;
	}

	public int getBoundRegister() {
		return boundRegister;
	}

	public void setBoundRegister(int boundRegister) {
		this.boundRegister = boundRegister;
	}

	@Override
	public String toString() {
		return "InstructionPointer [registers=" + Arrays.toString(registers) + ", boundRegister=" + boundRegister + "]";
	}
	
	


}
