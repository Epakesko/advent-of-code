package com.epakesko.advent.of.code.aoc_2021.day11;

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day
import java.awt.Point

public class Day11 extends Day{	
	@Override
	def calculateResult(fileName){
		List<String> lines = Util.readFile(fileName)
		Map<Point, Integer> octopusEnergy = new HashMap<>()
		
		for(int i = 0; i < lines.size(); i++) {
			for(int j = 0; j < lines[i].size(); j++) {
				int energy = lines[i][j] as Integer
				octopusEnergy.put(new Point(i, j), energy)
			}
		}
		int flashes = 0
		
		100.times { 
			octopusEnergy = octopusEnergy.collectEntries{key, value -> [key, value+1]}
			Set<Point> alreadyFlashed = []
			Set<Point> latestFlashed = []
			List flashed = octopusEnergy.findAll { k, v -> v == 10 }*.key.collect()
			while(!flashed.empty) {
				flashes += flashed.size()
				alreadyFlashed.addAll(flashed)
				flashed.each { octopus ->
					getNeighbors(octopus, octopusEnergy).each { neighbor ->
						octopusEnergy.put(neighbor, octopusEnergy.get(neighbor) + 1)
					}
				}
				flashed = octopusEnergy.findAll { k, v -> !alreadyFlashed.contains(k) && v > 9 }*.key
			}
			octopusEnergy = octopusEnergy.collectEntries{key, value -> value > 9 ? [key, 0] : [key, value]}
		}
		flashes
	}
	
	Set<Point> getNeighbors(Point octopus, Map<Point, Integer> map) {
		Set neighbors = []
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				if(i != 0 || j != 0) {
					Point n = new Point(((octopus.x) as Integer) + i, ((octopus.y) as Integer) + j)
					if(map.containsKey(n)) neighbors << n
				}
			}
		}
		neighbors
	}

	@Override
	def calculateResult2(fileName){
		List<String> lines = Util.readFile(fileName)
		Map<Point, Integer> octopusEnergy = new HashMap<>()
		
		for(int i = 0; i < lines.size(); i++) {
			for(int j = 0; j < lines[i].size(); j++) {
				int energy = lines[i][j] as Integer
				octopusEnergy.put(new Point(i, j), energy)
			}
		}
		int steps = 0
		
		while(true) { 
			steps++
			octopusEnergy = octopusEnergy.collectEntries{key, value -> [key, value+1]}
			Set<Point> alreadyFlashed = []
			Set<Point> latestFlashed = []
			List flashed = octopusEnergy.findAll { k, v -> v == 10 }*.key.collect()
			while(!flashed.empty) {
				alreadyFlashed.addAll(flashed)
				flashed.each { octopus ->
					getNeighbors(octopus, octopusEnergy).each { neighbor ->
						octopusEnergy.put(neighbor, octopusEnergy.get(neighbor) + 1)
					}
				}
				flashed = octopusEnergy.findAll { k, v -> !alreadyFlashed.contains(k) && v > 9 }*.key
			}
			octopusEnergy = octopusEnergy.collectEntries{key, value -> value > 9 ? [key, 0] : [key, value]}
			if(octopusEnergy.values().findAll{it == 0}.size() == 100) return steps
		}
	}
}
