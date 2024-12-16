import matplotlib.pyplot as plt
import matplotlib.animation as animation
import math
import numpy as np

lines = open("input.txt", "r").read().splitlines()

robots = []
velocities = []
loops = []
width = 101
height = 103
totalelapsed = 0

for line in lines:
    position, velocity = line.split(" ")
    px, py = map(lambda x: int(x), position[2:].split(","))
    vx, vy = map(lambda x: int(x), velocity[2:].split(","))
    robots.append((px, py))
    velocities.append((vx, vy))

def move(times):
    global totalelapsed
    totalelapsed += times
    for i in range(len(robots)):
        px, py = robots[i]
        vx, vy = velocities[i]

        finalx = (px + vx * times) % width
        finaly = (py + vy * times) % height
        robots[i] = (finalx, finaly)

first_quadrant = list(filter(lambda x: x[0] < width // 2 and x[1] < height // 2, robots))
second_quadrant = list(filter(lambda x: x[0] > width // 2 and x[1] < height // 2, robots))
third_quadrant = list(filter(lambda x: x[0] < width // 2 and x[1] > height // 2, robots))
fourth_quadrant = list(filter(lambda x: x[0] > width // 2 and x[1] > height // 2, robots))

print(len(first_quadrant) * len(second_quadrant) * len(third_quadrant) * len(fourth_quadrant))

def display():
    for i in range(height):
        for j in range(width):
            if (j, i) in robots:
                print("#", end="")
            else:
                print(".", end="")
        print()

# for i in range(len(velocities)):
#     velocity = velocities[i]
#     every_x = math.lcm(width, velocity[0]) // width
#     every_y = math.lcm(height, velocity[1]) // height
#     every_step = math.lcm(every_x, every_y)
#     print(every_x, every_y, every_step)
#     loops.append(every_step)

# res = 1
# for loop in loops:
#     res = math.lcm(res, loop)
# print(res)

#Separate the x and y values
displayable = ((robot[0]-40, 80-robot[1]) for robot in robots if 40 <= robot[0] <= 80 and 40 <= robot[1] <= 80)
x_values, y_values = zip(*displayable)

# Create a scatter plot
plt.figure(figsize=(0, 40))
fig, ax = plt.subplots()
line, = ax.plot(x_values, y_values, 'o', markersize=2)
line.set_color((1, 0, 0))
#plt.scatter(x_values, y_values, color='blue', marker='o', label='Points')

# Add labels and title
plt.title('Ho ho ho', fontsize=16)

# Add grid
plt.grid(True, linestyle='--', alpha=0.7)

# Show legend
plt.legend()
move(8268.5)
def update(num, plt, line):
    speed = max(min(abs(8270 - totalelapsed) / 100, 0.01), 0.002)
    if totalelapsed + speed >= 8270:
        speed = 8270 - totalelapsed
    if totalelapsed < 8270:
        move(speed)
    print("i have moved", totalelapsed, "times")
    displayable = ((robot[0]-40, 80-robot[1]) for robot in robots if 40 <= robot[0] <= 80 and 40 <= robot[1] <= 80)
    x_values, y_values = zip(*displayable)
    #plt.title(int(totalelapsed), fontsize=16)
    line.set_data(x_values, y_values)
    colorr = min(abs(8270 - totalelapsed) * 1000, 255) / 255
    colorg = abs(colorr - 1)
    line.set_color((colorr, colorg, 0))
    return line,

ani = animation.FuncAnimation(fig, update, 500, interval=0.2, fargs=[plt, line], blit=True)
ani.save('animation_drawing7.gif', writer='imagemagick', fps=200)

# Show the plot
#plt.show()