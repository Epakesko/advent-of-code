package aoc2020.day04

import aoc2020.Day
import aoc2020.common.Util

class Day04 extends Day {

	@Override
	public Object calculateResult(Object fileName) {
		List requiredKeys = ["byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"]
		
		List list = Util.readFile(fileName)
		List passwords = []
		int passwordIndex = 0
		list.each { String line ->
			if(line == "") {
				passwordIndex++
				return
			}
			List parsedPasswordElements = line.split(" ")
			if(passwords[passwordIndex] == null) {
				passwords << [:]
			}
			parsedPasswordElements.each { String parsedPasswordElement ->
				int colonIndex = parsedPasswordElement.indexOf(":");
				String key = parsedPasswordElement.substring(0, colonIndex)
				String value = parsedPasswordElement.substring(colonIndex + 1)
				Map currentPassword = passwords[passwordIndex]
				currentPassword.put(key, value)
			}
		}
		
		passwords.count { Map passwordElements ->
			!requiredKeys.find{ key -> !passwordElements.containsKey(key)}
		}
	}

	@Override
	public Object calculateResult2(Object fileName) {
		List requiredKeys = ["byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"]
		
		def list = Util.readFile(fileName)
		def passwords = []
		int passwordIndex = 0
		list.each { line ->
			if(line == "") {
				passwordIndex++
				return
			}
			List parsedPasswordElements = line.split(" ")
			if(passwords[passwordIndex] == null) {
				passwords << [:]
			}
			parsedPasswordElements.each { String parsedPasswordElement ->
				int colonIndex = parsedPasswordElement.indexOf(":");
				String key = parsedPasswordElement.substring(0, colonIndex)
				String value = parsedPasswordElement.substring(colonIndex + 1)
				Map currentPassword = passwords[passwordIndex]
				currentPassword.put(key, value)
			}
		}
		
		passwords.count { Map passwordElements ->
			isByrValid(passwordElements["byr"]) && isIyrValid(passwordElements["iyr"]) && isEyrValid(passwordElements["eyr"])&&
				isHgtValid(passwordElements["hgt"]) && isHclValid(passwordElements["hcl"]) && isEclValid(passwordElements["ecl"]) && isPidValid(passwordElements["pid"])
		}
	}
	
	private boolean isByrValid(String byr) {
		if(byr == null || !byr.isInteger()) return false
		int byrValue = byr as Integer
		
		byrValue <= 2002 && byrValue >= 1920
	}
	
	private boolean isIyrValid(String iyr) {
		if(iyr == null || !iyr.isInteger()) return false
		int iyrValue = iyr as Integer
		
		iyrValue <= 2020 && iyrValue >= 2010
	}
	
	private boolean isEyrValid(String eyr) {
		if(eyr == null || !eyr.isInteger()) return false
		int eyrValue = eyr as Integer
		
		eyrValue <= 2030 && eyrValue >= 2020
	}
	
	private boolean isHgtValid(String hgt) {
		(hgt ==~ /(1[5-8][0-9]|19[0-3])cm/) || (hgt ==~ /(59|6[0-9]|7[0-6])in/)
	}
	
	private boolean isHclValid(String hcl) {
		hcl ==~ /#(\d|[a-f]){6}/
	}
	
	private boolean isEclValid(String ecl) {
		List validColors = ["amb", "blu", "brn", "gry", "grn", "hzl", "oth"]
		validColors.contains(ecl)
	}
	
	private boolean isPidValid(String pid) {
		pid ==~ /\d{9}/
	}
	
}
