package com.epakesko.advent.of.code.common

public class Util {
	
	static def readFile(fileName) {
		File file = new File(fileName)
		return file.readLines()
	}
	
	static def readFileAsInts(fileName) {
		List lines = readFile(fileName)
		lines.collect {
			it as Integer
		}
	}
	
	 static def readFileAsLongs(fileName) {
		List lines = readFile(fileName)
		lines.collect {
			it as Long
		}
	}
	
	static long getGcd(long n1, long n2) {
		return n2 == 0 ? n1 : getGcd(n2, n1 % n2)
	}
	
	static long getLcm(long n1, long n2){
		Math.abs(n1 * n2) / getGcd(n1, n2)
	}
}