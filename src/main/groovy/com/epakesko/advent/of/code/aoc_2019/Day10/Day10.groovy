package com.epakesko.advent.of.code.aoc_2019.Day10

import com.epakesko.advent.of.code.aoc_2019.util.IntCode
import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

class Day10 extends Day {
	
	private double calcAngleDegrees(int x, int y) {
	  return Math.atan2(-x, -y) * 180 / Math.PI;
	}
	
	@Override
	public Object calculateResult(Object fileName) {
		List rows = Util.readFile(fileName)
		Map<Integer, Map<Integer, Integer>> numberOfAsteroidsSeen = new HashMap<>();
		rows.eachWithIndex { String row, int y ->
			numberOfAsteroidsSeen.put(y, new HashMap<>())
			row.eachWithIndex { String c, int x ->
				if("#".equals(c)) {
					int seenSoFar = 0
					numberOfAsteroidsSeen.each { int yOfSeen, Map<Integer, Integer> asteroidsOnY ->
						asteroidsOnY.each { int xOfSeen, Integer numberOfOthersSeen ->
							int xDiff = x - xOfSeen
							int yDiff = y - yOfSeen
							int gcd = xDiff < 0 ? Util.getGcd(-xDiff, yDiff) : Util.getGcd(xDiff, yDiff)
							if(gcd > 1) {
								xDiff /= gcd
								yDiff /= gcd
							}
							boolean seen = true
							
							int j = yOfSeen + yDiff
							int i = xOfSeen + xDiff
							while((xDiff > 0 && i <= x) || (xDiff <= 0 && i >= x) && j <= y) {
								if(numberOfAsteroidsSeen.get(j).containsKey(i)) seen = false
								
								i+=xDiff
								j+=yDiff
							}
							
							if(seen) {
								numberOfAsteroidsSeen.get(yOfSeen).put(xOfSeen, numberOfOthersSeen+1)
								seenSoFar++
							}
						}
					}
					numberOfAsteroidsSeen.get(y).put(x, seenSoFar)
				}
			}
		}
		int max = 0
		int bestX = 0
		int bestY = 1
		numberOfAsteroidsSeen.each { Integer y, Map<Integer, Integer> asteroidsOnY -> 
			asteroidsOnY.each { Integer x, Integer seen ->
				if(seen > max) {
					max = seen
					bestX = x
					bestY = y
				}
			}
		}
		"$bestY, $bestX -> $max"
	}
	
	@Override
	public Object calculateResult2(Object fileName) {
		List rows = Util.readFile(fileName)
		Map<Integer, Integer> resultsByDegree = new HashMap<>();
		Map<Integer, List<Integer>> asteroids = new HashMap<>();
		rows.eachWithIndex { String row, int y ->
			asteroids.put(y, new ArrayList<>())
			row.eachWithIndex { String c, int x ->
				if("#".equals(c)) {
					asteroids.each { int yOfSeen, List<Integer> asteroidsOnY ->
						asteroidsOnY.each { int xOfSeen ->
							int xDiff = x - xOfSeen
							int yDiff = y - yOfSeen
							int gcd = xDiff < 0 ? Util.getGcd(-xDiff, yDiff) : Util.getGcd(xDiff, yDiff)
							if(gcd > 1) {
								xDiff /= gcd
								yDiff /= gcd
							}
							boolean seen = true
							
							int j = yOfSeen + yDiff
							int i = xOfSeen + xDiff
							while((xDiff > 0 && i <= x) || (xDiff <= 0 && i >= x) && j <= y) {
								if(asteroids.get(j).contains(i)) seen = false
								
								i+=xDiff
								j+=yDiff
							}
							
							if(seen) {
								if(x == 17 && y == 22) {
									resultsByDegree.put(calcAngleDegrees(xDiff, yDiff), xOfSeen * 100 + yOfSeen)
								}
								if(xOfSeen == 17 && yOfSeen == 22) {
									resultsByDegree.put(calcAngleDegrees(-xDiff, -yDiff), x * 100 + y)
								}
							}
						}
					}
					asteroids.get(y) << x
				}
			}
		}
		double degreeOf200th = resultsByDegree.keySet().sort().reverse()[199]
		"$degreeOf200th -> ${resultsByDegree.get(degreeOf200th)}"
	}
}
