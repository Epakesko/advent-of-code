package com.epakesko.advent.of.code.aoc_2021

import com.epakesko.advent.of.code.day.Day
import com.epakesko.advent.of.code.day.DayFactory
import com.epakesko.advent.of.code.exception.NonExistentDayException
import com.epakesko.advent.of.code.exception.UnsolvedDayException
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

class DayFactory2021 implements DayFactory {
	
	@Override
	public Day getDay(String day) throws NonExistentDayException, UnsolvedDayException {
		switch(day) {
			case "01":
			case "1":
			case "02":
			case "2":
			case "03":
			case "3":
			case "04":
			case "4":
			case "05":
			case "5":
			case "06":
			case "6":
			case "07":
			case "7":
			case "08":
			case "8":
			case "09":
			case "9":
			case "10":
			case "11":
			case "12":
			case "13":
			case "14":
			case "15":
			case "16":
			case "17":
			case "18":
			case "19":
			case "20":
			case "21":
			case "22":
			case "23":
			case "24":
			case "25":
				throw new UnsolvedDayException("Day $day is not solved yet")
			default:
				throw new NonExistentDayException("Day $day is not in the advent calendar");
		}
	}
}
