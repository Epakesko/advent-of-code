lines = open("input.txt", "r").read().splitlines()


def prune(a, b):
    return (a ^ b) % 16777216


def next(a):
    a = prune(a, a * 64)
    a = prune(a, a // 32)
    a = prune(a, a * 2048)
    return a


s = 0
sequences = []
i = 0
for line in lines:
    last_changes = []
    buys = {}
    num = int(line)
    for _ in range(2000):
        prev = num
        num = next(num)
        bananas = num % 10
        diff = bananas - prev % 10
        last_changes.append(diff)
        if len(last_changes) == 5:
            last_changes.pop(0)
            last_changes_string = ":".join(str(x) for x in last_changes)
            if last_changes_string not in buys:
                buys[last_changes_string] = bananas
    sequences.append(buys)
    s += num
print(s)

sequencesums = {}
for sequence in sequences:
    for buy in sequence:
        sequencesums.setdefault(buy, 0)
        sequencesums[buy] += sequence[buy]

print(sequencesums[max(sequencesums, key=sequencesums.get)])
