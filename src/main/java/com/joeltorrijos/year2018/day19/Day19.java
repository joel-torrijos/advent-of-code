package com.joeltorrijos.year2018.day19;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.joeltorrijos.year2018.day16.Opcode;

public class Day19 {

	public static void main(String[] args) {
//		String fileName = "src/main/resources/2018/day19/day19-test-input.txt";
		String fileName = "src/main/resources/2018/day19/day19-input.txt";
	
		System.out.println(partOne(fileName));

	}
	
	public static List<String> getInputs(String fileName){
		
		List<String> instructions = new ArrayList<>();
		
		try(Stream<String> stream = Files.lines(Paths.get(fileName))) {
			
		    instructions = stream.collect(Collectors.toList());
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return instructions;
	}
	
	public static int partOne(String fileName) {
		
		List<String> instructions = getInputs(fileName);
		Map<String, BiFunction<int[], int[], Void>> opcodes = opcodes();
		
		int[] registers = new int[6];
		Arrays.fill(registers, 0);
		
		InstructionPointer ip = null;
		
		String instructionPointer = instructions.get(0);
		Pattern p = Pattern.compile("#ip\\s*(\\d)");
		Matcher m = p.matcher(instructionPointer);
		while(m.find()) {
			ip = new InstructionPointer(Integer.parseInt(m.group(1)));
		}


		Pattern opcodePattern = Pattern.compile("([a-zA-Z]*)\\s*(\\d*)\\s*(\\d*)\\s*(\\d*)\\s*");
		Matcher opcodeMatcher;
		
		while(ip.getRegisters()[ip.getBoundRegister()] < instructions.size() - 1) {

			opcodeMatcher = opcodePattern.matcher(instructions.get(ip.getRegisters()[ip.getBoundRegister()] + 1));
			int[] zzz = new int[4];
			Arrays.fill(zzz, 0);
			String opcode = "";
			if(opcodeMatcher.find()) {
				opcode = opcodeMatcher.group(1);
				zzz[1] = Integer.parseInt(opcodeMatcher.group(2));
				zzz[2] = Integer.parseInt(opcodeMatcher.group(3));
				zzz[3] = Integer.parseInt(opcodeMatcher.group(4));
			}

			opcodes.get(opcode).apply(zzz, registers);

			
			// Copy value of bounding register to instruction pointer and increment
			System.arraycopy(registers, 0, ip.getRegisters(), 0, registers.length);
			ip.getRegisters()[ip.getBoundRegister()]++;
//			System.out.println(ip);
			
			// Copy value back to register
			System.arraycopy(ip.getRegisters(), 0, registers, 0, ip.getRegisters().length);
			opcodeMatcher.reset();
		}

		return registers[0];
	}
	
	
	public static Map<String, BiFunction<int[], int[], Void>> opcodes() {
		Map<String, BiFunction<int[], int[], Void>> opcodes = new HashMap<>();

		opcodes.put("addr", Opcode::addr);
		opcodes.put("addi", Opcode::addi);
		opcodes.put("mulr", Opcode::mulr);
		opcodes.put("muli", Opcode::muli);
		opcodes.put("banr", Opcode::banr);
		opcodes.put("bani", Opcode::bani);
		opcodes.put("borr", Opcode::borr);
		opcodes.put("bori", Opcode::bori);
		opcodes.put("setr", Opcode::setr);
		opcodes.put("seti", Opcode::seti);
		opcodes.put("gtir", Opcode::gtir);
		opcodes.put("gtri", Opcode::gtri);
		opcodes.put("gtrr", Opcode::gtrr);
		opcodes.put("eqir", Opcode::eqir);
		opcodes.put("eqri", Opcode::eqri);
		opcodes.put("eqrr", Opcode::eqrr);
		
		return opcodes;
	
	}
	
	

}
