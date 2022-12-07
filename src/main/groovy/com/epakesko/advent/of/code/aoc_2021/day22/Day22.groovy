package com.epakesko.advent.of.code.aoc_2021.day22;

import com.epakesko.advent.of.code.aoc_2019.Day18.Day18.Node
import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

import static org.junit.jupiter.api.Assertions.assertLinesMatch

import java.awt.Point

public class Day22 extends Day{	
	
	@Override
	def calculateResult(fileName){
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
		int minx
		int miny
		int minz
		int maxx
		int maxy
		int maxz
		boolean on
		
		Cuboid(minx, miny, minz, maxx, maxy, maxz, on) {
			this.minx = minx
			this.miny = miny
			this.minz = minz
			this.maxx = maxx
			this.maxy = maxy
			this.maxz = maxz
			this.on = on
		}
		
		boolean isInside(int x, int y, int z) {
			x >= minx && x <= maxx && y >= miny && y <= maxy && z >= minz && z <= maxz
		}
	}
	
	@Override
	def calculateResult2(fileName){
		List<String> lines = Util.readFile(fileName)
		-1
	}
}
