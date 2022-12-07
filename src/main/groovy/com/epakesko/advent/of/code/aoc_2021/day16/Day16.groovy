package com.epakesko.advent.of.code.aoc_2021.day16;

import com.epakesko.advent.of.code.aoc_2019.Day18.Day18.Node
import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day
import java.awt.Point

public class Day16 extends Day{	
	@Override
	def calculateResult(fileName){
		List<String> lines = Util.readFile(fileName)
		String binaryString = new BigInteger(lines[0], 16).toString(2);
		int firstValue = Integer.parseInt(lines[0][0], 16)
		
		if(firstValue < 2) binaryString = "000$binaryString"
		else if(firstValue < 4) binaryString = "00$binaryString"
		else if(firstValue < 8) binaryString = "0$binaryString"
		
		Packet p = new Packet(binaryString)
		p.getVersionSum()
	}
	
	class Packet {
		int depth = 0
		int version
		int id
		long value
		List<Packet> subPackets
		
		Packet(String packetString) {
			this(packetString, 0)
		}
		
		Packet(String packetString, int d) {
			version = Integer.parseInt(packetString.substring(0, 3), 2)
			id = Integer.parseInt(packetString.substring(3, 6), 2)
			depth = d
			subPackets = new ArrayList<>()
			
			if(id != 4) {
				int lengthTypeId = (packetString[6] as Integer)
				if(lengthTypeId) {
					int numberOfSubpackets = Integer.parseInt(packetString.substring(7, 18), 2)
					int nextStart = 18
					numberOfSubpackets.times {
						int nextEnd = nextStart + getSubpacketLength(packetString.substring(nextStart))
						subPackets << new Packet(packetString.substring(nextStart, nextEnd), depth+1)
						nextStart = nextEnd
					}
				}
				else {
					int subpacketEnd = 22 + Integer.parseInt(packetString.substring(7, 22), 2)
					int processedLength = 22
					while(processedLength < subpacketEnd) {
						int newProcessedLength = processedLength + getSubpacketLength(packetString.substring(processedLength))
						subPackets << new Packet(packetString.substring(processedLength, newProcessedLength), depth+1)
						processedLength = newProcessedLength
					}
				}
			}
			
			switch(id)  {
				case 0:
					subPackets.each {
						value += it.value
					}
					break
				case 1:
					value = 1
					subPackets.each {
						value *= it.value
					}
					break
				case 2:
					value = subPackets.min{it.value}.value
					break
				case 3:
					value = subPackets.max{it.value}.value
					break
				case 4:
					value = readValuePacket(packetString.substring(6, getSubpacketLength(packetString)))
					break
				case 5:
					value = (subPackets[0].value > subPackets[1].value)? 1 : 0
					break
				case 6:
					value = (subPackets[0].value < subPackets[1].value)? 1 : 0
					break
				case 7:
					value = (subPackets[0].value == subPackets[1].value)? 1 : 0
					break
			}
		}
		
		@Override
		String toString() {
			String packetString = " -> " * depth
			packetString = "$packetString Packet ID: $id, version: $version"
			if(value) packetString = "$packetString, value: $value"
			if(!subPackets.empty) {
				subPackets.each { packetString = "$packetString\n${it.toString()}" }
			}
			packetString
		}
		
		long getVersionSum() {
			long versionSum = version
			subPackets.each {
				versionSum += it.getVersionSum()
			}
			versionSum
		}
	}
	
	int getSubpacketLength(String subPacket) {
		int id = Integer.parseInt(subPacket.substring(3, 6), 2)
		if(id == 4) {
			return 6 + getValuePacketEnd(subPacket.substring(6))
		}
		else {
			int lengthTypeId = (subPacket[6] as Integer)
			if(lengthTypeId) {
				int numberOfSubpackets = Integer.parseInt(subPacket.substring(7, 18), 2)
				int nextStart = 18
				numberOfSubpackets.times {
					nextStart += getSubpacketLength(subPacket.substring(nextStart))
				}
				return nextStart
			}
			else {
				return 22 + Integer.parseInt(subPacket.substring(7, 22), 2)
			}
		}
	}
	
	int getValuePacketEnd(String binaryString) {
		int end = 0
		while("1".equals(binaryString[end])) end += 5
		end+5
	}

	
	long readValuePacket(String binaryString) {
		String valueBinaryString = ""
		for(int i = 0; i < binaryString.size(); i += 5) {
			valueBinaryString = "$valueBinaryString${binaryString.substring(i+1, i+5)}"
		}
		return Long.parseLong(valueBinaryString, 2)
	}
	
	@Override
	def calculateResult2(fileName){
				List<String> lines = Util.readFile(fileName)
		String binaryString = new BigInteger(lines[0], 16).toString(2);
		int firstValue = Integer.parseInt(lines[0][0], 16)
		
		if(firstValue < 2) binaryString = "000$binaryString"
		else if(firstValue < 4) binaryString = "00$binaryString"
		else if(firstValue < 8) binaryString = "0$binaryString"
		
		Packet p = new Packet(binaryString)
		p.value
	}
}
