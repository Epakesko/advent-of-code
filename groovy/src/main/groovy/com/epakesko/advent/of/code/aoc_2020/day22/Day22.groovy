package com.epakesko.advent.of.code.aoc_2020.day22

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

class Day22 extends Day {

	@Override
	public Object calculateResult(Object fileName) {
		List lines = Util.readFile(fileName)
		
		List player1Deck = []
		List player2Deck = []
		
		int i = -1
		for(String line = lines[++i]; line.trim() != ""; line = lines[++i]) {
			if(line.startsWith("Player")) continue
			
			player1Deck << (line as Integer)
		}
		
		for(String line = lines[++i]; i < lines.size(); line = lines[++i]) {
			if(line.startsWith("Player")) continue
			
			player2Deck << (line as Integer)
		}
		
		while(player1Deck.size() && player2Deck.size()) {
			int player1Card = player1Deck[0]
			int player2Card = player2Deck[0]
			
			player1Deck.removeAt(0)
			player2Deck.removeAt(0)
			
			if(player1Card > player2Card) {
				player1Deck << player1Card
				player1Deck << player2Card
			}
			else if(player2Card > player1Card) {
				player2Deck << player2Card
				player2Deck << player1Card
			}
			else {
				println "Panik?"
			}
		}
		List winner = player1Deck
		if(winner.isEmpty()) winner = player2Deck
		int sum = 0
		for(int idx = 0; idx < winner.size(); idx++) {
			sum += winner[idx] * (winner.size() - idx)
		}
		sum
	}

	@Override
	public Object calculateResult2(Object fileName) {
		List lines = Util.readFile(fileName)
		
		List player1Deck = []
		List player2Deck = []
		
		int i = -1
		for(String line = lines[++i]; line.trim() != ""; line = lines[++i]) {
			if(line.startsWith("Player")) continue
			
			player1Deck << (line as Integer)
		}
		
		for(String line = lines[++i]; i < lines.size(); line = lines[++i]) {
			if(line.startsWith("Player")) continue
			
			player2Deck << (line as Integer)
		}
		
		List winner = playSubgame(player1Deck, player2Deck) [1]
		
		int sum = 0
		for(int idx = 0; idx < winner.size(); idx++) {
			sum += winner[idx] * (winner.size() - idx)
		}
		sum
	}
	
	List playSubgame(List player1Deck, List player2Deck) {
		List previousTurns = []
		while(player1Deck.size() && player2Deck.size()) {
			String thisTurn = player1Deck.join(",") + " " + player2Deck.join(",")
			if(previousTurns.contains(thisTurn)){
				return [1, player1Deck]
			}
			
			int player1Card = player1Deck[0]
			int player2Card = player2Deck[0]
			
			player1Deck.removeAt(0)
			player2Deck.removeAt(0)
			
			if(player1Deck.size() < player1Card || player2Deck.size() < player2Card) {
				if(player1Card > player2Card) {
					player1Deck << player1Card
					player1Deck << player2Card
				}
				else if(player2Card > player1Card) {
					player2Deck << player2Card
					player2Deck << player1Card
				}
			}
			else {
				List deckCopy1 = []
				player1Deck.subList(0, player1Card).each {
					deckCopy1 << it
				}
				List deckCopy2 = []
				player2Deck.subList(0, player2Card).each {
					deckCopy2 << it
				}
				
				List result = playSubgame(deckCopy1, deckCopy2)
				if(result[0] == 1) {
					player1Deck << player1Card
					player1Deck << player2Card
				}
				else {
					player2Deck << player2Card
					player2Deck << player1Card
				}
			}

			previousTurns << thisTurn
		}
		List winner = player1Deck
		int winnerNum = 1
		if(winner.isEmpty()) {
			winner = player2Deck
			winnerNum = 2
		}
		
		return [winnerNum, winner]
	}
}
