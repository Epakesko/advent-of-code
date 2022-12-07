package aoc2020.day23

import aoc2020.Day
import aoc2020.common.Util
import groovy.time.TimeCategory

class Day23 extends Day {

	static final int FIRST_PART_ROUNDS = 100
	static final int FIRST_PART_CUP_NUMBER = 9
	static final int SECOND_PART_ROUNDS = 10000000
	static final int SECOND_PART_CUP_NUMBER = 1000000

	
	@Override
	public Object calculateResult(Object fileName) {
		String inputNum = Util.readFile(fileName)[0]
		List input = inputNum.split("").collect { it as Integer }
		
		int currentCupIdx = 0
		FIRST_PART_ROUNDS.times { idx ->
			int currentCup = input[currentCupIdx]
			int cup1 = input[(currentCupIdx + 1) % FIRST_PART_CUP_NUMBER]
			int cup2 = input[(currentCupIdx + 2) % FIRST_PART_CUP_NUMBER]
			int cup3 = input[(currentCupIdx + 3) % FIRST_PART_CUP_NUMBER]
			input.removeElement(cup1)
			input.removeElement(cup2)
			input.removeElement(cup3)
			
			int destinationCup = currentCup
			int destinationCupIdx = -1
			while(destinationCupIdx == -1) {
				destinationCup--
				if(destinationCup < 0) destinationCup += FIRST_PART_CUP_NUMBER + 1
				destinationCupIdx = input.findIndexOf { it == destinationCup }
			}
			input.add(destinationCupIdx + 1, cup3)
			input.add(destinationCupIdx + 1, cup2)
			input.add(destinationCupIdx + 1, cup1)
			
			currentCupIdx = (input.findIndexOf{ it == currentCup} + 1) % FIRST_PART_CUP_NUMBER
		}
		int indexOfOne = input.findIndexOf { it == 1 }
		(input.subList(indexOfOne + 1, input.size()) + input.subList(0, indexOfOne)).join("")
	}

	@Override
	public Object calculateResult2(Object fileName) {
		String inputNum = Util.readFile(fileName)[0]
		Map input = [:]
		inputNum.split("").eachWithIndex { num, idx ->
			Node n = new Node()
			n.label = num as Integer
			if(idx != FIRST_PART_CUP_NUMBER - 1) n.nextNodeLabel = inputNum.split("")[idx+1] as Integer
			else n.nextNodeLabel = FIRST_PART_CUP_NUMBER + 1
			input[num as Integer] = n
		}
		
		for(int i = 10; i <= SECOND_PART_CUP_NUMBER; i++) {
			Node n = new Node()
			n.label = i
			if(i == SECOND_PART_CUP_NUMBER) n.nextNodeLabel = (inputNum.split("")[0] as Integer)
			else n.nextNodeLabel = i+1
			input[i] = n
		}
		
		int currentCupLabel = (inputNum.split("")[0] as Integer)
		Node currentCupNode = input[currentCupLabel]
		
		SECOND_PART_ROUNDS.times { idx ->
			Node cup1 = input[currentCupNode.nextNodeLabel]
			Node cup2 = input[cup1.nextNodeLabel]
			Node cup3 = input[cup2.nextNodeLabel]
			Node cup4 = input[cup3.nextNodeLabel]
			currentCupNode.nextNodeLabel = cup4.label
			
			int destinationCupLabel = currentCupLabel
			Node destinationCup = null
			while(destinationCup == null || destinationCup.label == cup1.label || destinationCup.label == cup2.label || destinationCup.label == cup3.label) {
				destinationCupLabel--
				if(destinationCupLabel == 0) destinationCupLabel = SECOND_PART_CUP_NUMBER
				destinationCup = input[destinationCupLabel]
			}
			
			int afterDestinationCupLabel = destinationCup.nextNodeLabel
			destinationCup.nextNodeLabel = cup1.label
			cup3.nextNodeLabel = afterDestinationCupLabel
			
			currentCupLabel = currentCupNode.nextNodeLabel
			currentCupNode = input[currentCupLabel]
			
		}
		Node oneNode = input[1]
		Node nextNode = input[oneNode.nextNodeLabel]
		Node nextNextNode = input[nextNode.nextNodeLabel]
		
		(nextNode.label as Long) * (nextNextNode.label as Long)
	}
	
	class Node {
		int nextNodeLabel
		int label
	}
}
