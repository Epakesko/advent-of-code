package aoc2020.day09

import aoc2020.Day
import aoc2020.common.Util

class Day09 extends Day {
	final static int PREAMBLE_NUM = 25
	final static int FIRST_PART_ANSWER = 29221323
	
	@Override
	public Object calculateResult(Object fileName) {
		List lines = Util.readFileAsLongs(fileName)
		
		List preamble = []
		for(int i = 0; i < PREAMBLE_NUM; i++) {
			preamble << lines[i]
		}
		
		for(int i = PREAMBLE_NUM; i < lines.size(); i++) {
			long inspectedNum = lines[i]
			boolean weak = true
			for(int j = 0; j < PREAMBLE_NUM; j++) {
				long num = preamble[j]
				long diff = inspectedNum - num
				if(diff != num && preamble.contains(diff)) {
					weak = false
					break
				}
			}
			if(weak) return inspectedNum
			else {
				preamble = preamble.minus(preamble[0])
				preamble << inspectedNum
			} 
		}
	}

	@Override
	public Object calculateResult2(Object fileName) {
		List nums = Util.readFileAsLongs(fileName)
		int lo = 0
		int hi = 1
		
		while(true) {
			long sumFromTo = nums.subList(lo, hi).sum()
			if(sumFromTo < FIRST_PART_ANSWER) hi++
			else if(sumFromTo > FIRST_PART_ANSWER) lo++
			else return nums.subList(lo, hi).min() + nums.subList(lo, hi).max()
		}
	}
	
}
