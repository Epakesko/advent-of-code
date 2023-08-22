import sys
sys.path.append(".")
sys.path.append("..")
from util import readLines
lines = readLines(__file__)

name = "Rucksack Reorganization"

def getValue(char):
    return ord(char) - (38 if char.isupper() else 96)

def part1():
    count = 0
    for line in lines:
        chars = list(set(line[0:len(line)//2]) & set(line[len(line)//2:]))
        for char in chars:
            count += getValue(char)
    return count

def part2():
    count = 0
    for triple in [lines[n:n+3] for n in range(0, len(lines), 3)]:
        chars = list(set(triple[0]) & set(triple[1]) & set(triple[2]))
        for char in chars:
            count += getValue(char)
    return count

if __name__ == "__main__":
    print(part1())
    print(part2())