package aoc2020;

abstract class Day {
	void run(runType, dayNumber){
		if(runType == "test") {
			test(dayNumber)
		}
		else if(runType == "test2") {
			test2(dayNumber)
		}
		else if(runType == "run"){
			run(dayNumber)
		}
		else if(runType == "run2"){
			run2(dayNumber)
		}
	}

	void test(dayNumber) {
		println calculateResult("src/aoc2020/${dayNumber}/testinput.txt")
	}

	void test2(dayNumber) {
		println calculateResult2("src/aoc2020/${dayNumber}/testinput.txt")
	}
	
	void run(dayNumber) {
		println calculateResult("src/aoc2020/${dayNumber}/input.txt")
	}
	
	void run2(dayNumber) {
		println calculateResult2("src/aoc2020/${dayNumber}/input.txt")
	}
	
	abstract def calculateResult(fileName);
	abstract def calculateResult2(fileName);
}