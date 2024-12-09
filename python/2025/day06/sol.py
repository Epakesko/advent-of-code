import itertools
import time
from multiprocessing import Pool

lines = open("input.txt", "r").read().splitlines()

height = len(lines)
width = len(lines[0])
obstacles = set()
visited = set()
guard = None

for y in range(height):
    positions = lines[y]
    for x in range(width):
        position = positions[x]
        if position == "#":
            obstacles.add((x, y))
        if position == "^":
            guard = (x, y, 0, -1)


def move(guard):
    return (guard[0] + guard[2], guard[1] + guard[3], guard[2], guard[3])


def rotate(guard):
    return (guard[0], guard[1], -guard[3], guard[2])


def solve1(guard):
    while (True):
        if guard[0] == -1 or guard[0] == width or guard[1] == -1 or guard[1] == height:
            break
        visited.add((guard[0], guard[1]))
        new_position = move(guard)
        if (new_position[0], new_position[1]) in obstacles:
            guard = rotate(guard)
        else:
            guard = new_position
    return len(set(visited))


start = time.time_ns()
print(solve1(guard))
print("1st part: ", (time.time_ns() - start)/1000000, "ms")


def solve2(start, originally_visited):
    possible_new_obstacles = set(map(lambda x: (x[0], x[1]), originally_visited))
    good = 0
    for new_obstacle in possible_new_obstacles:
        visited = set()
        guard = start
        while (True):
            if guard[0] == -1 or guard[0] == width or guard[1] == -1 or guard[1] == height:
                break
            if guard in visited:
                good += 1
                break
            visited.add(guard)
            while True:
                new_position = move(guard)
                if (new_position[0], new_position[1]) in obstacles or (new_position[0], new_position[1]) == new_obstacle:
                    guard = rotate(guard)
                else:
                    guard = new_position
                    break
    return good


start = time.time_ns()
print(solve2(guard, visited))
print("2nd part: ", (time.time_ns() - start)/1000000, "ms")
