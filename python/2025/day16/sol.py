import sys
lines = open("input.txt", "r").read().splitlines()
sys.setrecursionlimit(10000)
reindeer = None
exit = None
walls = set()
spaces = set()
visited_by_cost = {}

for y, line in enumerate(lines):
    for x, char in enumerate(line):
        if char == "S":
            reindeer = (x, y)
            visited_by_cost[reindeer] = {0: [{reindeer}]}
            spaces.add((x, y))
        elif char == "E":
            exit = (x, y)
            spaces.add((x, y))
        elif char == "#":
            walls.add((x, y))
        elif char == ".":
            spaces.add((x, y))

def get_neighbors(node, dir, visited):
    neighbors = []
    possible_neighbors = [(-1, 0), (1, 0), (0, -1), (0, 1)]
    for possible_neighbor in possible_neighbors:
        if possible_neighbor == (-dir[0], -dir[1]):
            continue
        neighbor = (node[0] + possible_neighbor[0], node[1] + possible_neighbor[1])
        if neighbor in walls or neighbor in visited or neighbor[0] < 0 or neighbor[1] < 0 or neighbor[0] >= len(lines[0]) or neighbor[1] >= len(lines):
            continue
        if neighbor in spaces:
            cost = 1 if possible_neighbor == dir else 1001
            neighbors.append((neighbor, cost))
    return sorted(neighbors, key=lambda x: x[1])

def dfs(node, dir, visited, cost):
    neighbors = get_neighbors(node, dir, visited)
    for neighbor in neighbors:
        new_cost = cost + neighbor[1]
        new_visited = visited.union({neighbor[0]})
        best_cost = min(visited_by_cost.get(neighbor[0], {len(lines) * len(lines[0]) * 100: 0}).keys())
        best_cost_exit = min(visited_by_cost.get(exit, {len(lines) * len(lines[0]) * 100: 0}).keys())
        if(new_cost > best_cost + 1000 or new_cost > best_cost_exit or new_cost > 147628):
            continue
        visited_by_cost.setdefault(neighbor[0], {})
        visited_by_cost[neighbor[0]].setdefault(new_cost, set())
        visited_by_cost[neighbor[0]][new_cost].update(new_visited)
        print(len(visited), best_cost_exit, best_cost, new_cost, len(visited_by_cost.get(exit, {}).get(best_cost_exit, set())))
        dfs(neighbor[0], (neighbor[0][0] - node[0], neighbor[0][1] - node[1]), new_visited, new_cost)

dfs(reindeer, (1, 0), {reindeer}, 0)
shortest_path_length = min(visited_by_cost[exit].keys())
print(shortest_path_length)
print(len(visited_by_cost[exit][shortest_path_length]))