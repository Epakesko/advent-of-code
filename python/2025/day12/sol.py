
lines = open("input.txt", "r").read().splitlines()

height = len(lines)
width = len(lines[0])
visited = set()


neighbors = [(1, 0), (-1, 0), (0, 1), (0, -1)]
sections = []


def get_section(x, y):
    name = lines[y][x]
    visited.add((x, y))
    perimeter = 0
    area = 1
    section = set()
    section.add((x, y))
    for neighbor in neighbors:
        xn = x + neighbor[0]
        yn = y + neighbor[1]
        if xn < 0 or xn >= width or yn < 0 or yn >= height or lines[yn][xn] != name:
            perimeter += 1
        elif (xn, yn) not in visited:
            (neighbor_area, neigbor_perimeter, neighbor_section) = get_section(xn, yn)
            area += neighbor_area
            perimeter += neigbor_perimeter
            section.update(neighbor_section)

    return (area, perimeter, section)


s = 0
for y in range(height):
    for x in range(width):
        if (x, y) in visited:
            continue
        area, perimeter, section = get_section(x, y)
        sections.append(section)
        s += area * perimeter
print(s)

s2 = 0
for section in sections:
    # print("#################")
    # print(section)
    grouped_by_x = {}
    grouped_by_y = {}
    for pos in section:
        grouped_by_x.setdefault(pos[0], []).append(pos[1])
        grouped_by_y.setdefault(pos[1], []).append(pos[0])

    sorted_grouped_by_x = [(x, tuple(sorted(y_list))) for x, y_list in grouped_by_x.items()]
    # print(sorted_grouped_by_x)
    for i in range(len(sorted_grouped_by_x)):
        x, y_list = sorted_grouped_by_x[i]
        starts = []
        ends = []
        prev_y = None
        for y in y_list:
            if prev_y is None:
                starts.append(y)
            elif y - prev_y > 1:
                ends.append(prev_y)
                starts.append(y)
            prev_y = y
        ends.append(y)
        sorted_grouped_by_x[i] = (x, starts, ends)

    sorted_grouped_by_y = [(y, tuple(sorted(x_list))) for y, x_list in grouped_by_y.items()]
    # print(sorted_grouped_by_y)
    for i in range(len(sorted_grouped_by_y)):
        y, x_list = sorted_grouped_by_y[i]
        starts = []
        ends = []
        prev_x = None
        for x in x_list:
            if prev_x is None:
                starts.append(x)
            elif x - prev_x > 1:
                ends.append(prev_x)
                starts.append(x)
            prev_x = x
        ends.append(x)
        sorted_grouped_by_y[i] = (y, starts, ends)

    result_by_x = sorted(sorted_grouped_by_x, key=lambda item: item[0])
    result_by_y = sorted(sorted_grouped_by_y, key=lambda item: item[0])
    # print(result_by_x)
    # print(result_by_y)

    vertical_edges = 0
    prev_starts = []
    prev_ends = []
    for x, y_starts, y_ends in result_by_x:
        for y_start in y_starts:
            if y_start not in prev_starts:
                vertical_edges += 1
        for y_end in y_ends:
            if y_end not in prev_ends:
                vertical_edges += 1
        prev_starts = y_starts
        prev_ends = y_ends

    horizontal_edges = 0
    prev_starts = []
    prev_ends = []
    for y, x_starts, x_ends in result_by_y:
        for x_start in x_starts:
            if x_start not in prev_starts:
                horizontal_edges += 1
        for x_end in x_ends:
            if x_end not in prev_ends:
                horizontal_edges += 1
        prev_starts = x_starts
        prev_ends = x_ends

    s2 += (vertical_edges + horizontal_edges) * len(section)
    # print(vertical_edges)
    # print(horizontal_edges)
    # print("#################")
print(s2)
