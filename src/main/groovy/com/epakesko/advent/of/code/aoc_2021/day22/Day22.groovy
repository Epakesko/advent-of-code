package com.epakesko.advent.of.code.aoc_2021.day22;

import com.epakesko.advent.of.code.aoc_2019.Day18.Day18.Node
import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

import java.awt.Point

public class Day22 extends Day{	
	
	@Override
	def calculateResult(fileName){
		return -1
		List<String> lines = Util.readFile(fileName)
		List<Cuboid> cuboids = new ArrayList<>()
		
		lines.each { line ->
			def split = line.split(" ")
			boolean on = "on".equals(split[0])
			def xyz = split[1].split(",")
			def x = xyz[0].split("x=")[1]
			def y = xyz[1].split("y=")[1]
			def z = xyz[2].split("z=")[1]
			def minmaxx = x.split(/\.\./)
			def minmaxy = y.split(/\.\./)
			def minmaxz = z.split(/\.\./)
			
			cuboids.add(new Cuboid(minmaxx[0] as Integer, minmaxy[0] as Integer, minmaxz[0] as Integer, minmaxx[1] as Integer, minmaxy[1] as Integer, minmaxz[1] as Integer, on))
		}
		
		int result
		for(int x = -50; x <= 50; x++) {
			for(int y = -50; y <= 50; y++) {
				for(int z = -50; z <= 50; z++ ) {
					boolean on = false
					cuboids.each { Cuboid c ->
						if(c.isInside(x, y, z)) {
							if(on != c.on) on = !on
						}
					}
					if(on) result++
				}
			}
		}
		result
	}
	
	class Cuboid {
		long minx
		long miny
		long minz
		long maxx
		long maxy
		long maxz
		boolean on
		
		Cuboid(minx, miny, minz, maxx, maxy, maxz) {
			this(minx, miny, minz, maxx, maxy, maxz, true)
		}
		
		Cuboid(minx, miny, minz, maxx, maxy, maxz, on) {
			this.minx = minx
			this.miny = miny
			this.minz = minz
			this.maxx = maxx
			this.maxy = maxy
			this.maxz = maxz
			this.on = on
		}
		
		boolean isInside(long x, long y, long z) {
			x >= minx && x <= maxx && y >= miny && y <= maxy && z >= minz && z <= maxz
		}
		
		long size() {
			(maxx - minx + 1) * (maxy - miny + 1) * (maxz - minz + 1)
		}
		
		@Override
		String toString() {
			"X: $minx - $maxx, Y: $miny - $maxy, Z: $minz - $maxz, size: ${size()}"
		}
	}
	
	List<Cuboid> getDifference(Cuboid c1, Cuboid intersectionCuboid) {
		List<Cuboid> smallerCuboids = new ArrayList<>()
		if(!intersectionCuboid) return smallerCuboids
		//println "remove $intersectionCuboid from $c1"
		if(c1.minx < intersectionCuboid.minx) smallerCuboids.add(new Cuboid(c1.minx, c1.miny, c1.minz, intersectionCuboid.minx - 1, c1.maxy, c1.maxz))
		if(c1.maxx > intersectionCuboid.maxx) smallerCuboids.add(new Cuboid(intersectionCuboid.maxx + 1, c1.miny, c1.minz, c1.maxx, c1.maxy, c1.maxz))
		if(c1.miny < intersectionCuboid.miny) smallerCuboids.add(new Cuboid(intersectionCuboid.minx, c1.miny, c1.minz, intersectionCuboid.maxx, intersectionCuboid.miny - 1, c1.maxz))
		if(c1.maxy > intersectionCuboid.maxy) smallerCuboids.add(new Cuboid(intersectionCuboid.minx, intersectionCuboid.maxy + 1, c1.minz, intersectionCuboid.maxx, c1.maxy, c1.maxz))
		if(c1.minz < intersectionCuboid.minz) smallerCuboids.add(new Cuboid(intersectionCuboid.minx, intersectionCuboid.miny, c1.minz, intersectionCuboid.maxx, intersectionCuboid.maxy, intersectionCuboid.minz - 1))
		if(c1.maxz > intersectionCuboid.maxz) smallerCuboids.add(new Cuboid(intersectionCuboid.minx, intersectionCuboid.miny, intersectionCuboid.maxz + 1, intersectionCuboid.maxx, intersectionCuboid.maxy, c1.maxz))
		smallerCuboids.each {
			//println "    $it"
		}
		smallerCuboids
	}
	
	Cuboid getIntersection(Cuboid c1, Cuboid c2) {
		//println "intersect $c1 and $c2..."
		int intersectionMinX = Math.max(c1.minx, c2.minx)
		int intersectionMaxX = Math.min(c1.maxx, c2.maxx)
		if(intersectionMaxX < intersectionMinX) return null
		int intersectionMinY = Math.max(c1.miny, c2.miny)
		int intersectionMaxY = Math.min(c1.maxy, c2.maxy)
		if(intersectionMaxY < intersectionMinY) return null
		int intersectionMinZ = Math.max(c1.minz, c2.minz)
		int intersectionMaxZ = Math.min(c1.maxz, c2.maxz)
		if(intersectionMaxZ < intersectionMinZ) return null
		
		
		Cuboid c = new Cuboid(intersectionMinX, intersectionMinY, intersectionMinZ, intersectionMaxX, intersectionMaxY, intersectionMaxZ)
		//println "    intersection: $c"
		return c
	}
	
	@Override
	def calculateResult2(fileName){
		List<String> lines = Util.readFile(fileName)
		List<Cuboid> cuboids = new ArrayList<>()
		
		lines.each { line ->
			def split = line.split(" ")
			boolean on = "on".equals(split[0])
			def xyz = split[1].split(",")
			def x = xyz[0].split("x=")[1]
			def y = xyz[1].split("y=")[1]
			def z = xyz[2].split("z=")[1]
			def minmaxx = x.split(/\.\./)
			def minmaxy = y.split(/\.\./)
			def minmaxz = z.split(/\.\./)
			
			Cuboid newCuboid = new Cuboid(minmaxx[0] as Integer, minmaxy[0] as Integer, minmaxz[0] as Integer, minmaxx[1] as Integer, minmaxy[1] as Integer, minmaxz[1] as Integer)
			List<Cuboid> newListOfCuboids = new ArrayList<>()
			//println "adding new cuboid: $newCuboid"
			if(on) {
				List<Cuboid> interSections = new ArrayList<>()
				for(Cuboid c : cuboids) {
					Cuboid intersectionCuboid = getIntersection(c, newCuboid)
					newListOfCuboids.add(c)
					if(intersectionCuboid) {
						interSections.add(intersectionCuboid)
					}
				}
				List<Cuboid> addableCuboids = new ArrayList<>()
				addableCuboids.add(newCuboid)
				for(Cuboid c : interSections) {
					List<Cuboid> newAddableCuboids = new ArrayList<>()
					addableCuboids.each {
						Cuboid intersectionCuboid = getIntersection(it, c)
						if(intersectionCuboid) newAddableCuboids.addAll(getDifference(it, intersectionCuboid))
						else newAddableCuboids.add(it)
					}
					addableCuboids = newAddableCuboids
				}
				newListOfCuboids.addAll(addableCuboids)
			}
			else {
				for(Cuboid c : cuboids) {
					Cuboid intersectionCuboid = getIntersection(c, newCuboid)
					if(intersectionCuboid) {
						newListOfCuboids.addAll(getDifference(c, intersectionCuboid))
					}
					else {
						newListOfCuboids.add(c)
					}
				}
			}
			cuboids = newListOfCuboids
		}
		
		cuboids.sum {
			it.size()
		}
	}
}
