package com.epakesko.advent.of.code.day

import com.epakesko.advent.of.code.aoc_2020.DayFactory2020
import com.epakesko.advent.of.code.aoc_2021.DayFactory2021
import com.epakesko.advent.of.code.exception.NonExistentDayException
import com.epakesko.advent.of.code.exception.NonExistentYearException
import com.epakesko.advent.of.code.exception.UnsolvedDayException

class DayByYearFactory {
	public Day getDay(String year, String day) throws NonExistentYearException, NonExistentDayException, UnsolvedDayException {
		DayFactory dayFactory = getDayFactory(year);
		return dayFactory.getDay(day);
	}
	
	public DayFactory getDayFactory(String year) throws NonExistentYearException {
		switch(year) {
			case "2020": return new DayFactory2020();
			case "2021": return new DayFactory2021();
			default: throw new NonExistentYearException("The year $year either does not have AoC, or is not solved (yet)");
		}
	}
}
