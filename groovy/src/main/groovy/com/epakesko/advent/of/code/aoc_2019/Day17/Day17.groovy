package com.epakesko.advent.of.code.aoc_2019.Day17

import com.epakesko.advent.of.code.aoc_2019.util.IntCode
import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day
import java.awt.Point

class Day17 extends Day {
	
	@Override
	public Object calculateResult(Object fileName) {
		IntCode intCode = new IntCode(fileName)
		
		int x, y
		int sum = 0
		Map<Point, Character> points = new HashMap<>()
		intCode.run().split(", ").collect { it as Integer }.each { num -> 
			char c = (char)num
			if(c == '\n') {
			y++
				x = 0
			}
			else if(c == '#') {
				points.put(new Point(x, y), '#')
				if(points.get(new Point(x, y-1)) == '#' &&
					points.get(new Point(x, y-2)) == '#' &&
					points.get(new Point(x-1, y-1)) == '#' &&
					points.get(new Point(x+1, y-1)) == '#') sum += x * (y-1)
			 	x++
			}
			else {
				points.put(new Point(x++, y), c)
			}
		}
		sum
	}
	
	@Override
	public Object calculateResult2(Object fileName) {
		IntCode intCode = new IntCode(fileName)
		
		int x, y
		Point robot
		Point dir = new Point(0, -1)
		Map<Point, Character> points = new HashMap<>()
		intCode.run().split(", ").collect { it as Integer }.each { num -> 
			char c = (char)num
			if(c == '\n') {
				y++
				x = 0
			}
			else if(c == '^') {
				robot = new Point(x++, y)
				points.put(robot, c)
			}
			else {
				points.put(new Point(x++, y), c)
			}
		}
		
		List steps = []
		int stepLength = 0
		while(true) {
			if(points.get(new Point((robot.x + dir.x) as Integer, (robot.y + dir.y) as Integer)) == '#') {
				stepLength++
				robot = new Point((robot.x + dir.x) as Integer, (robot.y + dir.y) as Integer)
			}
			else {
				Point newDir = dir.x == 0 ? new Point(-dir.y as Integer, 0) : new Point(0, dir.x as Integer)
				if(points.get(new Point((robot.x + newDir.x) as Integer, (robot.y + newDir.y) as Integer)) == '#') {
					if(stepLength != 0) steps << stepLength
					stepLength = 0
					steps << "R"
					dir = newDir
					continue
				}
				
				newDir = dir.x == 0 ? new Point(dir.y as Integer, 0) : new Point(0, -dir.x as Integer)
				if(points.get(new Point((robot.x + newDir.x) as Integer, (robot.y + newDir.y) as Integer)) == '#') {
					if(stepLength != 0) steps << stepLength
					stepLength = 0
					steps << "L"
					dir = newDir
					continue
				}
				if(stepLength != 0) steps << stepLength
				break
			}
			
			
		}
		String stepsString = steps.join("")
		String a = stepsString[0..8]
		stepsString = stepsString.replace(a, "A")
		String stepStringWithout = stepsString.replace("A", "")
		String b = stepStringWithout[0..6]
		stepsString = stepsString.replace(b, "B")
		String c = stepStringWithout.replace(b, "")[0..9]
		stepsString = stepsString.replace(c, "C")
		
		println "$a $b $c $stepsString"
		
		IntCode intCode2 = new IntCode(fileName)
		
		intCode2.memory.put(0L, 2)
		
		intCode2.runUntilInput()
		stepsString.split("").join(",").each { 
			intCode2.addInput((it as Character) as Integer)
			intCode2.continueUntilInput()
		}
		intCode2.addInput(10)
		println intCode2.continueUntilInput().split(", ").collect{ (it as Integer) as Character}.join("")
		a.split("").join(",").each {
			int num = (it as Character) as Integer
			if(it == "1") num += 7
			if(it == "2") num += 2
			intCode2.addInput(num)
			intCode2.continueUntilInput()
		}
		intCode2.addInput(10)
		println intCode2.continueUntilInput().split(", ").collect{ (it as Integer) as Character}.join("")
		b.split("").join(",").each {
			int num = (it as Character) as Integer
			if(it == "1") num += 7
			if(it == "2") num += 2
			intCode2.addInput(num)
			intCode2.continueUntilInput()
		}
		intCode2.addInput(10)
		println intCode2.continueUntilInput().split(", ").collect{ (it as Integer) as Character}.join("")
		c.split("").join(",").each {
			int num = (it as Character) as Integer
			if(it == "1") num += 7
			if(it == "0") num += 2
			intCode2.addInput(num)
			intCode2.continueUntilInput()
		}
		intCode2.addInput(10)
		println intCode2.continueUntilInput().split(", ").collect{ (it as Integer) as Character}.join("")
		intCode2.addInput(('y' as Character) as Integer)
		intCode2.continueUntilInput()
		intCode2.addInput(10)
		println intCode2.continueUntilInput()
		-1
	}
}
