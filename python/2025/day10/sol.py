
lines = list(map(lambda x: list(map(lambda y: int(y), list(x))), list(open("input.txt", "r").read().splitlines())))

height = len(lines)
width = len(lines[0])

def find_trails(current_pos, current_level):
    trails = set()
    if(current_pos[0] < 0 or current_pos[0] >= width or current_pos[1] < 0 or current_pos[1] >= height or lines[current_pos[1]][current_pos[0]] != current_level):
        return trails
    if(current_level == 9):
        trails.add(current_pos)
        return trails
    
    next_level = current_level + 1
    trails.update(find_trails((current_pos[0] + 1, current_pos[1]), next_level))
    trails.update(find_trails((current_pos[0] - 1, current_pos[1]), next_level))
    trails.update(find_trails((current_pos[0], current_pos[1] + 1), next_level))
    trails.update(find_trails((current_pos[0], current_pos[1] - 1), next_level))
    return trails

def find_trails_count(current_pos, current_level):
    if(current_pos[0] < 0 or current_pos[0] >= width or current_pos[1] < 0 or current_pos[1] >= height or lines[current_pos[1]][current_pos[0]] != current_level):
        return 0
    if(current_level == 9):
        return 1
    
    next_level = current_level + 1
    return find_trails_count((current_pos[0] + 1, current_pos[1]), next_level) \
        + find_trails_count((current_pos[0] - 1, current_pos[1]), next_level) \
        + find_trails_count((current_pos[0], current_pos[1] + 1), next_level) \
        + find_trails_count((current_pos[0], current_pos[1] - 1), next_level)

count = 0
count2 = 0
for y in range(height):
    for x in range(width):
        if(lines[y][x] == 0):
            count += len(find_trails((x, y), 0))
            count2 += find_trails_count((x, y), 0)

print(count)
print(count2)

