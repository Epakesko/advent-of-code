import itertools
import math

lines = open("input.txt", "r").read().splitlines()

dict = {}

height = len(lines)
width = len(lines[0])

for y in range(height):
    for x in range(width):
        position = (x, y)
        current = lines[y][x]
        if current == ".":
            continue
        dict.setdefault(current, [])
        dict[current].append(position)


def find_antinodes(node, dx, dy):
    x = node[0]
    y = node[1]
    while x in range(width) and y in range(height):
        x += dx
        y += dy
        if x in range(width) and y in range(height):
            antinodes2.append((x, y))
    x = node[0]
    y = node[1]
    while x in range(width) and y in range(height):
        x -= dx
        y -= dy
        if x in range(width) and y in range(height):
            antinodes2.append((x, y))


antinodes = []
antinodes2 = []
for key in dict:
    combinations = list(itertools.combinations(dict[key], 2))
    for combination in combinations:
        xdiff = combination[0][0] - combination[1][0]
        ydiff = combination[0][1] - combination[1][1]
        if combination[0][0] + xdiff in range(width) and combination[0][1] + ydiff in range(height):
            antinodes.append((combination[0][0] + xdiff, combination[0][1] + ydiff))
        if combination[1][0] - xdiff in range(width) and combination[1][1] - ydiff in range(height):
            antinodes.append((combination[1][0] - xdiff, combination[1][1] - ydiff))

        gcd = math.gcd(xdiff, ydiff)
        dx = xdiff // gcd
        dy = ydiff // gcd
        find_antinodes(combination[0], dx, dy)
        find_antinodes(combination[1], dx, dy)


print(len(set(antinodes)))
print(len(set(antinodes2)))
