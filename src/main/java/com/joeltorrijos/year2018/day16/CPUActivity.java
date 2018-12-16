package com.joeltorrijos.year2018.day16;

import java.util.Arrays;

public class CPUActivity {
	private int[] before;
	private int[] instruction;
	private int[] after;
	
	public CPUActivity(int[] before, int[] instruction, int[] after) {
		this.before = before;
		this.instruction = instruction;
		this.after = after;
	}

	public int[] getBefore() {
		return before;
	}

	public void setBefore(int[] before) {
		this.before = before;
	}

	public int[] getInstruction() {
		return instruction;
	}

	public void setInstruction(int[] instruction) {
		this.instruction = instruction;
	}

	public int[] getAfter() {
		return after;
	}

	public void setAfter(int[] after) {
		this.after = after;
	}

	@Override
	public String toString() {
		return "CPUActivity [before=" + Arrays.toString(before) + ", instruction=" + Arrays.toString(instruction)
				+ ", after=" + Arrays.toString(after) + "]";
	}
	
	
	
}

