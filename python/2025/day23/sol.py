lines = open("input.txt", "r").read().splitlines()

connections = {}

c = 0
groups = set()
computers = set()
for line in lines:
    com1, com2 = line.split("-")
    computers.add(com1)
    computers.add(com2)
    connections.setdefault(com1, set()).add(com2)
    connections.setdefault(com2, set()).add(com1)

    have_t = com1[0] == "t" or com2[0] == "t"

    for conn in connections[com1]:
        if conn in connections[com2]:
            groups.add(",".join(sorted({com1, com2, conn})))

        if (have_t or conn[0] == "t") and conn in connections[com2]:
            c += 1

while (True):
    new_groups = set()
    for group in groups:
        group_computers = set(group.split(","))
        for computer in computers:
            if computer in group_computers:
                continue

            if all(group_computer in connections[computer] for group_computer in group_computers):
                group_computers.add(computer)
                new_groups.add(",".join(sorted(group_computers)))

    if not new_groups:
        break

    groups = new_groups

print(groups)
