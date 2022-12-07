package aoc2020.day13

import aoc2020.Day
import aoc2020.common.Util

class Day13 extends Day {

	@Override
	public Object calculateResult(Object fileName) {
		List<String> lines = Util.readFile(fileName)
		int earliest = lines[0] as Integer
		List buses = lines[1].split(",")
		
		int minBus = Integer.MAX_VALUE
		int minDepartureDiff = Integer.MAX_VALUE
		
		buses.each { bus ->
			if(bus == "x") return
			int departure = bus as Integer
			
			if(departure - (earliest % departure) < minDepartureDiff) {
				minDepartureDiff = departure - (earliest % departure)
				minBus = departure
			}
			
		}
		
		minBus * minDepartureDiff
	}

	@Override
	public Object calculateResult2(Object fileName) {
		List<String> lines = Util.readFile(fileName)
		List buses = lines[1].split(",")
		List n = buses.findAll{ it != "x"}.collect{ it as Integer }
		List a = []
		buses.eachWithIndex { it, idx ->
			if(it == "x") {}
			else {
				int num = buses[idx] as Integer
				a << (num - (idx % num))
			}
		}
		a[0] = 0
		
		chineseRemainder(n,  a)
		
	}
	
	static BigInteger chineseRemainder(List n, List a) {
		BigInteger prod = 1
		for (int i = 0; i < n.size(); i++) {
			prod *= n[i]
		}
 
		BigInteger p, sm = 0
		for (BigInteger i = 0; i < n.size(); i++) {
			p = prod.intdiv(n[i])
			sm += a[i] * mulInv(p, n[i]) * p
		}
		return sm % prod
	}
 
	private static BigInteger mulInv(BigInteger a, BigInteger b) {
		BigInteger b0 = b
		BigInteger x0 = 0
		BigInteger x1 = 1
 
		if (b == 1) {
			return 1
		}
 
		while (a > 1) {
			BigInteger q = a.intdiv(b)
			BigInteger amb = a % b
			a = b
			b = amb
			BigInteger xqx = x1 - q * x0
			x1 = x0
			x0 = xqx
		}
 
		if (x1 < 0) {
			x1 += b0
		}
 
		return x1
	}
}
