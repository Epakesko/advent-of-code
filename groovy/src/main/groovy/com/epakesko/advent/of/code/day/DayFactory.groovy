package com.epakesko.advent.of.code.day

import com.epakesko.advent.of.code.exception.NonExistentDayException
import com.epakesko.advent.of.code.exception.UnsolvedDayException

interface DayFactory {
	public Day getDay(String day) throws NonExistentDayException, UnsolvedDayException;
}
