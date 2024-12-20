import time
lines = open("input.txt", "r").read().splitlines()

start = None
end = None

height = len(lines)
width = len(lines[0])

for y, line in enumerate(lines):
    for x, char in enumerate(line):
        if char == "S":
            start = (x, y)
        if char == "E":
            end = (x, y)
        if start and end:
            break

road = {start: 0}


def get_next_step(prev, current):
    x, y = current
    possible_neighbors = [(x - 1, y), (x + 1, y), (x, y - 1), (x, y + 1)]
    for neighbor in possible_neighbors:
        xn, yn = neighbor
        if neighbor != prev and xn >= 0 and xn < width and yn >= 0 and yn < height and (lines[yn][xn] == "." or neighbor == end):
            return neighbor


prev = None
current = start
i = 1
while (current != end):
    x, y = current
    next = get_next_step(prev, current)
    prev = current
    current = next
    road[current] = i
    i += 1

max_cheat = 20
min_diff = 100
c = 0
print(i)
for step in road:
    for xd in range(max_cheat + 1):
        for yd in range(max_cheat - xd + 1):
            options = {(xd, yd), (-xd, yd), (xd, -yd), (-xd, -yd)}
            for option in options:
                pos = (step[0] + option[0], step[1] + option[1])
                if pos in road and road[pos] - road[step] - xd - yd >= min_diff:
                    c += 1

print(c)
