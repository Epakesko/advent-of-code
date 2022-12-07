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

public class Main {
	public static void main(String[] args) {
		if(args.length < 2) {
			println "\u001B[31mERROR \u001B[0m"
			println "Arguments should be year and day and optionally 'test'. The provided arguments were: $args"
			println "Using 2020, all";
			args = ["2020", "all"]
		}
		
		println "\u001B[36mSolving " + (args[1].equals("all") ? "all days" : "day ${args[1]}") + " of year ${args[0]}\u001B[0m"
		println "\u001B[36m -------------------------------------------------------------------------------\u001B[0m"
		println "\u001B[36m| DAY          PART1 (TIME)                   PART2 (TIME)\t\t\t     |\u001B[0m"
		println "\u001B[36m -------------------------------------------------------------------------------\u001B[0m"
		
		DayByYearFactory dayByYearFactory = new DayByYearFactory()
		
		try {
			if("all".equals(args[1])) {
				(1..25).each {
					dayByYearFactory.getDay(args[0], "$it").solve((args as List)[2] ?: "")
				}
			}
			else {
				dayByYearFactory.getDay(args[0], args[1]).solve((args as List)[2] ?: "")
			}
		} catch(Exception e) {
			println "\u001B[31mERROR \u001B[0m"
			e.printStackTrace()
		}
	}
}