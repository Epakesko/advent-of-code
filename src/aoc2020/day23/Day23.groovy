package aoc2020.day23

import aoc2020.Day
import aoc2020.common.Util
import groovy.time.TimeCategory

class Day23 extends Day {


	
	@Override
	public Object calculateResult(Object fileName) {
		String inputNum = Util.readFile(fileName)[0]
		List input = inputNum.split("").collect { it as Integer }
		
		println input
		
		int currentCupIdx = 0
		100.times { idx ->
			int currentCup = input[currentCupIdx]
			int cup1 = input[(currentCupIdx + 1) % 9]
			int cup2 = input[(currentCupIdx + 2) % 9]
			int cup3 = input[(currentCupIdx + 3) % 9]
			input.removeElement(cup1)
			input.removeElement(cup2)
			input.removeElement(cup3)
			
			println "removed: " + cup1 + " " + cup2 + " " + cup3 + " newInput: $input"
			
			int destinationCup = currentCup
			int destinationCupIdx = -1
			while(destinationCupIdx == -1) {
				destinationCup--
				if(destinationCup < 0) destinationCup += 10
				destinationCupIdx = input.findIndexOf { it == destinationCup }
			}
			println "destinationCup: $destinationCup, idx: $destinationCupIdx"
			input.add(destinationCupIdx + 1, cup3)
			input.add(destinationCupIdx + 1, cup2)
			input.add(destinationCupIdx + 1, cup1)
			
			currentCupIdx = (input.findIndexOf{ it == currentCup} + 1) % 9
			println "currentCupIdx: $currentCupIdx, cup: ${input[currentCupIdx]}"
			
			
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
			if(idx != 8) n.nextNodeLabel = inputNum.split("")[idx+1] as Integer
			else n.nextNodeLabel = 10
			input[num as Integer] = n
		}
		
		for(int i = 10; i < 1000001; i++) {
			Node n = new Node()
			n.label = i
			if(i == 1000000) n.nextNodeLabel = (inputNum.split("")[0] as Integer)
			else n.nextNodeLabel = i+1
			input[i] = n
		}
		
		int currentCupLabel = (inputNum.split("")[0] as Integer)
		Node currentCupNode = input[currentCupLabel]
		
		10000000.times { idx ->
			//Date start = new Date()
			if(idx % 10000 == 0) println idx
			Node cup1 = input[currentCupNode.nextNodeLabel]
			Node cup2 = input[cup1.nextNodeLabel]
			Node cup3 = input[cup2.nextNodeLabel]
			Node cup4 = input[cup3.nextNodeLabel]
			//Date stop0 = new Date()
			currentCupNode.nextNodeLabel = cup4.label
			//Date stop1 = new Date()
			
			//println "removed: " + cup1.label + " " + cup2.label + " " + cup3.label + ""
			
			int destinationCupLabel = currentCupLabel
			Node destinationCup = null
			while(destinationCup == null || destinationCup.label == cup1.label || destinationCup.label == cup2.label || destinationCup.label == cup3.label) {
				destinationCupLabel--
				if(destinationCupLabel == 0) destinationCupLabel = 10000000
				destinationCup = input[destinationCupLabel]
			}
			//Date stop2 = new Date()
			//println "destinationCup: $destinationCupLabel"
			int afterDestinationCupLabel = destinationCup.nextNodeLabel
			destinationCup.nextNodeLabel = cup1.label
			cup3.nextNodeLabel = afterDestinationCupLabel
			//Date stop3 = new Date()
			
			currentCupLabel = currentCupNode.nextNodeLabel
			currentCupNode = input[currentCupLabel]
			//Date stop4 = new Date()
			//println "currentCup: $currentCupLabel"
			
			//println "0: ${TimeCategory.minus(stop0, start )}, 1: ${TimeCategory.minus(stop1, stop0 )}, 2: ${TimeCategory.minus(stop2, stop1 )}, 3: ${TimeCategory.minus(stop3, stop2 )}, 4: ${TimeCategory.minus(stop4, stop3)}"
		}
		//println input
		Node oneNode = input[1]
		Node nextNode = input[oneNode.nextNodeLabel]
		Node nextNextNode = input[nextNode.nextNodeLabel]
		
		
		"" + oneNode + " " + nextNode + " " + nextNextNode
	}
	
	class Node {
		int nextNodeLabel
		int label
		
		String toString() {
			"$label -> next -> $nextNodeLabel"
		}
	}
}
