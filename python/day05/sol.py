from util import readLines
import sys
import re
import copy
sys.path.append(".")
sys.path.append("..")
lines = readLines(__file__)

name = "Supply Stacks"

startingstacks = [
    ["R", "H", "M", "P", "Z"],
    ["B", "J", "C", "P"],
    ["D", "C", "L", "G", "H", "N", "S"],
    ["L", "R", "S", "Q", "D", "M", "T", "F"],
    ["M", "Z", "T", "B", "Q", "P", "S", "F"],
    ["G", "B", "Z", "S", "F", "T"],
    ["V", "R", "N"],
    ["M", "C", "V", "D", "T", "L", "G", "P"],
    ["L", "M", "F", "J", "N", "Q", "W"]
]

regex = "move (\d+) from (\d) to (\d)"


def part1():
    stacks = copy.deepcopy(startingstacks)
    for line in lines:
        res = re.search(regex, line)
        for _ in range(int(res.group(1))):
            stacks[int(res.group(3)) - 1].insert(0, stacks[int(res.group(2)) - 1].pop(0))

    return "".join(map(lambda stack: stack[0], stacks))


def part2():
    stacks = copy.deepcopy(startingstacks)
    for line in lines:
        res = re.search(regex, line)
        temp = []
        for _ in range(int(res.group(1))):
            temp.append(stacks[int(res.group(2)) - 1].pop(0))
        for _ in range(int(res.group(1))):
            stacks[int(res.group(3)) - 1].insert(0, temp.pop())

    return "".join(map(lambda stack: stack[0], stacks))


if __name__ == "__main__":
    print(part1())
    print(part2())
