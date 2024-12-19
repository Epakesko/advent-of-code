lines = open("input.txt", "r").read().splitlines()

towels = set()
towels.update(lines[0].split(", "))

cache = {}

def solve(flag):
    solutions = 0
    if flag in cache:
        return cache[flag]
    for towel in towels:
        if towel == flag:
            solutions += 1
        if flag.startswith(towel):
            solutions += solve(flag[len(towel):])
    cache[flag] = solutions
    return solutions

c = 0
s = 0
for i in range(2, len(lines)):
    solutions = solve(lines[i])
    if solutions > 0:
        c += 1
        s += solutions

print(c)
print(s)