import re

lines = open("input.txt", "r").read().splitlines()

button_a_regex = "Button A: X\+(\d+), Y\+(\d+)"
button_b_regex = "Button B: X\+(\d+), Y\+(\d+)"
prize_regex = "Prize: X=(\d+), Y=(\d+)"

def calc(offset = 0):
    s = 0
    for i in range(len(lines)):
        if i % 4 == 0:
            button_a = re.search(button_a_regex, lines[i])
        elif i % 4 == 1:
            button_b = re.search(button_b_regex, lines[i])
        elif i % 4 == 2:
            prize = re.search(prize_regex, lines[i])
            (x1, y1) = map(lambda x: int(x), button_a.groups())
            (x2, y2) = map(lambda x: int(x), button_b.groups())
            (xr, yr) = map(lambda x: int(x), prize.groups())

            xr += offset
            yr += offset

            b = (yr * x1 - xr * y1) / (y2 * x1 - x2 * y1)
            a = (xr - b * x2) / x1

            if a.is_integer() and b.is_integer():
                price = 3 * a + b
                s += int(price)
    return s

print(calc())
print(calc(10000000000000))
    