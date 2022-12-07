package aoc2020.day09

import aoc2020.Day
import aoc2020.common.Util

class Day09 extends Day {

	@Override
	public Object calculateResult(Object fileName) {
		List lines = Util.readFile(fileName)
		
		int preambleNum = 25
		List preamble = []
		for(int i = 0; i < preambleNum; i++) {
			preamble << (lines[i] as Integer)
		}
		
		for(int i = preambleNum; i < lines.size(); i++) {
			int inspectedNum = (lines[i] as Integer)
			boolean weak = true
			for(int j = 0; j < preambleNum; j++) {
				int num = preamble[j]
				int diff = inspectedNum - num
				if(diff != num && preamble.contains(diff)) {
					weak = false
				}
			}
			if(weak) return inspectedNum
			else {
				preamble = preamble.minus(preamble[0])
				preamble << inspectedNum
			} 
		}
		return -1
	}

	@Override
	public Object calculateResult2(Object fileName) {
		List lines = Util.readFile(fileName)
		List nums = lines.collect {
			it as Long
		}
		int lo = 0
		int hi = 1
		
		int searchingFor = 29221323
		
		while(true) {
			long sumFromTo = nums.subList(lo, hi).sum()
			if(sumFromTo < searchingFor) hi++
			else if(sumFromTo > searchingFor) lo++
			else return nums.subList(lo, hi).min() + nums.subList(lo, hi).max()
		}
	}
	
}
