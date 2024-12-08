import itertools
import time

lines = open("input.txt", "r").read().splitlines()

height = len(lines)
width = len(lines[0])
obstacles = []
guard = None

for y in range(height):
    positions = lines[y]
    for x in range(width): 
        position = positions[x]
        if position == "#":
            obstacles.append((x, y))
        if position == "^":
            guard = (x, y, 0, -1)

def move(guard):
    return (guard[0] + guard[2], guard[1] + guard[3], guard[2], guard[3])

def rotate(guard):
    return (guard[0], guard[1], -guard[3], guard[2])

def solve1(guard):
    visited = []
    while(True):
        if guard[0] == -1 or guard[0] == width or guard[1] == -1 or guard[1] == height:
            break
        visited.append((guard[0], guard[1]))
        while True:
            new_position = move(guard)
            if (new_position[0], new_position[1]) in obstacles:
                guard = rotate(guard)
            else:
                guard = new_position
                break
    return len(set(visited))

start = time.time_ns()
print(solve1(guard))
print("1st part: ", (time.time_ns() - start)/1000000, "ms")

def solve2(start):
    possible_new_obstacles = list(set(itertools.product(range(width), range(height))) - set(obstacles))
    good = 0
    c = 0
    for new_obstacle in possible_new_obstacles:
        print(c)
        c += 1
        static_obstacles = obstacles.copy()
        static_obstacles.append(new_obstacle)
        visited = []
        guard = start 
        while(True):
            if guard[0] == -1 or guard[0] == width or guard[1] == -1 or guard[1] == height:
                break
            if guard in visited:
                good += 1
                break
            visited.append(guard)
            while True:
                new_position = move(guard)
                if (new_position[0], new_position[1]) in static_obstacles:
                    guard = rotate(guard)
                else:
                    guard = new_position
                    break
    return good

print(solve2(guard))