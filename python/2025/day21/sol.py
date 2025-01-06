lines = list(map(lambda x: "A" + x, open("input.txt", "r").read().splitlines()))

dpmap = {
    "A<": "Av<<A",  "A>": "AvA",    "A^": "A<A",    "Av": "A<vA",  
    "^<": "Av<A",   "^>": "Av>A",   "^A": "A>A",
    "v<": "A<A",    "v>": "A>A",    "vA": "A^>A",
    "<^": "A>^A",   "<A": "A>>^A",  "<v": "A>A", 
    ">v": "A<A",    ">A": "A^A",    ">^": "A<^A"
}

dp = {
    "A<": 1, "A>": 1, "A^": 1, "Av": 1,
    "^<": 1, "^>": 1, "^A": 1,
    "v<": 1, "v>": 1, "vA": 1,
    "<^": 1, "<A": 1, "<v": 1,
    ">v": 1, ">A": 1, ">^": 1
}

dptest = {
    "A0": "A<A",    "A1": "A^<<A",  "A3": "A^A",    "A4": "A^^<<A", "A7": "A^^^<<A",    "A8": "A<^^^A", "A9": "A^^^A",
    "0A": "A>A",    "02": "A^A",    "08": "A^^^A",
    "1A": "A>>vA",  "17": "A^^A",
    "2A": "Av>A",   "29": "A^^>A",  
    "3A": "AvA",    "34": "A<<^A",  "37": "A<<^^A",
    "41": "AvA",    "45": "A>A",
    "56": "A>A",
    "6A": "AvvA",
    "73": "Avv>>A", "78": "A>A",   "79": "A>>A",
    "80": "AvvvA",  "83": "Avv>A",
    "9A": "AvvvA",  "97": "A<<A",   "98": "A<A",
}

for i in range(1, 26):
    newdp = {}
    for j in dp:
        steps = dpmap[j]
        newdp[j] = sum(dp.get(steps[step:step + 2], 1) for step in range(len(steps) - 1))
    dp = newdp

bigs = 0
for line in lines:
    s = 0
    for i in range(4):
        steps = dptest.get(line[i:i + 2])
        s += sum(dp.get(steps[step:step + 2], 1) for step in range(len(steps) - 1))
    bigs += s * int(line[1:-1])

print(bigs)
