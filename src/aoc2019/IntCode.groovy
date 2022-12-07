package aoc2019

import aoc2020.common.Util

class IntCode {
	int pointer = 0
	int[] memory
	
	public IntCode(fileName) {
		memory = Util.readFile(fileName)[0].split(",").collect{ it as Integer }.toArray()
	}
	
	void run() {
		Instruction instruction = createInstruction(pointer)
		
		while(!(instruction instanceof  StopInstruction)) {
			instruction.runInstruction();
			pointer += instruction.parameters.size() + 1
			instruction = createInstruction(pointer)
		}
	}
	
	abstract class Instruction {
		int[] parameters
		
		abstract void runInstruction();
		
	}
	
	class AdditionInstruction extends Instruction {
		
		public AdditionInstruction(Integer opCodeIndex) {
			parameters = IntCode.this.memory[(opCodeIndex+1)..(opCodeIndex+3)]
		}
		
		@Override
		public void runInstruction(){
			IntCode.this.memory[parameters[2]] = IntCode.this.memory[parameters[0]] + IntCode.this.memory[parameters[1]]
		}
	}
	
	class MultiplyInstruction extends Instruction {
		
		public MultiplyInstruction(Integer opCodeIndex) {
			parameters = IntCode.this.memory[(opCodeIndex+1)..(opCodeIndex+3)]
		}
		
		@Override
		public void runInstruction(){
			IntCode.this.memory[parameters[2]] = IntCode.this.memory[parameters[0]] * IntCode.this.memory[parameters[1]]
		}
	}
	
	class StopInstruction extends Instruction {
	
		@Override
		public void runInstruction() {
			return null
		}
		
	}

	Instruction createInstruction(int opCodeIndex) {
		if(memory[opCodeIndex] == 1) {
			return new AdditionInstruction(opCodeIndex)
		}
		else if(memory[opCodeIndex] == 2) {
			return new MultiplyInstruction(opCodeIndex)
		}
		else if(memory[opCodeIndex] == 99) {
			return new StopInstruction()
		}
		else {
			println "error, unexpected opcode: ${memory[opCodeIndex]}"
		}
	}
}





