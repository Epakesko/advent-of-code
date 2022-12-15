package com.epakesko.advent.of.code.aoc_2022.day12;

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day
import groovy.json.JsonSlurper

public class Day12 extends Day{
	String start
	String destination
	Map<String, Map> squares = new HashMap<>()
	Map<String, Map> visited = new HashMap<>()
	List<String> candidates = new ArrayList<>()
	
	private getDestinationDistance(String start) {
		candidates.add(start)
		visited.put(start, 0)
		while(!candidates.empty) {
			String candidateSquare = candidates.pop()
			int candidateSquareDist = visited.get(candidateSquare)
			if(candidateSquare.equals(destination)) return candidateSquareDist
			List neighbors = squares.get(candidateSquare).n
			neighbors.each { n ->
				if(!visited.containsKey(n)) {
					candidates.add(n)
					visited.put(n, candidateSquareDist + 1)
				}
			}
		}
		Integer.MAX_VALUE
	}
	
	private void addSquare(String square, int i, int j) {
		String name = "$i-$j"
		int height = square.charAt(0)
		if(height == 83) {
			start = name
			height = 96
		}
		else if(height == 69) {
			destination = name
			height = 123
		}
		squares.put(name, ["h": height, "n": []])
		if(i > 0) {
			String leftNeighborName = "${i-1}-$j"
			int leftNeighborHeight = squares.get(leftNeighborName).h
			if(leftNeighborHeight >= height - 1) squares.get(leftNeighborName).n << name
			if(height >= leftNeighborHeight - 1) squares.get(name).n << leftNeighborName
		}
		if(j > 0) {
			String upNeighborName = "$i-${j-1}"
			int upNeighborHeight = squares.get(upNeighborName).h
			if(upNeighborHeight >= height - 1) squares.get(upNeighborName).n << name
			if(height >= upNeighborHeight - 1) squares.get(name).n << upNeighborName
		}
	}
	
	@Override
	def calculateResult(fileName){
		List<String> lines = Util.readFile(fileName)
		
		lines.eachWithIndex { line, j ->
			line.eachWithIndex { square, i ->
				addSquare(square, i, j)
			}
		}
	
		getDestinationDistance(start)
	}
	
	@Override
	def calculateResult2(fileName){
		List<String> lines = Util.readFile(fileName)
		squares = new HashMap<>()
		visited = new HashMap<>()
		candidates = new ArrayList<>()
		
		lines.eachWithIndex { line, j ->
			line.eachWithIndex { square, i ->
				addSquare(square, i, j)
			}
		}
	
		squares.findAll { k, v -> v.h == 97 }.collect{ k, v -> 
			visited = new HashMap<>()
			candidates = new ArrayList<>()
			getDestinationDistance(k)
		}.min()
	}
}
