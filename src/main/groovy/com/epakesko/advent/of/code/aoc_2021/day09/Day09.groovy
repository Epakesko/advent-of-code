package com.epakesko.advent.of.code.aoc_2021.day09;

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day
import java.awt.Point

public class Day09 extends Day{	
	@Override
	def calculateResult(fileName){
		List<String> lines = Util.readFile(fileName)
		int sum
		for(int i = 0; i < lines.size(); i++) {
			for(int j = 0; j < lines[i].size(); j++) {
				int thisNum = (lines[i][j] as Integer)
				int leftNum = (j == 0 ? 10 : lines[i][j-1] as Integer)
				int upNum = (i == 0 ? 10 : lines[i-1][j] as Integer)
				int downNum = (i == (lines.size() - 1) ? 10 : lines[i+1][j] as Integer)
				int rightNum = (j == (lines[i].size() - 1) ? 10 : lines[i][j+1] as Integer)
				if(thisNum < leftNum && thisNum < upNum && thisNum < rightNum && thisNum < downNum) {
					sum += thisNum + 1
				}
			}
		}
		sum
	}

	@Override
	def calculateResult2(fileName){
		List<String> lines = Util.readFile(fileName)
		List<List<Integer>> nums = new ArrayList<>()
		lines.each { line -> nums << line.split("").collect{it as Integer}}
		List<Point> lowPoints = new ArrayList<>()
		
		for(int i = 0; i < lines.size(); i++) {
			for(int j = 0; j < lines[i].size(); j++) {
				int thisNum = (lines[i][j] as Integer)
				int leftNum = (j == 0 ? 10 : lines[i][j-1] as Integer)
				int upNum = (i == 0 ? 10 : lines[i-1][j] as Integer)
				int downNum = (i == (lines.size() - 1) ? 10 : lines[i+1][j] as Integer)
				int rightNum = (j == (lines[i].size() - 1) ? 10 : lines[i][j+1] as Integer)
				if(thisNum < leftNum && thisNum < upNum && thisNum < rightNum && thisNum < downNum) {
					lowPoints << new Point(i, j)
				}
			}
		}
		
		List basinSizes = []
		lowPoints.each {
			basinSizes << sizeOfBasinByBFS(nums, it)
		}
		def sorted = basinSizes.sort()
		sorted[-1] * sorted[-2] * sorted[-3]
	}
	
	private int sizeOfBasinByBFS(List<List<Integer>> map, Point startingPoint) {
		List<Point> visitedPoints = new ArrayList<>()
		List<Point> lastVisited = new ArrayList<>()
		visitedPoints.add(startingPoint)
		lastVisited.add(startingPoint)
		
		while(!lastVisited.empty) {
			List<Point> newLastVisited = new ArrayList<>()
			for(Point point : lastVisited) {
				int i = point.x
				int j = point.y
				Point leftPos = (j == 0 ? null : new Point(i, j-1))
				Point upPos = (i == 0 ? null : new Point(i-1, j))
				Point downPos = (i == (map.size() - 1) ? null : new Point(i+1, j))
				Point rightPos = (j == (map[i].size() - 1) ? null : new Point(i, j+1))
				
				if(leftPos != null && !visitedPoints.contains(leftPos) && map[leftPos.x][leftPos.y] != 9) {
					newLastVisited << leftPos
					visitedPoints << leftPos
				}
				if(upPos != null && !visitedPoints.contains(upPos) && map[upPos.x][upPos.y] != 9) {
					newLastVisited << upPos
					visitedPoints << upPos
				}
				if(downPos != null && !visitedPoints.contains(downPos) && map[downPos.x][downPos.y] != 9) {
					newLastVisited << downPos
					visitedPoints << downPos
				}
				if(rightPos != null && !visitedPoints.contains(rightPos) && map[rightPos.x][rightPos.y] != 9) {
					newLastVisited << rightPos
					visitedPoints << rightPos
				}
			}
			lastVisited = newLastVisited
		}
		visitedPoints.size()
	}
}
