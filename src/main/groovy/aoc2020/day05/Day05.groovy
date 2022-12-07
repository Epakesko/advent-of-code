package aoc2020.day05

import aoc2020.Day
import aoc2020.common.Util

class Day05 extends Day {

	@Override
	public Object calculateResult(Object fileName) {
		List lines = Util.readFile(fileName);
		List seats = []
		lines.each{ String line ->
			int low = 0;
			int high = 128;
			for(int i = 0; i < 7; i++) {
				int mid = (low + high)/2
				if(line.getAt(i) == 'B') low = mid
				else high = mid
			}
			int row = low
			low = 0
			high = 8
			for(int i = 7; i < 10; i++) {
				int mid = (low + high)/2
				if(line.getAt(i) == 'R')  low = mid
				else high = mid
			}
			int col = low
			seats << row * 8 + col
		}
		seats.max()
	}

	@Override
	public Object calculateResult2(Object fileName) {
		List lines = Util.readFile(fileName);
		List seats = []
		lines.each{ String line ->
			int low = 0;
			int high = 128;
			for(int i = 0; i < 7; i++) {
				int mid = (low + high)/2
				if(line.getAt(i) == 'B') low = mid
				else high = mid
			}
			int row = low
			low = 0
			high = 8
			for(int i = 7; i < 10; i++) {
				int mid = (low + high)/2
				if(line.getAt(i) == 'R')  low = mid
				else high = mid
			}
			int col = low
			seats << row * 8 + col
		}
		seats.sort().find {
			!seats.contains(it+1)
		} + 1
	}
}
