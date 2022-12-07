package aoc2020.common

public class Util {
	
	def static readFile(fileName) {
		File file = new File(fileName)
		return file.readLines()
	}
	
	def static readFileAsInts(fileName) {
		List lines = readFile(fileName)
		lines.collect {
			it as Integer
		}
	}
	
	def static readFileAsLongs(fileName) {
		List lines = readFile(fileName)
		lines.collect {
			it as Long
		}
	}
}