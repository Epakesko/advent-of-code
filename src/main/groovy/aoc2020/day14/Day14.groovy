package aoc2020.day14

import aoc2020.Day
import aoc2020.common.Util

class Day14 extends Day {

	@Override
	public Object calculateResult(Object fileName) {
		List lines = Util.readFile(fileName)
		
		Map mem = [:]
		String mask
		String memRegex = /mem\[(\d+)\] = (\d+)/
		
		lines.each { String line ->
			if(line.startsWith("mask = ")) {
				mask = line.split("mask = ")[1]
			}
			else {
				def (_, memPos, number) = ((line =~ memRegex)[0])
				String binaryNumber = Long.toBinaryString(number as Integer)
				String numberToMask = "0" * (mask.length() - binaryNumber.length()) + binaryNumber
				mem.put(memPos, maskNumber(mask, numberToMask))
			}
		}
		
		mem.values().sum()
	}

	private Long maskNumber(String mask, String numberToMask) {
		String maskedNumber = ""
		
		if(mask.length() != numberToMask.length()) return
		else {
			for(int i = 0; i < mask.length(); i++) {
				if(mask.charAt(i) == '0') maskedNumber += "0"
				else if(mask.charAt(i) == '1') maskedNumber += "1"
				else if(mask.charAt(i) == 'X') maskedNumber += numberToMask.charAt(i)
			}
		}
		
		Long.parseUnsignedLong(maskedNumber, 2)
	}
	
	@Override
	public Object calculateResult2(Object fileName) {
		List lines = Util.readFile(fileName)
		
		Map mem = [:]
		String mask
		String memRegex = /mem\[(\d+)\] = (\d+)/
		
		lines.each { String line ->
			if(line.startsWith("mask = ")) {
				mask = line.split("mask = ")[1]
			}
			else {
				def (_, memPos, number) = ((line =~ memRegex)[0])
				String memPosBinaryNumber = Long.toBinaryString(memPos as Integer)
				String memPosToMask = "0" * (mask.length() - memPosBinaryNumber.length()) + memPosBinaryNumber
				
				List maskedMemAddresses = maskAddress(mask, memPosToMask)
				maskedMemAddresses.each { maskedMemAddress ->
					mem.put(maskedMemAddress, number as Long)
				}
			}
		}
		mem.values().sum()
	}
	
	private List maskAddress(String mask, String memPos) {
		List maskedMemAddresses = []
		if(mask.length() != memPos.length()) return
		else {
			for(int i = 0; i < mask.length(); i++) {
				if(mask.charAt(i) == '0') {
					if(maskedMemAddresses.empty) maskedMemAddresses << ""
					maskedMemAddresses = maskedMemAddresses.collect{ maskedMemAddress ->
						maskedMemAddress + memPos.charAt(i)
					}
				}
				else if(mask.charAt(i) == '1') {
					if(maskedMemAddresses.empty) maskedMemAddresses << ""
					maskedMemAddresses = maskedMemAddresses.collect{ maskedMemAddress ->
						maskedMemAddress + "1"
					}
				}
				else if(mask.charAt(i) == 'X') {
					if(maskedMemAddresses.empty) maskedMemAddresses << ""
					maskedMemAddresses *= 2
					int idx = 0
					maskedMemAddresses = maskedMemAddresses.collect{ maskedMemAddress ->
						if(idx++ < maskedMemAddresses.size() / 2) maskedMemAddress + "1"
						else maskedMemAddress + "0"
					}
				}
			}
		}
		
		maskedMemAddresses.collect {maskedMemAddress -> Long.parseUnsignedLong(maskedMemAddress, 2)}
	}
}
