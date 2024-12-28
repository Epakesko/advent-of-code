lines = list(map(lambda x: "A" + x, open("input.txt", "r").read().splitlines()))


dpmap = {
    "A<": [["Av", "v<", "<<", "<A"]],  # 0. A -> <: v<<A
    "A>": [["Av", "vA"]],  # 1. A -> >: vA
    "A^": [["A<", "<A"]],  # 2. A -> ^: <A
    "Av": [["A<", "<v", "vA"], ["Av", "v<", "<A"]],  # 3. A -> v: <vA
    "^<": [["Av", "v<", "<A"]],  # 4. ^ -> <: v<A
    "^>": [["A>", ">v", "vA"], ["Av", "v>", ">A"]],  # 5. ^ -> > : v>A
    "^A": [["A>", ">A"]],  # 6. ^ -> A: >A
    "v<": [["A<", "<A"]],  # 7. v -> <: <A
    "v>": [["A>", ">A"]],  # 8. v -> >: >A
    "vA": [["A>", ">^", "^A"], ["A^", "^>", ">A"]],  # 9. v -> A: >^A
    "<^": [["A>", ">^", "^A"]],  # 10. < -> ^: >^A
    "<A": [["A>", ">>", ">^", "^A"]],  # 11. < -> A: >>^A
    ">v": [["A<", "<A"]],  # 12. > -> v: <A
    ">A": [["A^", "^A"]],  # 13. > -> A: ^A
    ">^": [["A^", "^<", "<A"], ["A<", "<^", "^A"]],  # 14. > -> ^: <^A
    "<v": [["A>", ">A"]],  # 15. < -> v: >A
}

dp = {
    "A<": 1,  # 0. A -> <: v<<A
    "A>": 1,  # 1. A -> >: vA
    "A^": 1,  # 2. A -> ^: <A
    "Av": 1,  # 3. A -> v: v<A
    "^<": 1,  # 4. ^ -> <: v<A
    "^>": 1,  # 5. ^ -> > : v>A
    "^A": 1,  # 6. ^ -> A: >A
    "v<": 1,  # 7. v -> <: <A
    "v>": 1,  # 8. v -> >: >A
    "vA": 1,  # 9. v -> A: >^A
    "<^": 1,  # 10. < -> ^: >^A
    "<A": 1,  # 11. < -> A: >>^A
    ">v": 1,  # 12. > -> v: <A
    ">A": 1,  # 13. > -> A: ^A
    ">^": 1,  # 14. > -> ^: <^A
    "<v": 1,  # 15. < -> v: >A
}

dptest = {
    "A0": ["A<A"],  # <A
    "02": ["A^A"],  # ^A
    "29": ["A^^>A", "A>^^A"],  # ^^>A
    "9A": ["AvvvA"],  # vvvA
    "A9": ["A^^^A"],  # ^^^A
    "98": ["A<A"],  # <A
    "80": ["AvvvA"],  # vvvA
    "0A": ["A>A"],  # >A
    "A1": ["A^<<A"],  # ^<<A
    "17": ["A^^A"],  # ^^A
    "79": ["A>>A"],  # >>A
    "A4": ["A^^<<A"],  # ^^<<A
    "45": ["A>A"],  # >A
    "56": ["A>A"],  # >A
    "6A": ["AvvA"],  # vvA
    "A3": ["A^A"],  # ^A
    "37": ["A<<^^A", "A^^<<A"],  # ^^<<A
    "34": ["A<<^A", "A^<<A"],  # <<^A
    "41": ["AvA"],  # vA
    "1A": ["A>>vA"],  # >>v
    "08": ["A^^^A"],  # ^^^A
    "83": ["Avv>A", "A>vvA"],  # vv>A
    "3A": ["AvA"],  # vA
    "A8": ["A<^^^A", "A^^^<A"],  # <^^^A
    "2A": ["Av>A", "A>vA"],  # v>A
    "97": ["A<<A"],  # <<A
    "73": ["Avv>>A", "A>>vvA"],  # >>vvA
    "A7": ["A^^^<<A"],  # ^^^<<A
    "78": ["A>A"],  # >A
}


# 029A: < A ^ A ^ ^ > AvvvA
# 980A: ^ ^ ^ A < AvvvA > A
# 179A: ^ <<A ^ ^A >> AvvvA
# 456A: ^ ^ << A > A > AvvA
# 379A: ^ A ^ ^ << A >> AvvA
for i in range(1, 26):
    newdp = {}
    for j in dp:  # A<, A>....
        newdp[j] = 100000000000000000000000000
        for dpmapelem in dpmap[j]:  # [["A<", "<v", "vA"], ["Av", "v<", "<A"]],
            s = sum(dp.get(k) if k in dp else 1 for k in dpmapelem)
            if s < newdp[j]:
                newdp[j] = s
    dp = newdp

bigs = 0
for line in lines:
    s = 0
    for i in range(4):
        test = line[i:i + 2]
        stepslist = dptest.get(test)
        best = 100000000000000000000000000
        for steps in stepslist:
            stepsum = 0
            for j in range(len(steps) - 1):
                k = steps[j:j + 2]
                stepsum += dp.get(k, 1)
            if stepsum < best:
                best = stepsum
        s += best
    bigs += s * int(line[1:-1])

print(bigs)
