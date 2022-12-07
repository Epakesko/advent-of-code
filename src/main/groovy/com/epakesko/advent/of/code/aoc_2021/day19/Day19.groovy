package com.epakesko.advent.of.code.aoc_2021.day19;

import com.epakesko.advent.of.code.aoc_2019.Day18.Day18.Node
import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

import java.awt.Point

public class Day19 extends Day{	
	@Override
	def calculateResult(fileName){
		List<String> lines = Util.readFile(fileName)
		List<Scanner> scanners = new ArrayList<>()
		
		int i = 0
		lines.each { String line ->
			if(line.matches(/--- scanner \d+ ---/)) {
				scanners.add(new Scanner(i++))
			}
			else if(!"".equals(line)) {
				scanners.last().beacons.add(new Beacon(line.split(",").collect{it as Integer}))
			}
		}
		
		Map<Scanner, List<Integer>> scannerPositions = new HashMap<>()
		Set<Beacon> beacons = new HashSet<>()
		scannerPositions.put(scanners[0], [0, 0, 0])
		beacons.addAll(scanners[0].beacons.collect{it.position})
		
		
		while(scannerPositions.size() < scanners.size()) {
			Map<Scanner, List<Integer>> newScannerPositions = new HashMap<>()
			boolean found = false
			for(Scanner originalScanner : scannerPositions.keySet()) {
				if(found) break
				for(Scanner comparisonScanner : scanners) {
					if(scannerPositions.containsKey(comparisonScanner)) continue
					if(found) break
					6.times {
						if(!found) {
							comparisonScanner.changeXPosition()
							4.times {
								if(!found) {
									comparisonScanner.rotateX()
									List<Integer> comparisonScannerPosition = has12CommonScanners(originalScanner, comparisonScanner)
									if(comparisonScannerPosition) {
										comparisonScanner.moveBeaconsToZero(-comparisonScannerPosition)
										found = true
										newScannerPositions.put(comparisonScanner, comparisonScannerPosition)
										beacons.addAll(comparisonScanner.beacons.collect{it.position})
									}
								}
							}
						}
					}
				}
			}
			scannerPositions.putAll(newScannerPositions)
		}
		
		int maxManhatten = 0
		for(int j = 0; j < scannerPositions.values().size() - 1; j++) {
			for(int k = j + 1; k < scannerPositions.values().size(); k++) {
				int distance = Math.abs(scannerPositions.values()[j][0] - scannerPositions.values()[k][0]) + 
				Math.abs(scannerPositions.values()[j][1] - scannerPositions.values()[k][1]) + 
				Math.abs(scannerPositions.values()[j][2] - scannerPositions.values()[k][2])
				if(distance > maxManhatten) maxManhatten = distance
			}
		}
		maxManhatten
	}
	
	List<Integer> has12CommonScanners(Scanner s1, Scanner s2) {
		Map<String, Integer> positionOccurence = new HashMap<>()
		for(int i = 0; i < s1.beacons.size(); i++) {
			for(int j = 0; j < s2.beacons.size(); j++) {
				List<Integer> s2PossiblePosition = move(s1.beacons[i].position, s2.beacons[j].position)
				String positionString = s2PossiblePosition.join(",")
				if(!positionOccurence.containsKey(positionString)) positionOccurence.put(positionString, 0)
				positionOccurence.put(positionString, positionOccurence.get(positionString) + 1)
				if(positionOccurence.get(positionString) == 12)	return s2PossiblePosition				
			}
		}
			
		null
	}
	
	List<Integer> rotate(List<Integer> v) {
		[v[0], -v[2], v[1]]
	}
	
	List<Integer> move(List<Integer> v, List<Integer> moveBy) {
		[v[0] - moveBy[0], v[1] - moveBy[1], v[2] - moveBy[2]]
	}
	
	List<Integer> getDifferentXPositions(List<Integer> v, int orientation) {
		switch(orientation) {
			case 1: return [-v[0], -v[1], v[2]] 
			case 2: return [v[1], -v[0], v[2]]
			case 3: return [-v[0], -v[1], v[2]]
			case 4: return [-v[2], v[0], -v[1]]
			case 5: return [-v[0], v[1], -v[2]]
			default: return v
		}
	}
	
	class Scanner {
		List<Beacon> beacons
		int id
		int orientation
		
		Scanner(id) {
			this.id = id
			beacons = new ArrayList<>()
		}
		
		@Override
		boolean equals(Object s) {
			s instanceof Scanner && s.id == this.id
		}
		
		void changeXPosition() {
			beacons.each { beacon ->
				beacon.position = getDifferentXPositions(beacon.position, orientation)
			}
			orientation = (orientation + 1) % 6
		}
		
		void rotateX() {
			beacons.each { beacon ->
				beacon.position = rotate(beacon.position)
			}
		}
		
		void moveBeaconsToZero(List<Integer> scannerPos) {
			beacons.each { beacon ->
				beacon.position = move(beacon.position, scannerPos)
			}
		}
	}
	
	class Beacon {
		List<Integer> position
		
		Beacon(List p) {
			position = p
		}
		
		@Override
		boolean equals(Object b) {
			b instanceof Beacon && b.position[0] == this.position[0] && b.position[1] == this.position[1] && b.position[2] == this.position[2]
		}
		
		@Override
		String toString() {
			position
		}
	}
	
	@Override
	def calculateResult2(fileName){
		List<String> lines = Util.readFile(fileName)
		-1
	}
}
