package com.epakesko.advent.of.code.aoc_2019.util

import com.epakesko.advent.of.code.common.Util

class IntCode {
	int pointer = 0
	int[] memory
	List output = []
	List input
	int inputIdx = 0
	
	public IntCode(fileName) {
		this(fileName, 1)
	}
	
	public IntCode(fileName, input) {
		this(fileName, [input])
	}
	
	public IntCode(fileName, List input) {
		memory = Util.readFile(fileName)[0].split(",").collect{ it as Integer }.toArray()
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
	
	def addInput(int input) {
		this.input << input
	}
	
	abstract class Instruction {
		int[] parameters
		List parameterModes
		
		abstract void runInstruction();
		
		int getParameterByIndex(int index) {
			int mode = parameterModes[index]
			int parameter = parameters[index]
			
			switch(mode) {
				case 0: return IntCode.this.memory[parameter]
				case 1:	return parameter
				default: return -1
			}
		}
	}
	
	class AdditionInstruction extends Instruction {
		
		public AdditionInstruction(Integer opCodeIndex, List parameterModes) {
			parameters = IntCode.this.memory[(opCodeIndex+1)..(opCodeIndex+3)]
			this.parameterModes = parameterModes
		}
		
		@Override
		public void runInstruction(){
			IntCode.this.memory[parameters[2]] = getParameterByIndex(0) + getParameterByIndex(1)
		}
	}
	
	class MultiplyInstruction extends Instruction {
		
		public MultiplyInstruction(Integer opCodeIndex, List parameterModes) {
			parameters = IntCode.this.memory[(opCodeIndex+1)..(opCodeIndex+3)]
			this.parameterModes = parameterModes
		}
		
		@Override
		public void runInstruction(){
			IntCode.this.memory[parameters[2]] = getParameterByIndex(0) * getParameterByIndex(1)
		}
	}
	
	class InputInstruction extends Instruction {
	
		public InputInstruction(Integer opCodeIndex, List parameterModes) {
			parameters = IntCode.this.memory[(opCodeIndex+1)]
			this.parameterModes = parameterModes
		}
		
		@Override
		public void runInstruction() {
			IntCode.this.memory[parameters[0]] = IntCode.this.input[IntCode.this.inputIdx]
			IntCode.this.inputIdx++
		}
		
	}
	
	class OutputInstruction extends Instruction {
	
		public OutputInstruction(Integer opCodeIndex, List parameterModes) {
			parameters = IntCode.this.memory[(opCodeIndex+1)]
			this.parameterModes = parameterModes
		}
		
		@Override
		public void runInstruction() {
			output << getParameterByIndex(0)
		}
	}
	
	class JumpIfTrueInstruction extends Instruction {
	
		public JumpIfTrueInstruction(Integer opCodeIndex, List parameterModes) {
			parameters = IntCode.this.memory[(opCodeIndex+1), (opCodeIndex+2)]
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
	
		public JumpIfFalseInstruction(Integer opCodeIndex, List parameterModes) {
			parameters = IntCode.this.memory[(opCodeIndex+1), (opCodeIndex+2)]
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
	
		public LessThanInstruction(Integer opCodeIndex, List parameterModes) {
			parameters = IntCode.this.memory[(opCodeIndex+1)..(opCodeIndex+3)]
			this.parameterModes = parameterModes
		}
		
		@Override
		public void runInstruction() {
			if(getParameterByIndex(0) < getParameterByIndex(1)) {
				IntCode.this.memory[parameters[2]] = 1
			}
			else {
				IntCode.this.memory[parameters[2]] = 0
			}
		}
	}
	
	class EqualsInstruction extends Instruction {
	
		public EqualsInstruction(Integer opCodeIndex, List parameterModes) {
			parameters = IntCode.this.memory[(opCodeIndex+1)..(opCodeIndex+3)]
			this.parameterModes = parameterModes
		}
		
		@Override
		public void runInstruction() {
			if(getParameterByIndex(0) == getParameterByIndex(1)) {
				IntCode.this.memory[parameters[2]] = 1
			}
			else {
				IntCode.this.memory[parameters[2]] = 0
			}
		}
	}
	
	class StopInstruction extends Instruction {
	
		@Override
		public void runInstruction() {
			return null
		}
		
	}

	Instruction createInstruction(int opCodeIndex) {
		int opCode = memory[opCodeIndex] % 100
		List parameterModes = readParameterModes(memory[opCodeIndex] / 100 as Integer);
		
		switch(opCode) {
			case 1:	return new AdditionInstruction(opCodeIndex, parameterModes)
			case 2:	return new MultiplyInstruction(opCodeIndex, parameterModes)
			case 3:	return new InputInstruction(opCodeIndex, parameterModes)
			case 4:	return new OutputInstruction(opCodeIndex, parameterModes)
			case 5:	return new JumpIfTrueInstruction(opCodeIndex, parameterModes)
			case 6:	return new JumpIfFalseInstruction(opCodeIndex, parameterModes)
			case 7:	return new LessThanInstruction(opCodeIndex, parameterModes)
			case 8:	return new EqualsInstruction(opCodeIndex, parameterModes)
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
}





