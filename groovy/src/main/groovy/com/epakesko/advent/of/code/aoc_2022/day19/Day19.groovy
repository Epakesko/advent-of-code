package com.epakesko.advent.of.code.aoc_2022.day19;

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

public class Day19 extends Day{
	
	class Blueprint {
		String id
		int oreCollectorOreReq
		int clayCollectorOreReq
		int obsCollectorOreReq
		int obsCollectorClayReq
		int geodeCollectorOreReq
		int geodeCollectorObsReq
		int oreCollectorLimit
		
		public Blueprint(id) {
			this.id = id
		}
	}
	
	class Status {
		int ore = 0
		int clay = 0
		int obsidian = 0
		int geode = 0
		int oreCollector = 1
		int clayCollector = 0
		int obsidianCollector = 0
		int geodeCollector = 0
		
		String getId() {
			"$ore $clay $obsidian $geode $oreCollector $clayCollector $obsidianCollector $geodeCollector"
		}
		
		boolean canBuildGeodeCollector(Blueprint b) {
			b.geodeCollectorOreReq <= ore && b.geodeCollectorObsReq <= obsidian 
		}
		
		void buildGeodeCollector(Blueprint b) {
			ore -= b.geodeCollectorOreReq
			obsidian -= b.geodeCollectorObsReq
			geodeCollector++
		}
		
		boolean canBuildObsCollector(Blueprint b) {
			b.obsCollectorOreReq <= ore && b.obsCollectorClayReq <= clay && b.geodeCollectorObsReq > obsidianCollector
		}
		
		void buildObsCollector(Blueprint b) {
			ore -= b.obsCollectorOreReq
			clay -= b.obsCollectorClayReq
			obsidianCollector++
		}
		
		boolean canBuildClayCollector(Blueprint b) {
			b.clayCollectorOreReq <= ore && b.obsCollectorClayReq > clayCollector
		}
		
		void buildClayCollector(Blueprint b) {
			ore -= b.clayCollectorOreReq
			clayCollector++
		}
		
		boolean canBuildOreCollector(Blueprint b) {
			b.oreCollectorOreReq <= ore && b.oreCollectorLimit > oreCollector
		}
		
		void buildOreCollector(Blueprint b) {
			ore -= b.oreCollectorOreReq
			oreCollector++
		}
		
		Status build() {
			Status nextStatus = new Status()
			nextStatus.ore = ore + oreCollector
			nextStatus.clay = clay + clayCollector
			nextStatus.obsidian = obsidian + obsidianCollector
			nextStatus.geode = geode + geodeCollector
			nextStatus.oreCollector = oreCollector
			nextStatus.clayCollector = clayCollector
			nextStatus.geodeCollector = geodeCollector
			nextStatus.obsidianCollector = obsidianCollector
			nextStatus
		}
	}
	
	@Override
	def calculateResult(fileName){
		List<String> lines = Util.readFile(fileName)
		List<Blueprint> blueprints = new ArrayList<>()
		
		lines.each { line ->
			line = line.trim()
			def m = line =~ /Blueprint (\d+):/
			if(m.find()) blueprints << new Blueprint(m[0][1])
			else {
				Blueprint last = blueprints.last()
				m = line =~ /Each ore robot costs (\d+) ore/
				if(m.find()) last.oreCollectorOreReq = m[0][1] as Integer
				else {
					m = line =~ /Each clay robot costs (\d+) ore/
					if(m.find()) last.clayCollectorOreReq = m[0][1] as Integer
					else {
						m = line =~ /Each obsidian robot costs (\d+) ore and (\d+) clay/
						if(m.find()) {
							last.obsCollectorOreReq = m[0][1] as Integer
							last.obsCollectorClayReq = m[0][2] as Integer
						} else {
							m = line =~ /Each geode robot costs (\d+) ore and (\d+) obsidian/
							if(m.find()) {
								 last.geodeCollectorOreReq = m[0][1] as Integer
								 last.geodeCollectorObsReq = m[0][2] as Integer
								 last.oreCollectorLimit = [last.clayCollectorOreReq, last.geodeCollectorOreReq, last.obsCollectorOreReq].max()
							}
						}
					}
				}
			}
		}
		blueprints.sum { blueprint ->
			Status s = new Status()
			Map<String, Status> visited = new HashMap<>()
			List<String> candidates = new ArrayList<>()
			
			candidates.add(s.getId())
			visited.put(s.getId(), s)
			
			24.times {
				List<String> newCandidates = new ArrayList<>()
				while(!candidates.empty) {
					String candidateString = candidates.pop()
					Status sNew = visited.get(candidateString)
					
					if(sNew.canBuildGeodeCollector(blueprint)) {
						Status sNew1 = sNew.build()
						sNew1.buildGeodeCollector(blueprint)
						if(!visited.containsKey(sNew1.getId())) {
							visited.put(sNew1.getId(), sNew1)
							newCandidates.add(sNew1.getId());
						}
					}
					else if(sNew.canBuildObsCollector(blueprint)) {
						Status sNew1 = sNew.build()
						sNew1.buildObsCollector(blueprint)
						if(!visited.containsKey(sNew1.getId())) {
							visited.put(sNew1.getId(), sNew1)
							newCandidates.add(sNew1.getId());
						}
					}
					else {
						if(sNew.canBuildClayCollector(blueprint)) {
							Status sNew1 = sNew.build()
							sNew1.buildClayCollector(blueprint)
							if(!visited.containsKey(sNew1.getId())) {
								visited.put(sNew1.getId(), sNew1)
								newCandidates.add(sNew1.getId());
							}
						}
						if(sNew.canBuildOreCollector(blueprint)) {
							Status sNew1 = sNew.build()
							sNew1.buildOreCollector(blueprint)
							if(!visited.containsKey(sNew1.getId())) {
								visited.put(sNew1.getId(), sNew1)
								newCandidates.add(sNew1.getId());
							}
						}
					
						Status sNew1 = sNew.build()
						if(!visited.containsKey(sNew1.getId())) {
							visited.put(sNew1.getId(), sNew1)
							newCandidates.add(sNew1.getId());
						}
					}
				}
				candidates = newCandidates
			}
			def maxGeo = visited.values().max { it.geode }.geode
			(blueprint.id as Integer) * maxGeo
		}
	}
	
	@Override
	def calculateResult2(fileName) {
		List<String> lines = Util.readFile(fileName)
		List<Blueprint> blueprints = new ArrayList<>()
		lines.each { line ->
			line = line.trim()
			def m = line =~ /Blueprint (\d+):/
			if(m.find()) blueprints << new Blueprint(m[0][1])
			else {
				Blueprint last = blueprints.last()
				m = line =~ /Each ore robot costs (\d+) ore/
				if(m.find()) last.oreCollectorOreReq = m[0][1] as Integer
				else {
					m = line =~ /Each clay robot costs (\d+) ore/
					if(m.find()) last.clayCollectorOreReq = m[0][1] as Integer
					else {
						m = line =~ /Each obsidian robot costs (\d+) ore and (\d+) clay/
						if(m.find()) {
							last.obsCollectorOreReq = m[0][1] as Integer
							last.obsCollectorClayReq = m[0][2] as Integer
						} else {
							m = line =~ /Each geode robot costs (\d+) ore and (\d+) obsidian/
							if(m.find()) {
								 last.geodeCollectorOreReq = m[0][1] as Integer
								 last.geodeCollectorObsReq = m[0][2] as Integer
								 last.oreCollectorLimit = [last.clayCollectorOreReq, last.geodeCollectorOreReq, last.obsCollectorOreReq].max()
							}
						}
					}
				}
			}
			
		}
		int product = 1
		blueprints[0..2].each { blueprint ->
			println "${blueprint.id} $product"
			Status s = new Status()
			Map<String, Status> visited = new HashMap<>()
			List<String> candidates = new ArrayList<>()
			
			candidates.add(s.getId())
			visited.put(s.getId(), s)
			
			32.times {
				println "$it ${candidates.size()}"
				List<String> newCandidates = new ArrayList<>()
				while(!candidates.empty) {
					String candidateString = candidates.pop()
					Status sNew = visited.get(candidateString)
					
					if(sNew.canBuildGeodeCollector(blueprint)) {
						Status sNew1 = sNew.build()
						sNew1.buildGeodeCollector(blueprint)
						if(!visited.containsKey(sNew1.getId())) {
							visited.put(sNew1.getId(), sNew1)
							newCandidates.add(sNew1.getId());
						}
					}
					else if(sNew.canBuildObsCollector(blueprint)) {
						Status sNew1 = sNew.build()
						sNew1.buildObsCollector(blueprint)
						if(!visited.containsKey(sNew1.getId())) {
							visited.put(sNew1.getId(), sNew1)
							newCandidates.add(sNew1.getId());
						}
					}
					else {
						if(sNew.canBuildClayCollector(blueprint)) {
							Status sNew1 = sNew.build()
							sNew1.buildClayCollector(blueprint)
							if(!visited.containsKey(sNew1.getId())) {
								visited.put(sNew1.getId(), sNew1)
								newCandidates.add(sNew1.getId());
							}
						}
						if(sNew.canBuildOreCollector(blueprint)) {
							Status sNew1 = sNew.build()
							sNew1.buildOreCollector(blueprint)
							if(!visited.containsKey(sNew1.getId())) {
								visited.put(sNew1.getId(), sNew1)
								newCandidates.add(sNew1.getId());
							}
						}
					
						Status sNew1 = sNew.build()
						if(!visited.containsKey(sNew1.getId())) {
							visited.put(sNew1.getId(), sNew1)
							newCandidates.add(sNew1.getId());
						}
					}
				}
				candidates = newCandidates
			}
			def maxGeo = visited.values().max { it.geode }.geode
			product *= maxGeo
		}
		product
	}
}
