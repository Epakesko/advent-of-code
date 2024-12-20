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
road2 = [start]

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
while(current != end):
    x, y = current
    next = get_next_step(prev, current)
    prev = current
    current = next
    road[current] = i
    road2.append(current)
    i += 1

def cheat(asd, to_):
    x1 = asd[0]
    y1 = asd[1]
    x2, y2 = to_
    steps_to_cheat = abs(x1 - x2) + abs(y1 - y2)
    if steps_to_cheat < 51:
        return steps_to_cheat
    return 0

max_cheat = 50
min_diff = 100
cheats = []
c = 0
print(i)
for step in road:
    for xd in range(-max_cheat, max_cheat + 1):
        for yd in range(-(max_cheat - abs(xd) + 1), max_cheat - abs(xd) + 1):
            x = step[0] + xd
            y = step[1] + yd
            if (x, y) in road and road[(x, y)] - road[step] - abs(xd) - abs(yd) >= min_diff:
                c += 1
print(c)

# for i in range(0, len(road2) - min_diff - 1):
#     for j in range(i + min_diff, len(road2)):
#         cheating_length = cheat(road2[i], road2[j])
        #if cheating_length > 0 and j - i - cheating_length >= min_diff:
         #    cheats.add((i, j))

# lengt = len(cheats)
# print(lengt)
# print("validating...")
# dic = {}
# for cheat in cheats:
#     i, j = cheat
#     a = road[i]
#     b = road[j]
#     steps_to_cheat = abs(a[0] - b[0]) + abs(a[1] - b[1])
#     dic.setdefault(j - i - steps_to_cheat, 0)
#     dic[j - i - steps_to_cheat] += 1
#     if i >= j or steps_to_cheat > 50 or j - i - steps_to_cheat < min_diff:
#         print("invalid", i, j, steps_to_cheat)

# print(cheats[-100:])

