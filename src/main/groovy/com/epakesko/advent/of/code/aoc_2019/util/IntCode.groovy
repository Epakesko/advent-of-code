package com.epakesko.advent.of.code.aoc_2019.util

import com.epakesko.advent.of.code.common.Util

class IntCode {
	int inputIdx = 0
	long pointer = 0
	long relativeBase = 0
	
	Map<Long, Long> memory = new HashMap<>();
	List input
	List output = []
	
	public IntCode(fileName) {
		Util.readFile(fileName)[0].split(",").collect{ it as Long }.eachWithIndex { elem, idx -> memory.put(idx as Long, elem)}
		this.input = []
	}
	
	public IntCode(fileName, input) {
		this(fileName)
		this.input = [input]
	}
	
	public IntCode(fileName, List input) {
		this(fileName)
		this.input = input
	}
	
	def run() {
		Instruction instruction = createInstruction(pointer)
		
		while(!(instruction instanceof StopInstruction)) {
			instruction.runInstruction();
			pointer += instruction.parameters.size() + 1
			instruction = createInstruction(pointer)
		}
		
		return output.join(", ")
	}
	
	def runUntilOutput() {
		Instruction instruction = createInstruction(pointer)
		output = []
		
		while(!(instruction instanceof StopInstruction)) {
			def instructionResult = instruction.runInstruction();
			
			pointer += instruction.parameters.size() + 1
			if(instruction instanceof OutputInstruction) break;
			
			instruction = createInstruction(pointer)
		}
		return output[0]
	}
	
	def runUntilInput() {
		output = []
		Instruction instruction = createInstruction(pointer)
		def instructionResult = instruction.runInstruction();
		pointer += instruction.parameters.size() + 1
		instruction = createInstruction(pointer)
		
		
		while(!(instruction instanceof StopInstruction || instruction instanceof InputInstruction)) {
			instructionResult = instruction.runInstruction();
			pointer += instruction.parameters.size() + 1
			instruction = createInstruction(pointer)
		}
		
		return output.join(", ")
	}
	
	def addInput(int input) {
		this.input << input
	}
	
	abstract class Instruction {
		long[] parameters
		List parameterModes
		
		abstract void runInstruction();
		
		long getParameterByIndex(int index) {
			int mode = parameterModes[index]
			long parameter = parameters[index]
			
			switch(mode) {
				case 0: return IntCode.this.getFromMemory(parameter)
				case 1:	return parameter
				case 2:	return IntCode.this.getFromMemory(relativeBase + parameter)
				default: return -1
			}
		}
		
		long getDestinationByIndex(int index) {
			int mode = parameterModes[index]
			long parameter = parameters[index]
			
			switch(mode) {
				case 0: 
				case 1:	return parameter
				case 2:	return relativeBase + parameter
				default: return -1
			}
		}
	}
	
	class AdditionInstruction extends Instruction {
		
		public AdditionInstruction(Long opCodeIndex, List parameterModes) {
			parameters = IntCode.this.getRangeFromMemory(opCodeIndex+1, opCodeIndex+3)
			this.parameterModes = parameterModes
		}
		
		@Override
		public void runInstruction(){
			IntCode.this.memory[getDestinationByIndex(2)] = (getParameterByIndex(0) + getParameterByIndex(1))
		}
	}
	
	class MultiplyInstruction extends Instruction {
		
		public MultiplyInstruction(Long opCodeIndex, List parameterModes) {
			parameters = IntCode.this.getRangeFromMemory(opCodeIndex+1, opCodeIndex+3)
			this.parameterModes = parameterModes
		}
		
		@Override
		public void runInstruction(){
			IntCode.this.memory[getDestinationByIndex(2)] = (getParameterByIndex(0) * getParameterByIndex(1))
		}
	}
	
	class InputInstruction extends Instruction {
	
		public InputInstruction(Long opCodeIndex, List parameterModes) {
			parameters = IntCode.this.getRangeFromMemory(opCodeIndex+1, opCodeIndex+1)
			this.parameterModes = parameterModes
		}
		
		@Override
		public void runInstruction() {
			IntCode.this.memory[getDestinationByIndex(0)] = IntCode.this.input[IntCode.this.inputIdx++]
		}
		
	}
	
	class OutputInstruction extends Instruction {
	
		public OutputInstruction(Long opCodeIndex, List parameterModes) {
			parameters = IntCode.this.getRangeFromMemory(opCodeIndex+1, opCodeIndex+1)
			this.parameterModes = parameterModes
		}
		
		@Override
		public void runInstruction() {
			output << getParameterByIndex(0)
		}
	}
	
	class JumpIfTrueInstruction extends Instruction {
	
		public JumpIfTrueInstruction(Long opCodeIndex, List parameterModes) {
			parameters = IntCode.this.getRangeFromMemory(opCodeIndex+1, opCodeIndex+2)
			this.parameterModes = parameterModes
		}
		
		@Override
		public void runInstruction() {
			if(getParameterByIndex(0)) {
				pointer = getParameterByIndex(1) - 3
			}
		}
	}
	
	class JumpIfFalseInstruction extends Instruction {
	
		public JumpIfFalseInstruction(Long opCodeIndex, List parameterModes) {
			parameters = IntCode.this.getRangeFromMemory(opCodeIndex+1, opCodeIndex+2)
			this.parameterModes = parameterModes
		}
		
		@Override
		public void runInstruction() {
			if(!getParameterByIndex(0)) {
				pointer = getParameterByIndex(1) - 3
			}
		}
	}
	
	class LessThanInstruction extends Instruction {
	
		public LessThanInstruction(Long opCodeIndex, List parameterModes) {
			parameters = IntCode.this.getRangeFromMemory(opCodeIndex+1, opCodeIndex+3)
			this.parameterModes = parameterModes
		}
		
		@Override
		public void runInstruction() {
			if(getParameterByIndex(0) < getParameterByIndex(1)) {
				IntCode.this.memory[getDestinationByIndex(2)] = 1
			}
			else {
				IntCode.this.memory[getDestinationByIndex(2)] = 0
			}
		}
	}
	
	class EqualsInstruction extends Instruction {
	
		public EqualsInstruction(Long opCodeIndex, List parameterModes) {
			parameters = IntCode.this.getRangeFromMemory(opCodeIndex+1, opCodeIndex+3)
			this.parameterModes = parameterModes
		}
		
		@Override
		public void runInstruction() {
			if(getParameterByIndex(0) == getParameterByIndex(1)) {
				IntCode.this.memory[getDestinationByIndex(2)] = 1
			}
			else {
				IntCode.this.memory[getDestinationByIndex(2)] = 0
			}
		}
	}
	
	class AdjustRelativeBaseInstruction extends Instruction {
	
		public AdjustRelativeBaseInstruction(Long opCodeIndex, List parameterModes) {
			parameters = IntCode.this.getRangeFromMemory(opCodeIndex+1, opCodeIndex+1)
			this.parameterModes = parameterModes
		}
		
		@Override
		public void runInstruction() {
			relativeBase += getParameterByIndex(0);
		}
	}
	
	class StopInstruction extends Instruction {
	
		@Override
		public void runInstruction() {
			return null
		}
		
	}

	Instruction createInstruction(long opCodeIndex) {
		int opCode = getFromMemory(opCodeIndex) % 100
		List parameterModes = readParameterModes(getFromMemory(opCodeIndex) / 100 as Integer);
		
		switch(opCode) {
			case 1:	return new AdditionInstruction(opCodeIndex, parameterModes)
			case 2:	return new MultiplyInstruction(opCodeIndex, parameterModes)
			case 3:	return new InputInstruction(opCodeIndex, parameterModes)
			case 4:	return new OutputInstruction(opCodeIndex, parameterModes)
			case 5:	return new JumpIfTrueInstruction(opCodeIndex, parameterModes)
			case 6:	return new JumpIfFalseInstruction(opCodeIndex, parameterModes)
			case 7:	return new LessThanInstruction(opCodeIndex, parameterModes)
			case 8:	return new EqualsInstruction(opCodeIndex, parameterModes)
			case 9:	return new AdjustRelativeBaseInstruction(opCodeIndex, parameterModes)
			case 99: return new StopInstruction()
			default: println "error, unexpected opcode: $opCode"
		}
	}
	
	private List readParameterModes(int fullParameterModes) {
		List parameterModes = []
		3.times {
			parameterModes << fullParameterModes % 10
			fullParameterModes /= 10
		}
		return parameterModes
	}
	
	private long getFromMemory(long index) {
		return memory.containsKey(index) ? memory.get(index) : 0
	}
	
	private long[] getRangeFromMemory(long startIndex, long endIndex) {
		long[] range = new long[endIndex - startIndex + 1];
		for(long index = startIndex; index <= endIndex; index++) {
			range[index - startIndex] = memory.containsKey(index) ? memory.get(index) : 0
		}
		return range
	}
}





