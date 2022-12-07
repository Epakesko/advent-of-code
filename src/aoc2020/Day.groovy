package aoc2020;

abstract class Day {
	void run(runType, dayNumber){
		if(runType == "test") {
			test()
		}
		else if(runType == "run"){
			run(dayNumber)
		}
		else if(runType == "run2"){
			run2(dayNumber)
		}
	}

	void test() {}
	
	void run(dayNumber) {
		println calculateResult("aoc2020/${dayNumber}/input.txt")
	}
	
	void run2(dayNumber) {
		println calculateResult2("aoc2020/${dayNumber}/input.txt")
	}
	
	def calculateResult(fileName) {}
	def calculateResult2(fileName) {}
}