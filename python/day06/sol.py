from util import readLines
import sys
sys.path.append(".")
sys.path.append("..")
lines = readLines(__file__)

name = "Tuning Trouble"


def part1():
    line = lines[0]
    for n in range(0, len(line)):
        if len(set(line[n:n+4])) == 4:
            return n+4
    return -1


def part2():
    line = lines[0]
    for n in range(0, len(line)):
        if len(set(line[n:n+14])) == 14:
            return n+14
    return -1


if __name__ == "__main__":
    print(part1())
    print(part2())
