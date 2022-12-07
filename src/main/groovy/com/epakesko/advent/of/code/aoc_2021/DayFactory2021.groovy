package com.epakesko.advent.of.code.aoc_2021

import com.epakesko.advent.of.code.aoc_2021.day01.Day01
import com.epakesko.advent.of.code.aoc_2021.day02.Day02
import com.epakesko.advent.of.code.aoc_2021.day03.Day03
import com.epakesko.advent.of.code.aoc_2021.day04.Day04
import com.epakesko.advent.of.code.aoc_2021.day05.Day05
import com.epakesko.advent.of.code.aoc_2021.day06.Day06
import com.epakesko.advent.of.code.aoc_2021.day08.Day08
import com.epakesko.advent.of.code.aoc_2021.day09.Day09
import com.epakesko.advent.of.code.aoc_2021.day10.Day10
import com.epakesko.advent.of.code.aoc_2021.day11.Day11
import com.epakesko.advent.of.code.aoc_2021.day12.Day12
import com.epakesko.advent.of.code.aoc_2021.day13.Day13
import com.epakesko.advent.of.code.aoc_2021.day14.Day14
import com.epakesko.advent.of.code.aoc_2021.day15.Day15
import com.epakesko.advent.of.code.aoc_2021.day16.Day16
import com.epakesko.advent.of.code.aoc_2021.day17.Day17
import com.epakesko.advent.of.code.day.Day
import com.epakesko.advent.of.code.day.DayFactory
import com.epakesko.advent.of.code.exception.NonExistentDayException
import com.epakesko.advent.of.code.exception.UnsolvedDayException

class DayFactory2021 implements DayFactory {
	@Override
	public Day getDay(String dayNumber) throws NonExistentDayException, UnsolvedDayException {	
		Day day = getDayObject(dayNumber);
		day.dayNumber = "day" + dayNumber.padLeft(2, "0")
		day.year = 2021
		return day
	}
	
	private Day getDayObject(String day) throws NonExistentDayException, UnsolvedDayException {
		switch(day) {
			case "01":
			case "1":
				return new Day01();
			case "02":
			case "2":
				return new Day02();
			case "03":
			case "3":
				return new Day03();
			case "04":
			case "4":
				return new Day04();
			case "05":
			case "5":
				return new Day05();
			case "06":
			case "6":
				return new Day06();
			case "07":
			case "7":
			case "08":
			case "8":
				return new Day08();
			case "09":
			case "9":
				return new Day09();
			case "10":
				return new Day10();
			case "11":
				return new Day11();
			case "12":
				return new Day12();
			case "13":
				return new Day13();
			case "14":
				return new Day14();
			case "15":
				return new Day15();
			case "16":
				return new Day16();
			case "17":
				return new Day17();
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
