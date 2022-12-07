package aoc2020.day07

import aoc2020.Day
import aoc2020.common.Util

class Day07 extends Day {

	@Override
	public Object calculateResult(Object fileName) {
		List lines = Util.readFile(fileName)
		
		Map possibleParents = [:]
		
		String pattern =  /((\d+) ([\s\w]+) bag[s]?)/
		
		Set possibleOuterBags = ["shiny gold"]
		
		lines.each { String line ->
			String[] splittedLine = line.split(" bag[s]? contain ")
			String parent = splittedLine[0]
			String fullContainment = splittedLine[1]
			
			def group = fullContainment =~ pattern
			for(int i = 0; i < group.size(); i++) {
				int numberOfBags = group[i][2]
				String nameOfBag = group[i][3]
				if(!possibleParents.containsKey(nameOfBag)) {
					possibleParents.put(nameOfBag, [])
				}
				List listOfParents = possibleParents[nameOfBag]
				listOfParents << ["parentName": parent, "numberOfBags": numberOfBags]
			}
		}
		possibleOuterBags.addAll(getParents("shiny gold", possibleParents))
		
		return possibleOuterBags.size() - 1
	}

	Set getParents(String bag, Map possibleParents) {
		Set parents = []
		possibleParents[bag].each { Map parentBag ->
			parents << parentBag["parentName"]
			parents.addAll(getParents(parentBag["parentName"], possibleParents))
		}
		return parents
	}
	
	@Override
	public Object calculateResult2(Object fileName) {
		List lines = Util.readFile(fileName)
		
		Map possibleChildren = [:]
		
		String pattern =  /((\d+) ([\s\w]+) bag[s]?)/
		
		Set possibleOuterBags = ["shiny gold"]
		
		lines.each { String line ->
			String[] splittedLine = line.split(" bag[s]? contain ")
			String parent = splittedLine[0]
			String fullContainment = splittedLine[1]
			
			if(!possibleChildren.containsKey(parent)) {
				possibleChildren.put(parent, [])
			}
			List listOfChildren = possibleChildren[parent]
			
			def group = fullContainment =~ pattern
			for(int i = 0; i < group.size(); i++) {
				int numberOfBags = group[i][2] as Integer
				String nameOfBag = group[i][3]
				listOfChildren << ["childName": nameOfBag, "numberOfBags": numberOfBags]
			}
		}
		return getNumberOfChildren("shiny gold", possibleChildren)
	}
	
	int getNumberOfChildren(String bag, Map possibleChildren) {
		int numberOfChildren = 0
		possibleChildren[bag].each { Map childBag ->
			numberOfChildren += childBag["numberOfBags"] + childBag["numberOfBags"] * getNumberOfChildren(childBag["childName"], possibleChildren)
		}
		return numberOfChildren
	}
}
