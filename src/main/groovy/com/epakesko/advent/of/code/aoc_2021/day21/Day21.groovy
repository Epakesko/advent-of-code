package com.epakesko.advent.of.code.aoc_2021.day21;

import com.epakesko.advent.of.code.aoc_2019.Day18.Day18.Node
import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

import static org.junit.jupiter.api.Assertions.assertLinesMatch

import java.awt.Point

public class Day21 extends Day{	
	@Override
	def calculateResult(fileName){
		List<String> lines = Util.readFile(fileName)
		int pos1 = lines[0] as Integer
		int pos2 = lines[1] as Integer
		
		int p1 = 0
		int p2 = 0
		
		int dice = 1
		boolean firstPlayer = true
		while(p1 < 1000 && p2 < 1000) {
			int result = (dice++ % 100) + (dice++ % 100) + (dice++ % 100)
			if(firstPlayer) {
				pos1 += result
				pos1 %= 10
				p1 += (pos1 == 0 ? 10 : pos1)
			}
			else {
				pos2 += result
				pos2 %= 10
				p2 += (pos2 == 0 ? 10 : pos2)
			}
			firstPlayer = !firstPlayer
		}
		
		Math.min(p1, p2) * (dice-1)
	}

	@Override
	def calculateResult2(fileName){
		List<String> lines = Util.readFile(fileName)
		int pos1 = lines[0] as Integer
		int pos2 = lines[1] as Integer
		
		Map<GameStatus, Long> occurencesByStatus = new HashMap<>()
		
		occurencesByStatus.put(new GameStatus(pos1, pos2, 0, 0, 0), 1)
		
		long p1Winner = 0
		long p2Winner = 0
		while(!occurencesByStatus.isEmpty()) {
			Map<GameStatus, Long> newOccurencesByStatus = new HashMap<>()
			occurencesByStatus.each { GameStatus gs, Long occurences ->
				if(gs.score1 >= 21) {
					p1Winner += occurences
					return
				}
				if(gs.score2 >= 21) {
					p2Winner += occurences
					return
				}
				
				if(gs.nextplayer) {
					getPossibleNextPositions(gs.pos2).each { k, v ->
						GameStatus newGs = new GameStatus(gs.pos1, k, gs.score1, gs.score2 + (k == 0 ? 10 : k), 0)
						newOccurencesByStatus.put(newGs, occurences * v + (newOccurencesByStatus.get(newGs)?:0))
					}
				}
				else {
					getPossibleNextPositions(gs.pos1).each { k, v ->
						GameStatus newGs = new GameStatus(k, gs.pos2, gs.score1 + (k == 0 ? 10 : k), gs.score2, 1)
						newOccurencesByStatus.put(newGs, occurences * v + (newOccurencesByStatus.get(newGs)?:0))
					}
				}
			}
			occurencesByStatus = newOccurencesByStatus
		}
		
		Math.max(p1Winner, p2Winner)
	}
	
	Map<Integer, Integer> getPossibleNextPositions(int pos) {
		Map possibilites = [:]
		
		possibilites.putAt((pos + 3) % 10, 1)
		possibilites.putAt((pos + 4) % 10, 3)
		possibilites.putAt((pos + 5) % 10, 6)
		possibilites.putAt((pos + 6) % 10, 7)
		possibilites.putAt((pos + 7) % 10, 6)
		possibilites.putAt((pos + 8) % 10, 3)
		possibilites.putAt((pos + 9) % 10, 1)
		
		possibilites
	}
	
	class GameStatus {
		int pos1
		int pos2
		int score1
		int score2
		int nextplayer
		
		GameStatus(int p1, int p2, int s1, int s2, int n) {
			this.pos1 = p1
			this.pos2 = p2
			this.score1 = s1
			this.score2 = s2
			this.nextplayer = n
		}
		
		@Override
		boolean equals(Object o) {
			if(o instanceof GameStatus) {
				GameStatus gs = (GameStatus)o
				if(gs.pos1 == this.pos1 && gs.pos2 == this.pos2 && gs.score1 == this.score1 && gs.score2 == this.score2 && gs.nextplayer == this.nextplayer) return true
			}
			false
		}
		
		@Override
		int hashCode() {
			pos1 * 1000000 + pos2 * 100000 + score1 * 10000 + score2 * 100 + nextplayer
		}
		
		@Override
		String toString() {
			"$pos1 $pos2 $score1 $score2 $nextplayer"
		}
	}
}
