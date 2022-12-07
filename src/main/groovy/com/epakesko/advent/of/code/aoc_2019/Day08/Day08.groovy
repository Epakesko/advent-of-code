package com.epakesko.advent.of.code.aoc_2019.Day08

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

class Day08 extends Day {

	@Override
	public Object calculateResult(Object fileName) {
		String password = Util.readFile(fileName)[0]
		List passwordChars = password.toList()
		def collated = passwordChars.collate(150)
		List layers = collated*.join()
		
		String layerWithLeastZeroes = ""
		int min = 150
		layers.each { String layer ->
			int numberOfZeroes = layer.count("0")
			if(min > numberOfZeroes) {
				min = numberOfZeroes
				layerWithLeastZeroes = layer
			} 
		}
		
		layerWithLeastZeroes.count("1") * layerWithLeastZeroes.count("2")
	}
	
	@Override
	public Object calculateResult2(Object fileName) {
		List layers = Util.readFile(fileName)[0].toList().collate(150)*.join()
		List<List<String>> decoded = [[],[],[],[],[],[]]
		for(int j = 0; j < 6; j++) {
			for(int i = 0; i < 25; i++) {
				String color = "2"
				layers.find { String layer ->
					color = layer.getAt(j * 25 + i)
					if(!("2".equals(color))) return true
					else return false
				}
				decoded[j] << color
			}
		}
		"\n" + decoded*.join("").collect{String line -> line.replace("0", " ").replace("1", "#")}.join("\n")
	}
}
