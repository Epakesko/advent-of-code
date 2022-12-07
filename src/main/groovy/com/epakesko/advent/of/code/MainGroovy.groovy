package com.epakesko.advent.of.code;

import com.epakesko.advent.of.code.day.Day
import com.epakesko.advent.of.code.day.DayByYearFactory
import com.epakesko.advent.of.code.aoc_2020.day01.Day01
import com.epakesko.advent.of.code.aoc_2020.day02.Day02
import com.epakesko.advent.of.code.aoc_2020.day03.Day03
import com.epakesko.advent.of.code.aoc_2020.day04.Day04
import com.epakesko.advent.of.code.aoc_2020.day05.Day05
import com.epakesko.advent.of.code.aoc_2020.day06.Day06
import com.epakesko.advent.of.code.aoc_2020.day07.Day07
import com.epakesko.advent.of.code.aoc_2020.day08.Day08
import com.epakesko.advent.of.code.aoc_2020.day09.Day09
import com.epakesko.advent.of.code.aoc_2020.day10.Day10
import com.epakesko.advent.of.code.aoc_2020.day11.Day11
import com.epakesko.advent.of.code.aoc_2020.day12.Day12
import com.epakesko.advent.of.code.aoc_2020.day13.Day13
import com.epakesko.advent.of.code.aoc_2020.day14.Day14
import com.epakesko.advent.of.code.aoc_2020.day15.Day15
import com.epakesko.advent.of.code.aoc_2020.day16.Day16
import com.epakesko.advent.of.code.aoc_2020.day17.Day17
import com.epakesko.advent.of.code.aoc_2020.day18.Day18
import com.epakesko.advent.of.code.aoc_2020.day19.Day19
import com.epakesko.advent.of.code.aoc_2020.day20.Day20
import com.epakesko.advent.of.code.aoc_2020.day21.Day21
import com.epakesko.advent.of.code.aoc_2020.day22.Day22
import com.epakesko.advent.of.code.aoc_2020.day23.Day23
import com.epakesko.advent.of.code.aoc_2020.day24.Day24
import com.epakesko.advent.of.code.aoc_2020.day25.Day25

public class MainGroovy {
	public static void main(String[] args) {
		if(args.length != 3) {
			println "ERROR"
			println "Arguments should be year, day and type. The provided arguments were: $args"
			println "Using 2020, 1 and run";
			args = ["2020", "1", "run"]
		}
		
		DayByYearFactory dayByYearFactory = new DayByYearFactory()
		
		try {
			dayByYearFactory.getDay(args[0], args[1]).start(args[2], "../resources/")
		} catch(Exception e) {
			println "ERROR:"
			e.printStackTrace()
		}
	}
}