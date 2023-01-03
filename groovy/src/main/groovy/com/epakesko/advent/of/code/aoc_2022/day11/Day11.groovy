package com.epakesko.advent.of.code.aoc_2022.day11;

import java.util.regex.Matcher

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

public class Day11 extends Day{
	
	enum Operation {
		ADD,
		MULTIPLY,
		SQUARE
	}
	
	Long calculate(long i, Operation opS, opN) {
		worriedCalculate(i, opS, opN).intdiv(3)
	}
	
	Long worriedCalculate(long i, Operation opS, opN) {
		long newInt = i
		switch(opS) {
			case Operation.ADD:
				newInt += opN
				break
			case Operation.MULTIPLY:
				newInt *= opN
				break
			case Operation.SQUARE:
				newInt *= newInt
				break
		}
		newInt
	}
	
	
	class Monkey {
		Operation opSign
		int testNumber
		int opNumber
		List<Long> items
		int destIfTrue
		int destIfFalse
		long inspections = 0
		
		Monkey(Operation opSign, int opNumber, int testNumber, List items, int destIfTrue, int destIfFalse) {
			this.opSign = opSign
			this.opNumber = opNumber
			this.testNumber = testNumber
			this.items = items
			this.destIfTrue = destIfTrue
			this.destIfFalse = destIfFalse
		}
		
		String toString() {
			"$items - $opSign: $opNumber - $testNumber - $destIfTrue - $destIfFalse ### $inspections"
		}
	}
	
	Monkey buildMonkey(List<String> lines, int i) {
		Matcher itemLineMatcher = lines[i * 7 + 1] =~ /Starting items: (.*)/
		List<Long> items = itemLineMatcher[0][1].split(", ").collect { it as Long }
		Matcher opLineMatcher = lines[i * 7 + 2] =~ /Operation: new = old ([+*]) (.+)/
		Operation opSign = opLineMatcher[0][2] == "old" ? Operation.SQUARE : opLineMatcher[0][1] == "+" ? Operation.ADD : Operation.MULTIPLY
		int opNumber = opLineMatcher[0][2] == "old" ? 0 : opLineMatcher[0][2] as Integer
		Matcher testLineMatcher = lines[i * 7 + 3] =~ /Test: divisible by (\d+)/
		int testNumber = testLineMatcher[0][1] as Integer
		Matcher testTrueLineMatcher = lines[i * 7 + 4] =~ /If true: throw to monkey (\d+)/
		int destIfTrue = testTrueLineMatcher[0][1] as Integer
		Matcher testFalseLineMatcher = lines[i * 7 + 5] =~ /If false: throw to monkey (\d+)/
		int destIfFalse = testFalseLineMatcher[0][1] as Integer
		
		new Monkey(opSign, opNumber, testNumber, items, destIfTrue, destIfFalse)
	}
	
	@Override
	def calculateResult(fileName){
		List<String> lines = Util.readFile(fileName)
		List<Monkey> monkeys = new ArrayList<>()
		
		for(int i = 0; i < lines.size() / 7; i++) {
			monkeys << buildMonkey(lines, i)
		}
		
		20.times {
			monkeys.each { m ->
				m.inspections += m.items.size()
				m.items.each { item ->
					int newItem = calculate(item, m.opSign, m.opNumber)
					if(newItem % m.testNumber == 0) monkeys[m.destIfTrue].items.add(newItem)
					else monkeys[m.destIfFalse].items.add(newItem)
				}
				m.items.removeAll{ true }
			}
		}
		
		List<Monkey> most = monkeys.sort{ it.inspections }.takeRight(2)
		most[0].inspections * most[1].inspections
	}
		
	@Override
	def calculateResult2(fileName){
		List<String> lines = Util.readFile(fileName)
		List<Monkey> monkeys = new ArrayList<>()
		
		long lcm = 1
		
		for(int i = 0; i < lines.size() / 7; i++) {
			Monkey m = buildMonkey(lines, i)
			lcm *= m.testNumber
			monkeys << m
		}
		
		10000.times {
			monkeys.each { m ->
				m.inspections += m.items.size()
				m.items.each { item ->
					long newItem = worriedCalculate(item, m.opSign, m.opNumber) % lcm
					if(newItem % m.testNumber == 0) monkeys[m.destIfTrue].items.add(newItem)
					else monkeys[m.destIfFalse].items.add(newItem)
				}
				m.items.removeAll{ true }
			}
		}
		
		List<Monkey> most = monkeys.sort{ it.inspections }.takeRight(2)
		most[0].inspections * most[1].inspections
	}
}
