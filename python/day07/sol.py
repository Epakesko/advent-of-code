from util import readLines
import sys
import re
sys.path.append(".")
sys.path.append("..")
lines = readLines(__file__)

name = "No Space Left On Device"
cdRegex = "\$ cd (.+)"
lsRegex = "\$ ls"
fileRegex = "(\d+) (.+)"


def part1():
    folderSizes = {}
    folderStack = []
    for line in lines:
        match = re.fullmatch(cdRegex, line)
        if match:
            dir = match.group(1)
            if dir == "..":
                folderStack.pop()
            else:
                folderStack.append(dir)
                folderSizes["-".join(folderStack)] = 0
        match = re.fullmatch(fileRegex, line)
        if match:
            fullstack = ""
            for folder in folderStack:
                fullstack += folder if fullstack == "" else "-" + folder
                folderSizes[fullstack] += int(match.group(1))
    return sum(map(lambda folderSize: 0 if folderSize > 100000 else folderSize, folderSizes.values()))


def part2():
    folderSizes = {}
    folderStack = []
    for line in lines:
        match = re.fullmatch(cdRegex, line)
        if match:
            dir = match.group(1)
            if dir == "..":
                folderStack.pop()
            else:
                folderStack.append(dir)
                folderSizes["-".join(folderStack)] = 0
        match = re.fullmatch(fileRegex, line)
        if match:
            fullstack = ""
            for folder in folderStack:
                fullstack += folder if fullstack == "" else "-" + folder
                folderSizes[fullstack] += int(match.group(1))

    requiredSize = folderSizes["/"] - 40000000
    items = [item for item in folderSizes.items() if item[1] > requiredSize]
    return min(items, key=lambda tup: tup[1])[1]


if __name__ == "__main__":
    print(part1())
    print(part2())
