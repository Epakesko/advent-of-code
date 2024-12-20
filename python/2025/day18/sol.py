lines = open("input.txt", "r").read().splitlines()

obstacles = set()

first_x = 1024
width = 71
height = 71

for i in range(first_x):
    x, y = lines[i].split(",")
    obstacles.add((int(x), int(y)))

queue = []
distance = {(0, 0): 0}


def get_neighbours(x, y):
    neighbours = []
    possible_neighbours = [(x + 1, y), (x - 1, y), (x, y + 1), (x, y - 1)]
    for possible_neighbour in possible_neighbours:
        new_x = possible_neighbour[0]
        new_y = possible_neighbour[1]
        if new_x < 0 or new_x >= width or new_y < 0 or new_y >= height or (new_x, new_y) in obstacles:
            continue
        neighbours.append((new_x, new_y))
    return neighbours


def bfs(node):
    queue.append(node)
    while queue:
        m = queue.pop(0)

        for neighbour in get_neighbours(m[0], m[1]):
            if neighbour not in distance or distance[neighbour] > distance[m] + 1:
                distance[neighbour] = distance[m] + 1
                queue.append(neighbour)
                if neighbour == (70, 70):
                    return True
    return False


bfs((0, 0))

print(distance[(70, 70)])

for i in range(first_x, len(lines)):
    x, y = lines[i].split(",")
    obstacles.add((int(x), int(y)))
    queue = []
    distance = {(0, 0): 0}
    if not bfs((0, 0)):
        print(lines[i])
        break
