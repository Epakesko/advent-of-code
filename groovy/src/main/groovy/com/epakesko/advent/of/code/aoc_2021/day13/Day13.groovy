package com.epakesko.advent.of.code.aoc_2021.day13;

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day
import java.awt.Point

public class Day13 extends Day{	
	@Override
	def calculateResult(fileName){
		List<String> lines = Util.readFile(fileName)
		Set<Point> dots = new HashSet<>()
		List<Map<String, Integer>> folds = new ArrayList<>()
		int i = 0
		String line = lines[i++]
		while(!"".equals(line)) {
			def splitted = line.split(",")
			dots.add(new Point(splitted[0] as Integer, splitted[1] as Integer))
			line = lines[i++]
		}
		
		for(i; i < lines.size(); i++) {
			String foldInstr = lines[i].replace("fold along ", "")
			def splitted = foldInstr.split(/[=]/)
			folds.add(new Fold(splitted[0], splitted[1] as Integer))
		}
		fold(dots, folds[0].xy, folds[0].coord).size()
	}
	
	class Fold {
		String xy
		int coord
		
		Fold(String xy, int c) {
			this.xy = xy
			this.coord = c
		}
	}
	
	Set<Point> fold(Set<Point> dots, String xy, Integer coord) {
		Set<Point> foldedDots = new HashSet<>()
		dots.each { dot ->
			if("x".equals(xy)) {
				if(coord > dot.x) foldedDots.add(dot)
				else foldedDots.add(new Point(2 * coord - (dot.x as Integer), dot.y as Integer))
			}
			else if("y".equals(xy)) {
				if(coord > dot.y) foldedDots.add(dot)
				else foldedDots.add(new Point(dot.x as Integer, 2 * coord - (dot.y as Integer)))
			}
		}
		foldedDots
	}

	@Override
	def calculateResult2(fileName){
				List<String> lines = Util.readFile(fileName)
		Set<Point> dots = new HashSet<>()
		List<Map<String, Integer>> folds = new ArrayList<>()
		int i = 0
		String line = lines[i++]
		while(!"".equals(line)) {
			def splitted = line.split(",")
			dots.add(new Point(splitted[0] as Integer, splitted[1] as Integer))
			line = lines[i++]
		}
		
		for(i; i < lines.size(); i++) {
			String foldInstr = lines[i].replace("fold along ", "")
			def splitted = foldInstr.split(/[=]/)
			folds.add(new Fold(splitted[0], splitted[1] as Integer))
		}
		folds.each { foldHere ->
			dots = fold(dots, foldHere.xy, foldHere.coord)
		}
		int maxX = 0
		int maxY = 0
		dots.each {
			if(it.x > maxX) maxX = it.x as Integer
			if(it.y > maxY) maxY = it.y as Integer
		}
		
		for(int j = 0; j <= maxY; j++) {
			for(int k = 0; k <= maxX; k++) {
				if(dots.contains(new Point(k, j))) print "#"
				else print "."
			}
			println ""
		}
	}
}
