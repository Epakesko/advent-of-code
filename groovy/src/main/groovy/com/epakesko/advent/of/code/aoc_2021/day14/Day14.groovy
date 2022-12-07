package com.epakesko.advent.of.code.aoc_2021.day14;

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day
import java.awt.Point

public class Day14 extends Day{	
	@Override
	def calculateResult(fileName){
		List<String> lines = Util.readFile(fileName)
		String polymer = lines[0]
		Map<String, String> reactions = new HashMap<>()
		for(int i = 2; i < lines.size(); i++) {
			def splitted = lines[i].split(" -> ")
			reactions.put(splitted[0], splitted[1])
		}
		
		
		10.times { idx ->
			String newPolymer = ""
			for(int i = 0; i < polymer.size()-1; i++) {
				newPolymer += polymer[i]
				newPolymer += reactions.get(polymer.substring(i, i+2))
			}
			newPolymer += polymer[polymer.size()-1]
			polymer = newPolymer
		}
		
		Map<String, Integer> occurences = new HashMap<>()
		polymer.each { String c ->
			if(!occurences.containsKey(c)) occurences.put(c, 0)
			occurences.put(c, occurences.get(c) + 1)
		}
		
		occurences.values().max() - occurences.values().min()
	}

	@Override
	def calculateResult2(fileName){
		List<String> lines = Util.readFile(fileName)
		String polymer = lines[0]
		Map<String, String> reactions = new HashMap<>()
		Map<String, Long> reactionOccurences = new HashMap<>()
		for(int i = 2; i < lines.size(); i++) {
			def splitted = lines[i].split(" -> ")
			reactions.put(splitted[0], splitted[1])
			reactionOccurences.put(splitted[0], 0)
		}
		
		for(int i = 0; i < polymer.size()-1; i++) {
			reactionOccurences.put(polymer.substring(i, i+2), reactionOccurences.get(polymer.substring(i, i+2)) + 1L)
		}
		
		40.times {
			Map<String, Long> newReactionOccurences = reactions.collectEntries{ [it.key, 0L] }
			reactionOccurences.each { reactionOccurence ->
				String newElement = reactions.get(reactionOccurence.key)
				String newReactionMaterial1 = reactionOccurence.key[0] + newElement
				String newReactionMaterial2 = newElement + reactionOccurence.key[1]
				
				newReactionOccurences.put(newReactionMaterial1, newReactionOccurences.get(newReactionMaterial1) + reactionOccurence.value)
				newReactionOccurences.put(newReactionMaterial2, newReactionOccurences.get(newReactionMaterial2) + reactionOccurence.value)
			}
			reactionOccurences = newReactionOccurences
		}
		
		Map<String, Long> occurences = new HashMap<>()
		reactionOccurences.each { String c, long num ->
			if(!occurences.containsKey(c[0])) occurences.put(c[0], 0)
			if(!occurences.containsKey(c[1])) occurences.put(c[1], 0)
			occurences.put(c[0], occurences.get(c[0]) + num)
			occurences.put(c[1], occurences.get(c[1]) + num)
		}
		
		occurences.put(polymer[0], occurences.get(polymer[0]) + 1)
		occurences.put(polymer[polymer.size()-1], occurences.get(polymer[polymer.size()-1]) + 1)
		
		occurences.values().max()/2L - occurences.values().min()/2L
	}
}
