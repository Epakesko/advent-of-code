package com.epakesko.advent.of.code.aoc_2021

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
