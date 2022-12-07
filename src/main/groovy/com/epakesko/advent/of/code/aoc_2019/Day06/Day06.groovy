package com.epakesko.advent.of.code.aoc_2019.Day06

import com.epakesko.advent.of.code.aoc_2019.util.IntCode
import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

class Day06 extends Day {

	@Override
	public Object calculateResult(Object fileName) {
		List orbits = Util.readFile(fileName)
		Map<String, Set<String>> orbitedBy = new HashMap<>()
		Map<String, Set<String>> orbiting = new HashMap<>()
		
		orbits.each { String orbit ->
			List orbitParts = orbit.split("\\)")
			String orbited = orbitParts[0]
			String orbiter = orbitParts[1]
			
			Set inheritelyOrbitedBy = [orbiter]
			Set inheritelyOrbiting = [orbited]
			
			if(orbitedBy.containsKey(orbiter)) {
				inheritelyOrbitedBy.addAll(orbitedBy.get(orbiter))
			}
			if(orbiting.containsKey(orbited)) {
				inheritelyOrbiting.addAll(orbiting.get(orbited))
			}
			
			inheritelyOrbitedBy.each { inheritelyOrbitedByObject ->
				if(!orbiting.containsKey(inheritelyOrbitedByObject)) orbiting.put(inheritelyOrbitedByObject, new HashSet())
				orbiting.get(inheritelyOrbitedByObject).addAll(inheritelyOrbiting)
			}
			
			inheritelyOrbiting.each { inheritelyOrbitingObject ->
				if(!orbitedBy.containsKey(inheritelyOrbitingObject)) orbitedBy.put(inheritelyOrbitingObject, new HashSet())
				orbitedBy.get(inheritelyOrbitingObject).addAll(inheritelyOrbitedBy)
			}
		}
		
		orbiting.values().sum { Set orbitsByObject ->
			orbitsByObject.size()
		}
	}
	
	@Override
	public Object calculateResult2(Object fileName) {
		List orbits = Util.readFile(fileName)
		Map<String, Set<String>> orbitedBy = new HashMap<>()
		Map<String, Set<String>> orbiting = new HashMap<>()
		Map<String, String> allOrbits = new HashMap<>()
		
		orbits.each { String orbit ->
			List orbitParts = orbit.split("\\)")
			String orbited = orbitParts[0]
			String orbiter = orbitParts[1]
			allOrbits.put(orbiter, orbited)
			
			Set inheritelyOrbitedBy = [orbiter]
			Set inheritelyOrbiting = [orbited]
			
			if(orbitedBy.containsKey(orbiter)) {
				inheritelyOrbitedBy.addAll(orbitedBy.get(orbiter))
			}
			if(orbiting.containsKey(orbited)) {
				inheritelyOrbiting.addAll(orbiting.get(orbited))
			}
			
			inheritelyOrbitedBy.each { inheritelyOrbitedByObject ->
				if(!orbiting.containsKey(inheritelyOrbitedByObject)) orbiting.put(inheritelyOrbitedByObject, new HashSet())
				orbiting.get(inheritelyOrbitedByObject).addAll(inheritelyOrbiting)
			}
			
			inheritelyOrbiting.each { inheritelyOrbitingObject ->
				if(!orbitedBy.containsKey(inheritelyOrbitingObject)) orbitedBy.put(inheritelyOrbitingObject, new HashSet())
				orbitedBy.get(inheritelyOrbitingObject).addAll(inheritelyOrbitedBy)
			}
		}
		
		Set commonOrbits = orbiting.get("YOU").intersect(orbiting.get("SAN"))
		String prevNode = allOrbits.get("YOU")
		int count = 0
		while(true) {
			count++
			String nextNode = allOrbits.get(prevNode)
			if(commonOrbits.contains(nextNode)) break;
			prevNode = nextNode
		} 
		prevNode = allOrbits.get("SAN")
		while(true) {
			count++
			String nextNode = allOrbits.get(prevNode)
			if(commonOrbits.contains(nextNode)) break;
			prevNode = nextNode
		}
		count
	}
}
