package com.joeltorrijos.year2018.day16;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day16 {
	public static void main(String[] args) {
//		String fileName = "src/main/resources/2018/day16/day16-test-input.txt";
		String fileName1 = "src/main/resources/2018/day16/day16-input.txt";
		String fileName2 = "src/main/resources/2018/day16/day16-inputb.txt";
		
		System.out.println(partOne(fileName1));;
		System.out.println(partTwo(fileName2));;
	}
	
	public static int partOne(String fileName) {
		
		List<CPUActivity> activities = getInputs(fileName);
		
		Map<String, Predicate<CPUActivity>> opcodeTests = opcodeTests();
		
		int equalOrMoreThanThree = 0;
		
		for(CPUActivity activity : activities) {
			long count = opcodeTests.entrySet().stream()
											   .map(entry -> isValidForOpcode(activity, entry.getValue()))
											   .filter(x -> x == true)
											   .count();
			
			if(count >= 3) {
				equalOrMoreThanThree++;
			}
		}
		
		return equalOrMoreThanThree;
	}

	
	public static int partTwo(String fileName) {
		
//		mapNumberToOpcode

		int[] registers = {0,0,0,0};
		Map<Integer, BiFunction<int[], int[], Void>> opcodes = opcodes();
		
		try(Stream<String> stream = Files.lines(Paths.get(fileName))) {
			
			List<String[]> splitLines = stream.map(line -> line.split(" ")).collect(Collectors.toList());
			List<int[]> instructions = splitLines.stream().map(arr ->  Stream.of(arr).mapToInt(Integer::parseInt).toArray()).collect(Collectors.toList());
			
			for(int[] instruction : instructions) {
				opcodes.get(instruction[0]).apply(instruction, registers);
			}

			} catch (IOException e) {
				e.printStackTrace();
			}

			return registers[0];
			
		}
	
	public static Map<String, Predicate<CPUActivity>> opcodeTests() {
		
		Map<String, Predicate<CPUActivity>> opcodeTests = new HashMap<>();
		
		// Addition
		opcodeTests.put("addr", input -> (input.getBefore()[input.getInstruction()[1]] + input.getBefore()[input.getInstruction()[2]] == input.getAfter()[input.getInstruction()[3]]));
		opcodeTests.put("addi", input -> (input.getBefore()[input.getInstruction()[1]] + input.getInstruction()[2] == input.getAfter()[input.getInstruction()[3]]));
		
		// Multiplication
		opcodeTests.put("mulr", input -> (input.getBefore()[input.getInstruction()[1]] * input.getBefore()[input.getInstruction()[2]] == input.getAfter()[input.getInstruction()[3]]));
		opcodeTests.put("muli", input -> (input.getBefore()[input.getInstruction()[1]] * input.getInstruction()[2] == input.getAfter()[input.getInstruction()[3]]));
		
		// Bitwise AND
		opcodeTests.put("banr", input -> ((input.getBefore()[input.getInstruction()[1]] & input.getBefore()[input.getInstruction()[2]]) == input.getAfter()[input.getInstruction()[3]]));
		opcodeTests.put("bani", input -> ((input.getBefore()[input.getInstruction()[1]] & input.getInstruction()[2]) == input.getAfter()[input.getInstruction()[3]]));
		
		// Bitwise OR
		opcodeTests.put("borr", input -> ((input.getBefore()[input.getInstruction()[1]] | input.getBefore()[input.getInstruction()[2]]) == input.getAfter()[input.getInstruction()[3]]));
		opcodeTests.put("bori", input -> ((input.getBefore()[input.getInstruction()[1]] | input.getInstruction()[2]) == input.getAfter()[input.getInstruction()[3]]));

		// Assignment
		opcodeTests.put("setr", input -> (input.getBefore()[input.getInstruction()[1]] == input.getAfter()[input.getInstruction()[3]]));
		opcodeTests.put("seti", input -> (input.getInstruction()[1] == input.getAfter()[input.getInstruction()[3]]));
		
		// Greater-than testing
		opcodeTests.put("gtir", input -> {
			if(input.getInstruction()[1] > input.getBefore()[input.getInstruction()[2]]) {
				return input.getAfter()[input.getInstruction()[3]] == 1;
			} else
				return input.getAfter()[input.getInstruction()[3]] == 0;
		});
		opcodeTests.put("gtri", input -> {
			if(input.getBefore()[input.getInstruction()[1]] > input.getInstruction()[2]) {
				return input.getAfter()[input.getInstruction()[3]] == 1;
			} else
				return input.getAfter()[input.getInstruction()[3]] == 0;
		});
		opcodeTests.put("gtrr", input -> {
			if(input.getBefore()[input.getInstruction()[1]] > input.getBefore()[input.getInstruction()[2]]) {
				return input.getAfter()[input.getInstruction()[3]] == 1;
			} else
				return input.getAfter()[input.getInstruction()[3]] == 0;
		});
		
		// Equality testing
		opcodeTests.put("eqir", input -> {
			if(input.getInstruction()[1] == input.getBefore()[input.getInstruction()[2]]) {
				return input.getAfter()[input.getInstruction()[3]] == 1;
			} else
				return input.getAfter()[input.getInstruction()[3]] == 0;
		});
		opcodeTests.put("eqri", input -> {
			if(input.getBefore()[input.getInstruction()[1]] == input.getInstruction()[2]) {
				return input.getAfter()[input.getInstruction()[3]] == 1;
			} else
				return input.getAfter()[input.getInstruction()[3]] == 0;
		});
		opcodeTests.put("eqrr", input -> {
			if(input.getBefore()[input.getInstruction()[1]] == input.getBefore()[input.getInstruction()[2]]) {
				return input.getAfter()[input.getInstruction()[3]] == 1;
			} else
				return input.getAfter()[input.getInstruction()[3]] == 0;
		});
		
		return opcodeTests;
	}
	
	public static Map<Integer, BiFunction<int[], int[], Void>> opcodes() {
		
		BiFunction<int[], int[], Void> addr = (instructions, registers) -> { 
			registers[instructions[3]] = registers[instructions[1]] + registers[instructions[2]];
			return null; };
			
		BiFunction<int[], int[], Void> addi = (instructions, registers) -> {
			registers[instructions[3]] = registers[instructions[1]] + instructions[2]; 
			return null; };
			
		BiFunction<int[], int[], Void> mulr = (instructions, registers) -> { 
			registers[instructions[3]] = registers[instructions[1]] * registers[instructions[2]]; 
			return null; };
			
		BiFunction<int[], int[], Void> muli = (instructions, registers) -> {
			registers[instructions[3]] = registers[instructions[1]] * instructions[2]; 
			return null; };
			
		BiFunction<int[], int[], Void> banr = (instructions, registers) -> { 
			registers[instructions[3]] = registers[instructions[1]] & registers[instructions[2]]; 
			return null; };
			
		BiFunction<int[], int[], Void> bani = (instructions, registers) -> { 
			registers[instructions[3]] = registers[instructions[1]] & instructions[2]; 
			return null; };
			
		BiFunction<int[], int[], Void> borr = (instructions, registers) -> {
			registers[instructions[3]] = registers[instructions[1]] | registers[instructions[2]]; 
			return null; };
			
		BiFunction<int[], int[], Void> bori = (instructions, registers) -> {
			registers[instructions[3]] = registers[instructions[1]] | instructions[2]; 
			return null; };
		
		BiFunction<int[], int[], Void> setr = (instructions, registers) -> { 
			registers[instructions[3]] = registers[instructions[1]]; 
			return null; };
			
		BiFunction<int[], int[], Void> seti = (instructions, registers) -> { 
			registers[instructions[3]] = instructions[1]; 
			return null; };
			
		BiFunction<int[], int[], Void> gtir = (instructions, registers) -> { 
			if(instructions[1] > registers[instructions[2]]) registers[instructions[3]] = 1;
			else registers[instructions[3]] = 0; 
			return null; };
			
		BiFunction<int[], int[], Void> gtri = (instructions, registers) -> { 
			if(registers[instructions[1]] > instructions[2]) registers[instructions[3]] = 1;
			else registers[instructions[3]] = 0; 
			return null; };
			
		BiFunction<int[], int[], Void> gtrr = (instructions, registers) -> { 
			if(registers[instructions[1]] > registers[instructions[2]]) registers[instructions[3]] = 1;
			else registers[instructions[3]] = 0; 
			return null; };
			
		BiFunction<int[], int[], Void> eqir = (instructions, registers) -> { 
			if(instructions[1] == registers[instructions[2]]) registers[instructions[3]] = 1;
			else registers[instructions[3]] = 0; 
			return null; };
			
		BiFunction<int[], int[], Void> eqri = (instructions, registers) -> { 
			if(registers[instructions[1]] == instructions[2]) registers[instructions[3]] = 1;
			else registers[instructions[3]] = 0; 
			return null; };
				
		BiFunction<int[], int[], Void> eqrr = (instructions, registers) -> { 
			if(registers[instructions[1]] == registers[instructions[2]]) registers[instructions[3]] = 1;
			else registers[instructions[3]] = 0; 
			return null; };

		
//		0 muli 45
//		1 bani 48
//		2 addi 46
//		3 seti 59
//		4 eqrr 46
			
//		5 equir 50
//		6 setr 47
//		7 bori 51
//		8 gtri 51
//		9 eqri 42
			
//		10 gtir 59
//		11 borr 59
//		12 addr 41
//		13 gtrr 45
//		14 mulr 41
			
//		15 banr 48
		
		Map<Integer, BiFunction<int[], int[], Void>> opcodes = new HashMap<>();
		opcodes.put(0, muli);
		opcodes.put(1, bani);
		opcodes.put(2, addi);
		opcodes.put(3, seti);
		opcodes.put(4, eqrr);
		
		opcodes.put(5, eqir);
		opcodes.put(6, setr);
		opcodes.put(7, bori);
		opcodes.put(8, gtri);
		opcodes.put(9, eqri);
		
		opcodes.put(10, gtir);
		opcodes.put(11, borr);
		opcodes.put(12, addr);
		opcodes.put(13, gtrr);
		opcodes.put(14, mulr);
		
		opcodes.put(15, banr);
		
		return opcodes;
	}
	
	public static List<CPUActivity> getInputs(String fileName){
		
		List<CPUActivity> activities = new ArrayList<>();
		
		try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line;
			String before;
			String instructions;
			String after;
			
			while ((line = br.readLine()) != null) {
				before = line;
				List<Integer> beforeList = new ArrayList<>();
				Pattern p = Pattern.compile("\\d+");
				Matcher m = p.matcher(before);
				while(m.find()) {
					beforeList.add(Integer.parseInt(m.group()));
				}
				
				int[] beforeArr =  beforeList.stream().mapToInt(i->i).toArray();
				m.reset();
				
				instructions = br.readLine();
				List<Integer> instructionList = new ArrayList<>();
				m = p.matcher(instructions);
				while(m.find()) {
					instructionList.add(Integer.parseInt(m.group()));
				}
				int[] instructionArr =  instructionList.stream().mapToInt(i->i).toArray();
				m.reset();
				
				after = br.readLine();
				List<Integer> afterList = new ArrayList<>();
				m = p.matcher(after);
				while(m.find()) {
					afterList.add(Integer.parseInt(m.group()));
				}
				int[] afterArr =  afterList.stream().mapToInt(i->i).toArray();
				
				
				activities.add(new CPUActivity(beforeArr, instructionArr, afterArr));
				
				br.skip(1);
		    }
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return activities;
	}
	
	// TODO
	public static void mapNumberToOpcode(String fileName) {
		List<CPUActivity> activities = getInputs(fileName);
		
		Map<String, Predicate<CPUActivity>> opcodeTests = opcodeTests();
		
		Map<String, Integer> score = new HashMap<>();
 
		for(CPUActivity activity : activities) {
			int opcode = activity.getInstruction()[0];
			Set<String> potentialOpcodes = opcodeTests.entrySet().stream()
														.filter(entry -> isValidForOpcode(activity,entry.getValue()))
														.map(entry -> opcode + " " + entry.getKey())
														.collect(Collectors.toSet());
			
			potentialOpcodes.forEach(potential -> score.put(potential, score.getOrDefault(potential, 0) + 1));
			
		}
		
		System.out.println("Scores");
		score.entrySet().stream().sorted(Comparator.comparing(Entry::getKey)).forEach(entry -> System.out.println(entry.getKey() + " " + entry.getValue()));
	}
	
	public static boolean isValidForOpcode(CPUActivity input, Predicate<CPUActivity> opcode) {
		return opcode.test(input);
	}
	
	
}
