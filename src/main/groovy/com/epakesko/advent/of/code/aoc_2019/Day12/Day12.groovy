package com.epakesko.advent.of.code.aoc_2019.Day12

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day
import java.awt.Point

class Day12 extends Day {
	
	@Override
	public Object calculateResult(Object fileName) {	
		List moonStrings = Util.readFile(fileName)
		Set moons = []
		moonStrings.eachWithIndex { String moonString, int idx ->
			moons << new Moon(moonString, idx)
		}
		
		1000.times { 
			moons.each { Moon moon ->
				moons.each { Moon relativeMoon ->
					if(!moon.equals(relativeMoon)) {
						moon.velocityChange.move(moon.getRelativeVelocity(relativeMoon))
					}
				}
			}
			moons.each { Moon moon ->
				moon.move()
			}
		}
		int sumEnergy = 0
		moons.each { Moon moon ->
			sumEnergy += moon.position.getEnergy() * moon.velocity.getEnergy()
		}
		sumEnergy
	}
	
	@Override
	public Object calculateResult2(Object fileName) {
		List moonStrings = Util.readFile(fileName)
		Set moons = []
		moonStrings.eachWithIndex { String moonString, int idx ->
			moons << new Moon(moonString, idx)
		}
		
		Map firstOccurenceOfSamePos = new HashMap<>()
		
		long index = 0;
		while(true){
			index++
			moons.each { Moon moon ->
				moons.each { Moon relativeMoon ->
					if(!moon.equals(relativeMoon)) {
						moon.velocityChange.move(moon.getRelativeVelocity(relativeMoon))
					}
				}
			}
			moons.each { Moon moon ->
				moon.move()
			}
			if(moons[0].position.x == moons[0].originalPosition.x && moons[0].velocity.x == 0 &&
				moons[1].position.x == moons[1].originalPosition.x && moons[1].velocity.x == 0 && 
				moons[2].position.x == moons[2].originalPosition.x && moons[2].velocity.x == 0) if(!firstOccurenceOfSamePos.containsKey("x")) firstOccurenceOfSamePos.put("x", index)
			if(moons[0].position.y == moons[0].originalPosition.y && moons[0].velocity.y == 0 &&
				moons[1].position.y == moons[1].originalPosition.y && moons[1].velocity.y == 0 && 
				moons[2].position.y == moons[2].originalPosition.y && moons[2].velocity.y == 0)  if(!firstOccurenceOfSamePos.containsKey("y")) firstOccurenceOfSamePos.put("y", index)
			if(moons[0].position.z == moons[0].originalPosition.z && moons[0].velocity.z == 0 &&
				moons[1].position.z == moons[1].originalPosition.z && moons[1].velocity.z == 0 && 
				moons[2].position.z == moons[2].originalPosition.z && moons[2].velocity.z == 0)  if(!firstOccurenceOfSamePos.containsKey("z")) firstOccurenceOfSamePos.put("z", index)
			if(firstOccurenceOfSamePos.size() == 3) break;
		}
		long lcm = 1
		firstOccurenceOfSamePos.values().each {
			lcm = Util.getLcm(it, lcm)
		}
		println firstOccurenceOfSamePos
		lcm
	}
	
	class Moon {
		int id
		Coordinates3D originalPosition
		Coordinates3D position
		Coordinates3D velocity
		Coordinates3D velocityChange
		
		Moon(String moonString, int id) {
			this.id = id
			moonString = moonString.replaceAll(/[< >]/, "")
			List splittedMoonString = moonString.split(",")
			position = new Coordinates3D(moonString.split(",")[0].split("=")[1] as Integer, moonString.split(",")[1].split("=")[1] as Integer, moonString.split(",")[2].split("=")[1] as Integer)
			originalPosition = new Coordinates3D(moonString.split(",")[0].split("=")[1] as Integer, moonString.split(",")[1].split("=")[1] as Integer, moonString.split(",")[2].split("=")[1] as Integer)
			velocity = new Coordinates3D(0, 0, 0)
			velocityChange = new Coordinates3D(0, 0, 0)
		}
		
		@Override
		boolean equals(Object obj) {
			(obj instanceof Moon) && (obj.id == this.id)
		}
		
		Coordinates3D getRelativeVelocity(Moon moon) {
			position.getRelativeCoordinates(moon.position)
		}
		
		void move() {
			velocity.move(velocityChange)
			velocityChange.reset()
			position.move(velocity)
		}
		
		@Override
		String toString() {
			"pos=$position, vel=$velocity"
		}
	}
	
	class Coordinates3D {
		int x, y, z
		
		Coordinates3D(int x, int y, int z) {
			this.x = x
			this.y = y
			this.z = z
		}
		
		Coordinates3D getRelativeCoordinates(Coordinates3D coords) {
			new Coordinates3D(getDirection(this.x, coords.x), getDirection(this.y, coords.y), getDirection(this.z, coords.z))
		} 
		
		private int getDirection(int from, int to) {
			from < to ? 1 : from == to ? 0 : -1
		}
		
		void move(Coordinates3D moveDir) {
			x += moveDir.x
			y += moveDir.y
			z += moveDir.z
		}
		
		void reset() {
			x = 0
			y = 0
			z = 0
		}
		
		int getEnergy() {
			Math.abs(x) + Math.abs(y) + Math.abs(z)
		}
		
		@Override
		String toString() {
			"<x=${String.valueOf(x).padLeft(3)}, y=${String.valueOf(y).padLeft(3)}, z=${String.valueOf(z).padLeft(3)}>}"
		}
		
		@Override
		boolean equals(Object obj) {
			(obj instanceof Coordinates3D) && (obj.x == this.x && obj.y == this.y && obj.z == this.z)
		}
	}
	
}
