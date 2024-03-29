package com.epakesko.advent.of.code.aoc_2020.day18

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

class Day18 extends Day {
	
	enum Operator {
		ADDITION,
		PRODUCT;
		
		long calc(left, right) {
			if(this == ADDITION) {
				return left + right
			}
			else {
				return left * right
			}
		}
	}
	
	@Override
	public Object calculateResult(Object fileName) {
		List lines = Util.readFile(fileName)
		
		lines.collect { line ->
			parseSubstring(line)
		}.sum()
	}

	long parseSubstring(String line) {
		Long leftOperand = null
		Operator operator = null
		
		long result = 0
		for(int i = 0; i < line.length(); i++) {
			String c = line.getAt(i)
			if(c == "(") {
				int closingParanthesisIdx = i + findClosingParanthesis(line.substring(i+1))
				result = parseSubstring(line.substring(i+1, closingParanthesisIdx+1))
				i = closingParanthesisIdx+1
				if(leftOperand == null) leftOperand = result
				else leftOperand = operator.calc(leftOperand, result)
			}
			else if(c == " ") {
				continue
			}
			else if(c == "+"){
				operator = Operator.ADDITION
			}
			else if(c == "*"){
				operator = Operator.PRODUCT
			}
			else {
				if(leftOperand == null) leftOperand = c as Long
				else leftOperand = operator.calc(leftOperand, c as Long)
			}
		}
		leftOperand
	}
	
	int findClosingParanthesis(String line) {
		int openParanthesises = 1
		int idx = 0
		for(char c : line) {
			if(c == '(') {
				openParanthesises++
			}
			if(c == ')') {
				openParanthesises--
			}
			if(openParanthesises == 0) return idx
			idx++
		}
		
		idx
	}
	
	int findOpeningParanthesis(String line) {
		int openParanthesises = 1
		for(int i = line.size()-1; i >= 0; i--) {
			String c = line.getAt(i)
			if(c == '(') {
				openParanthesises--
			}
			if(c == ')') {
				openParanthesises++
			}
			if(openParanthesises == 0) {
				return i
			}
		}
	}
	
	@Override
	public Object calculateResult2(Object fileName) {
		List lines = Util.readFile(fileName)
		
		lines.collect { line ->
			evalLine(line)
		}.sum()
	}
	
	long evalLine(String line) {
		for(int i = 0; i < line.length(); i++) {
			String c = line.getAt(i)
			if(c == "+"){
				int endIdx = findRightOperandEnd(line, i)
				int startIdx = findLeftOperandStart(line, i)
				line = line.substring(0, startIdx) + '(' + line.substring(startIdx, endIdx) + ')' + line.substring(endIdx)
				i++
			}
		}
		parseSubstring(line)
	}
	
	int findRightOperandEnd(String line, int idx){
		if(line.getAt(idx+2) != '(') return idx + 3
		else{
			return idx + 3 + findClosingParanthesis(line.substring(idx + 3))
		}
	}
	
	int findLeftOperandStart(String line, int idx){
		if(line.getAt(idx-2) != ')') return idx - 2
		else{
			return findOpeningParanthesis(line.substring(0, idx - 2))
		}
	}
}
