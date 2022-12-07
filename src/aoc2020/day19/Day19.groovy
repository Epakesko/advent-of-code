package aoc2020.day19

import aoc2020.Day
import aoc2020.common.Util

class Day19 extends Day {
	
	@Override
	public Object calculateResult(Object fileName) {
		List lines = Util.readFile(fileName)
		
		Map ruleMap = [:]
		int i = 0
		for(String line = lines[i]; line != null && line.trim() != ""; line = lines[++i]) {
			List splittedLine = line.split(": ")
			int ruleNum = splittedLine[0] as Integer
			List rules = splittedLine[1].split (" \\| ")
			
			ruleMap[ruleNum] = rules
		}
		String mainRule = getRuleRegex(0, ruleMap)
		List codes = lines.subList(i, lines.size())
		
		codes.count { code ->
			code ==~ mainRule
		}
	}

	String getRuleRegex(int ruleNum, Map rules) {
		String regexString = "("
		for(int i = 0; i < rules[ruleNum].size(); i++) {
			String rule = rules[ruleNum][i]
			def matcher = (rule =~ /\"[a-z]\"/)
			if(matcher.matches()) {
				regexString += matcher[0][1]
				break
			}
			if(i > 0) regexString += "|"
			rule.split(" ").each { num ->
				regexString += getRuleRegex(num as Integer, rules)
			}
		}
		return regexString + ")"
	}
	
	String getRuleRegex2(int ruleNum, Map rules, int parentRule) {
		String regexString = "("
		for(int i = 0; i < rules[ruleNum].size(); i++) {
			String rule = rules[ruleNum][i]
			def matcher = (rule =~ /\"[a-z]\"/)
			if(matcher.matches()) {
				regexString += matcher[0][1]
				break
			}
			if(i > 0) regexString += "|"
			rule.split(" ").each { num ->
				regexString += getRuleRegex2(num as Integer, rules, ruleNum)
			}
		}
		if(ruleNum == 8) return regexString + ")+"
		if(parentRule == 11) return regexString + "){x}"
		return regexString + ")"
	}
	
	@Override
	public Object calculateResult2(Object fileName) {
		List lines = Util.readFile(fileName)
		
		Map ruleMap = [:]
		int i = 0
		for(String line = lines[i]; line != null && line.trim() != ""; line = lines[++i]) {
			List splittedLine = line.split(": ")
			int ruleNum = splittedLine[0] as Integer
			List rules = splittedLine[1].split (" \\| ")
			
			ruleMap[ruleNum] = rules
		}
		String mainRule = getRuleRegex2(0, ruleMap, -1)

		List codes = lines.subList(i, lines.size())
		
		codes.count { code ->
			boolean found = false
			for(int idx = 1; idx < 44; idx++) {
				if(code ==~ mainRule.replace("x", "$idx")) {
					found = true
					break
				}
			}
			found
		}
	}
}
