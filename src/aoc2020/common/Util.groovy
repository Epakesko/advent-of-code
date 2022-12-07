package aoc2020.common

public class Util {
	
	def static readFile(fileName) {
		File file = new File(fileName)
		println file.getAbsolutePath()
		return file.readLines()
	}
	
	def static readFileAsInts(fileName) {
		List lines = readFile(fileName)
		lines.collect {
			it as Integer
		}
	}
}