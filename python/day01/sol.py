import sys
sys.path.append(".")
sys.path.append("..")
from util import readLines
lines = readLines(__file__)

name = "Calorie Counting"

def part1():
    cal = 0
    max = 0
    for line in lines: 
        if line.strip():
            cal += int(line)
        else:
            if cal > max:
                max = cal
            cal = 0
    return max

def part2():
    cals = []
    cal = 0
    for line in lines: 
        if line.strip():
            cal += int(line)
        else:
            cals.append(cal)
            cal = 0
    cals.sort()
    return sum(cals[-3:])

if __name__ == "__main__":
    print(part1())
    print(part2())