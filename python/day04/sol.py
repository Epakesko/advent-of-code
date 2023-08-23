from util import readLines
import sys
import re
sys.path.append(".")
sys.path.append("..")
lines = readLines(__file__)

name = "Camp Cleanup"


def part1():
    return sum(
        map(
            lambda regex: 1 if
            (int(regex.group(1)) - int(regex.group(3))) * (int(regex.group(2)) - int(regex.group(4))) <= 0
            else 0,
            map(
                lambda line: re.search("(\d+)-(\d+),(\d+)-(\d+)", line),
                lines
            )
        )
    )


def part2():
    return sum(
        map(
            lambda regex: 1 if
            not ((int(regex.group(1)) > int(regex.group(4))) or (int(regex.group(2)) < int(regex.group(3))))
            else 0,
            map(
                lambda line: re.search("(\d+)-(\d+),(\d+)-(\d+)", line),
                lines
            )
        )
    )


if __name__ == "__main__":
    print(part1())
    print(part2())
