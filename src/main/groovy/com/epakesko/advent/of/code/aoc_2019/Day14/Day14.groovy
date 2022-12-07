package com.epakesko.advent.of.code.aoc_2019.Day14

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day
import java.awt.Point

class Day14 extends Day {
	
	@Override
	public Object calculateResult(Object fileName) {	
		List reactions = Util.readFile(fileName)
		Map<String, Map<String, Object>> howIsItProduced = new HashMap<>()
		
		reactions.each { String reaction ->
			def (consumed, produced) = reaction.split(" => ")
			Map<String, Integer> consumedMaterials = new HashMap<>()
			String[] consumedMaterialStrings = consumed.split(", ").each { String consumedMaterialString ->
				def (consumedAmount, consumedMaterial) = consumedMaterialString.split(" ")
				consumedMaterials.put(consumedMaterial, consumedAmount as Integer)
			}
			def (producedAmount, producedMaterial) = produced.split(" ")
			
			howIsItProduced.put(producedMaterial, [amount: producedAmount as Integer, materials: consumedMaterials])
		}
		Map levelMap = ["ORE":0]
		while(true) {
			howIsItProduced.each { reaction ->
				if(!levelMap.containsKey(reaction.key) && reaction.value.materials.every { levelMap.containsKey(it.key) }) {
					List levels = []
					reaction.value.materials.each {
						levels << levelMap.get(it.key)
					}
					levelMap.put(reaction.key, levels.max() + 1)
				}
			}
			if(levelMap.size() == howIsItProduced.size() + 1) break
		}
		
		Map<String, Integer> fuelMaterialMap = new HashMap<>()
		howIsItProduced.get("FUEL").materials.each { fuelMaterialMap << it}
		while(true) {
			Map<String, Integer> newFuelMaterialMap = new HashMap<>()
			boolean madeSomethingLessRefined = false
			fuelMaterialMap.each { Map.Entry consumedMaterial ->
				Map<String, Object> consumedMaterialsReaction = howIsItProduced.get(consumedMaterial.key)

				int highestLevel = levelMap.findAll{ fuelMaterialMap.containsKey(it.key) }.values().max()
				int thisLevel = levelMap.get(consumedMaterial.key)
				if(thisLevel > 1 && thisLevel == highestLevel)  {
					madeSomethingLessRefined = true
					int consumedMaterialsProducedPerReaction = consumedMaterialsReaction.amount
					int consumedMaterialsNeeded = consumedMaterial.value
					int reactionsNeeded = Math.ceil(consumedMaterialsNeeded / consumedMaterialsProducedPerReaction) as Integer
					consumedMaterialsReaction.materials.each { Map.Entry reactionElement ->
							String reactionElementName = reactionElement.key
							int amount = reactionElement.value
							
							if(!newFuelMaterialMap.containsKey(reactionElementName)) newFuelMaterialMap.put(reactionElementName, 0)
							newFuelMaterialMap.put(reactionElementName, amount * reactionsNeeded + newFuelMaterialMap.get(reactionElementName))
					}
				}
				else {
					if(!newFuelMaterialMap.containsKey(consumedMaterial.key)) newFuelMaterialMap.put(consumedMaterial.key, 0)
					newFuelMaterialMap.put(consumedMaterial.key, consumedMaterial.value + newFuelMaterialMap.get(consumedMaterial.key))
				}
			}
			if(!madeSomethingLessRefined) break
			fuelMaterialMap = newFuelMaterialMap
		}
		int oreCount = 0
		fuelMaterialMap.each { Map.Entry leastRefinedMaterial ->
			Map<String, Object> leastRefinedMaterialReaction = howIsItProduced.get(leastRefinedMaterial.key)
			int leastRefinedMaterialProducedPerReaction = leastRefinedMaterialReaction.amount
			int lestRefinedMaterialNeeded = leastRefinedMaterial.value
			int reactionsNeeded = Math.ceil(lestRefinedMaterialNeeded / leastRefinedMaterialProducedPerReaction) as Integer
			
			oreCount += reactionsNeeded * leastRefinedMaterialReaction.materials.get("ORE")
		}
		oreCount
	}
	
	@Override
	public Object calculateResult2(Object fileName) {		
		List reactions = Util.readFile(fileName)
		Map<String, Map<String, Object>> howIsItProduced = new HashMap<>()
		Map<String, Long> remainingMaterial = new HashMap<>()
		
		reactions.each { String reaction ->
			def (consumed, produced) = reaction.split(" => ")
			Map<String, Long> consumedMaterials = new HashMap<>()
			String[] consumedMaterialStrings = consumed.split(", ").each { String consumedMaterialString ->
				def (consumedAmount, consumedMaterial) = consumedMaterialString.split(" ")
				consumedMaterials.put(consumedMaterial, consumedAmount as Long)
			}
			def (producedAmount, producedMaterial) = produced.split(" ")
			
			howIsItProduced.put(producedMaterial, [amount: producedAmount as Long, materials: consumedMaterials])
			remainingMaterial.put(producedMaterial, 0)
		}
		Map levelMap = ["ORE":0]
		while(true) {
			howIsItProduced.each { reaction ->
				if(!levelMap.containsKey(reaction.key) && reaction.value.materials.every { levelMap.containsKey(it.key) }) {
					List levels = []
					reaction.value.materials.each {
						levels << levelMap.get(it.key)
					}
					levelMap.put(reaction.key, levels.max() + 1)
				}
			}
			if(levelMap.size() == howIsItProduced.size() + 1) break
		}
		
		long allOreCount = 1000000000000
		int index = 0
		while(true) {
			Map<String, Integer> fuelMaterialMap = new HashMap<>()
			howIsItProduced.get("FUEL").materials.each { fuelMaterialMap << it}
			while(true) {
				Map<String, Integer> newFuelMaterialMap = new HashMap<>()
				boolean madeSomethingLessRefined = false
				fuelMaterialMap.each { Map.Entry consumedMaterial ->
					Map<String, Object> consumedMaterialsReaction = howIsItProduced.get(consumedMaterial.key)
	
					int highestLevel = levelMap.findAll{ fuelMaterialMap.containsKey(it.key) }.values().max()
					int thisLevel = levelMap.get(consumedMaterial.key)
					if(thisLevel > 1 && thisLevel == highestLevel)  {
						madeSomethingLessRefined = true
						long consumedMaterialsProducedPerReaction = consumedMaterialsReaction.amount
						long consumedMaterialsNeeded = consumedMaterial.value - remainingMaterial.get(consumedMaterial.key)
						long reactionsNeeded = Math.ceil(consumedMaterialsNeeded / consumedMaterialsProducedPerReaction) as Long
						remainingMaterial.put(consumedMaterial.key, reactionsNeeded * consumedMaterialsProducedPerReaction - consumedMaterialsNeeded)
						
						consumedMaterialsReaction.materials.each { Map.Entry reactionElement ->
								String reactionElementName = reactionElement.key
								long amount = reactionElement.value
								
								if(!newFuelMaterialMap.containsKey(reactionElementName)) newFuelMaterialMap.put(reactionElementName, 0)
								newFuelMaterialMap.put(reactionElementName, amount * reactionsNeeded + newFuelMaterialMap.get(reactionElementName))
						}
					}
					else {
						if(!newFuelMaterialMap.containsKey(consumedMaterial.key)) newFuelMaterialMap.put(consumedMaterial.key, 0)
						newFuelMaterialMap.put(consumedMaterial.key, consumedMaterial.value + newFuelMaterialMap.get(consumedMaterial.key))
					}
				}
				if(!madeSomethingLessRefined) break
				fuelMaterialMap = newFuelMaterialMap
			}
			long oreCount = 0
			fuelMaterialMap.each { Map.Entry leastRefinedMaterial ->
				Map<String, Object> leastRefinedMaterialReaction = howIsItProduced.get(leastRefinedMaterial.key)
				
				long leastRefinedMaterialProducedPerReaction = leastRefinedMaterialReaction.amount
				long leastRefinedMaterialNeeded = leastRefinedMaterial.value - remainingMaterial.get(leastRefinedMaterial.key)
				long reactionsNeeded = Math.ceil(leastRefinedMaterialNeeded / leastRefinedMaterialProducedPerReaction) as Long
				remainingMaterial.put(leastRefinedMaterial.key, reactionsNeeded * leastRefinedMaterialProducedPerReaction - leastRefinedMaterialNeeded)
				
				oreCount += reactionsNeeded * leastRefinedMaterialReaction.materials.get("ORE")
			}
			//if(allOreCount.intdiv(10000000000) > (allOreCount - oreCount).intdiv(10000000000)) println "Remaining: ${allOreCount - oreCount}"
			allOreCount -= oreCount
			if(allOreCount <= 0) break
			index++
		}
		index
	}
}
