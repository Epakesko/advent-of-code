package aoc2020.common

public class Util {
	
	def static readFile(fileName) {
		File file = new File(fileName)
		println file.getAbsolutePath()
		return file.readLines()
	}
}