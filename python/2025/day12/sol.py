
lines = open("input.txt", "r").read().splitlines()

height = len(lines)
width = len(lines[0])
visited = set()

def get_neighbors(x, y):
    return [(1, 0), (-1, 0), (0, 1), (0, -1)]

def get_section(x, y, neighborEdges):
    name = lines[y][x]
    visited.add((x, y))
    perimeter = 0
    area = 1
    edge = 0
    edges = set()
    for neighbor in get_neighbors(x, y):
        xn = x + neighbor[0]
        yn = y + neighbor[1]
        if xn < 0 or xn >= width or yn < 0 or yn >= height or lines[yn][xn] != name:
            perimeter += 1
            edges.add((neighbor[0], neighbor[1]))
            if(neighbor not in neighborEdges):
                edge += 1
            
    print(x, y, name, area, perimeter, edge)

    for neighbor in get_neighbors(x, y):
        xn = x + neighbor[0]
        yn = y + neighbor[1]
        if xn >= 0 and xn < width and yn >= 0 and yn < height and lines[yn][xn] == name and (xn, yn) not in visited:
            (neighborArea, neigborPerimeter, neighborEdge) = get_section(xn, yn, edges)
            print("neighbor", xn, yn, neighborArea, neigborPerimeter, neighborEdge)
            area += neighborArea
            perimeter += neigborPerimeter
            edge += neighborEdge

            
    print(x, y, name, area, perimeter, edge)

    return (area, perimeter, edge)

s = 0
s2 = 0
for y in range(height):
    for x in range(width):
        if (x, y) in visited:
            continue
        area, perimeter, edge = get_section(x, y, set())
        print(x, y, area, perimeter, edge)
        s += area * perimeter
        s2 += area * edge

print(s)
print(s2)
