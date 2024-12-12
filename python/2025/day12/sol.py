
lines = open("input.txt", "r").read().splitlines()

height = len(lines)
width = len(lines[0])
visited = set()


neighbors = [(1, 0), (-1, 0), (0, 1), (0, -1)]


def get_section(x, y):
    name = lines[y][x]
    visited.add((x, y))
    perimeter = 0
    area = 1
    for neighbor in neighbors:
        xn = x + neighbor[0]
        yn = y + neighbor[1]
        if xn < 0 or xn >= width or yn < 0 or yn >= height or lines[yn][xn] != name:
            perimeter += 1
        elif (xn, yn) not in visited:
            (neighbor_area, neigbor_perimeter) = get_section(xn, yn)
            area += neighbor_area
            perimeter += neigbor_perimeter

    return (area, perimeter)


s = 0
for y in range(height):
    for x in range(width):
        if (x, y) in visited:
            continue
        area, perimeter = get_section(x, y)
        s += area * perimeter
print(s)
