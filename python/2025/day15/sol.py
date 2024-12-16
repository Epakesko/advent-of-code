lines = open("input.txt", "r").read().splitlines()
walls = set()
boxes = set()
move = []
robot = None

move_dict = {
    "^": (0, -1),
    "v": (0, 1),
    "<": (-1, 0),
    ">": (1, 0)
}

height = 0
width = len(lines[0])

for y, line in enumerate(lines):
    for x, char in enumerate(line):
        if char == "#":
            walls.add((x, y))
        elif char == "O":
            boxes.add((x, y))
        elif char == "@":
            robot = (x, y)
    if line == "":
        height = y
        break

for y, line in enumerate(lines[y+1:]):
    for x, char in enumerate(line):
        move.append(move_dict[char])

def move_robot(robot, move):
    next_pos = (robot[0] + move[0], robot[1] + move[1])
    first_box_to_move = None
    while(next_pos in boxes):
        if first_box_to_move is None:
            first_box_to_move = next_pos
        next_pos = (next_pos[0] + move[0], next_pos[1] + move[1])
    if next_pos in walls:
        return robot
    else:
        robot = (robot[0] + move[0], robot[1] + move[1])
        if first_box_to_move is not None:
            boxes.remove(first_box_to_move)
            boxes.add(next_pos)
        return robot

def display():
    for y in range(height):
        for x in range(width):
            if (x, y) in walls:
                print("#", end="")
            elif (x, y) in boxes:
                print("O", end="")
            elif (x, y) == robot:
                print("@", end="")
            else:
                print(".", end="")
        print()
    
for m in move:
    robot = move_robot(robot, m)

display()
print(sum([100 * box[1] + box[0] for box in boxes]))


walls = set()
boxes = set()
robot = None
move = []

move_dict = {
    "^": (0, -1),
    "v": (0, 1),
    "<": (-0.5, 0),
    ">": (0.5, 0)
}

for y, line in enumerate(lines):
    for x, char in enumerate(line):
        if char == "#":
            walls.add((x, y))
        elif char == "O":
            boxes.add((x, y))
        elif char == "@":
            robot = (x, y)
    if line == "":
        break

for y, line in enumerate(lines[y+1:]):
    for x, char in enumerate(line):
        move.append(char)

def move_boxes(move):
    moved_boxes = []
    for m in move:
        if m == "<":
            next_box = (robot[0] - 1, robot[1])
            while(next_box in boxes):
                moved_boxes.append(next_box)
                next_box = (next_box[0] - 1, next_box[1])
            if next_box in walls:
                return None
            else:
                return moved_boxes
        elif m == ">":
            next_box = (robot[0] + 0.5, robot[1])
            while(next_box in boxes):
                moved_boxes.append(next_box)
                next_box = (next_box[0] + 1, next_box[1])
            if next_box in walls:
                return None
            else:
                return moved_boxes
        elif m == "^":
            possible_moved_boxes = []
            possible_nextboxes = [(robot[0], robot[1] - 1), (robot[0] - 0.5, robot[1] - 1)]
            while(possible_nextboxes):
                new_possible_nextboxes = []
                for possible_nextbox in possible_nextboxes:
                    if possible_nextbox in boxes:
                        possible_moved_boxes.append(possible_nextbox)
                        new_possible_nextboxes.append((possible_nextbox[0], possible_nextbox[1] - 1))
                        new_possible_nextboxes.append((possible_nextbox[0] - 0.5, possible_nextbox[1] - 1))
                        new_possible_nextboxes.append((possible_nextbox[0] + 0.5, possible_nextbox[1] - 1))
                    elif possible_nextbox in walls:
                        return None
                possible_nextboxes = new_possible_nextboxes
            return possible_moved_boxes
        elif m == "v":
            possible_moved_boxes = []
            possible_nextboxes = [(robot[0], robot[1] + 1), (robot[0] - 0.5, robot[1] + 1)]
            while(possible_nextboxes):
                new_possible_nextboxes = []
                for possible_nextbox in possible_nextboxes:
                    if possible_nextbox in boxes:
                        possible_moved_boxes.append(possible_nextbox)
                        new_possible_nextboxes.append((possible_nextbox[0], possible_nextbox[1] + 1))
                        new_possible_nextboxes.append((possible_nextbox[0] - 0.5, possible_nextbox[1] + 1))
                        new_possible_nextboxes.append((possible_nextbox[0] + 0.5, possible_nextbox[1] + 1))
                    elif possible_nextbox in walls:
                        return None
                possible_nextboxes = new_possible_nextboxes
            return possible_moved_boxes


def move_robot2(robot, move):
    #print("robot is moving to ", (robot[0] + move_dict[move][0], robot[1] + move_dict[move][1]))
    movement = move_dict[move]
    move_boxes_list = move_boxes(move)
    #print("boxes to move ", move_boxes_list)
    if move_boxes_list == None:
        return robot
    else:
        for box in move_boxes_list:
            if box in boxes:
                boxes.remove(box)
        for box in move_boxes_list:
            boxes.add((box[0] + movement[0], box[1] + movement[1]))
        robot = (robot[0] + movement[0], robot[1] + movement[1])
        return robot

def display2(icon = "@"):
    #print("robot is at ", robot)
    for y in range(height):
        for x in range(width):
            if (x, y) in walls:
                print("##", end="")
            elif (x, y) in boxes:
                print("[]", end="")
            elif (x+0.5, y) in boxes and (x-0.5, y) in boxes:
                print("][", end="")
            elif (x+0.5, y) == robot and (x-0.5, y) in boxes:
                print("]" + icon, end="")
            elif (x, y) == robot and (x+0.5, y) in boxes:
                print(icon + "[", end="")
            elif (x+0.5, y) in boxes:
                print(".[", end="")
            elif (x-0.5, y) in boxes:
                print("].", end="")
            elif (x, y) == robot:
                print(icon + ".", end="")
            elif (x+0.5, y) == robot:
                print("." + icon, end="")
            else:
                print("..", end="")
        print()

print(len(boxes))
for m in move:
    #display2(m)
    robot = move_robot2(robot, m)

#display2()
print(len(boxes))

print(sum([100 * box[1] + 2 * box[0] for box in boxes]))