package com.epakesko.advent.of.code.aoc_2022.day08;

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

public class Day08 extends Day{
		
	@Override
	def calculateResult(fileName){
		List<String> lines = Util.readFile(fileName)
		Set<String> visible = new HashSet<>()
		int cols = lines[0].size()
		int rows = lines.size()
		lines.eachWithIndex { line, y ->
			int rowMax = -1
			line.eachWithIndex { c, x ->
				int cInt = c as Integer
				if(cInt > rowMax) {
					rowMax = cInt
					visible.add("$x-$y")
				}
			}
			rowMax = -1
			line.reverse().eachWithIndex { c, xRev ->
				int cInt = c as Integer
				int x = cols - 1 - xRev
				if(cInt > rowMax) {
					rowMax = cInt
					visible.add("$x-$y")
				}
			}
		}
		lines[0].size().times { x ->
			int colMax = -1
			lines.eachWithIndex { line, y ->
				int cInt = line[x] as Integer
				if(cInt > colMax) {
					colMax = cInt
					visible.add("$x-$y")
				}
			}
			colMax = -1
			lines.reverse().eachWithIndex { line, yRev ->
				int cInt = line[x] as Integer
				int y = rows - 1 - yRev
				if(cInt > colMax) {
					colMax = cInt
					visible.add("$x-$y")
				}
			}
		}
		visible.size()
	}
	
	@Override
	def calculateResult2(fileName){
		List<String> lines = Util.readFile(fileName)
		int cols = lines[0].size()
		int rows = lines.size()
		int maxScore = 0
		for(int i = 1; i < rows - 1; i++) {
			for(int j = 1; j < cols - 1; j++) {
				int h = lines[i][j] as Integer
				int score = 1
				int oneDirScore = 0
				for (int k = i - 1; k >= 0; k--) {
					int n = lines[k][j] as Integer
					oneDirScore++;
					if(n >= h) break;
				}
				score *= oneDirScore
				oneDirScore = 0
				for (int k = i + 1; k < rows; k++) {
					int n = lines[k][j] as Integer
					oneDirScore++;
					if(n >= h) break;
				}
				score *= oneDirScore
				oneDirScore = 0
				for (int k = j - 1; k >= 0; k--) {
					int n = lines[i][k] as Integer
					oneDirScore++;
					if(n >= h) break;
				}
				score *= oneDirScore
				oneDirScore = 0
				for (int k = j + 1; k < cols; k++) {
					int n = lines[i][k] as Integer
					oneDirScore++;
					if(n >= h) break;
				}
				score *= oneDirScore
				if(score > maxScore) maxScore = score
			}
		}
		maxScore
	}
}
