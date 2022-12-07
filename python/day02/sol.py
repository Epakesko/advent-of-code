import sys
sys.path.append(".")
sys.path.append("..")
from util import readLines
lines = readLines(__file__)

name = "Rock Paper Scissors"

def getScorePart1(game):
    match game:
        case "A X": return 4
        case "A Y": return 8
        case "A Z": return 3
        case "B X": return 1
        case "B Y": return 5
        case "B Z": return 9
        case "C X": return 7
        case "C Y": return 2
        case "C Z": return 6

def getScorePart2(game):
    match game:
        case "A X": return 3
        case "A Y": return 4
        case "A Z": return 8
        case "B X": return 1
        case "B Y": return 5
        case "B Z": return 9
        case "C X": return 2
        case "C Y": return 6
        case "C Z": return 7

def part1():
    return sum(list(map(getScorePart1, lines)))

def part2():
    return sum(list(map(getScorePart2, lines)))

if __name__ == "__main__":
    print(part1())
    print(part2())