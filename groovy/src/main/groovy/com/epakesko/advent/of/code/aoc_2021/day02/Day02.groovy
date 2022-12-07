package com.epakesko.advent.of.code.aoc_2021.day02;

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

public class Day02 extends Day{
	final static String FORWARD = "forward"
	final static String UP = "up"
	final static String DOWN = "down"
	
	
	@Override
	def calculateResult(fileName){
		List commandList = Util.readFile(fileName)
		int h, v
		commandList.each { command ->
			def splitCommand = command.split(" ")
			int arg = splitCommand[1] as Integer
			switch(splitCommand[0]) {
				case FORWARD: h += arg; break;
				case UP: v -= arg; break;
				case DOWN: v += arg; break;
			}
		}
		h * v
	}
	
	@Override
	def calculateResult2(fileName){
		List commandList = Util.readFile(fileName)
		int h, v, a
		commandList.each { command ->
			def splitCommand = command.split(" ")
			int arg = splitCommand[1] as Integer
			switch(splitCommand[0]) {
				case FORWARD: h += arg; v+= arg * a; break;
				case UP: a -= arg; break;
				case DOWN: a += arg; break;
			}
		}
		h * v
	}
}
