lines = open("input.txt", "r").readlines()


def xmas(i, j):
    count = 0
    for x in range(-1, 2):
        if i + 3 * x < 0 or i + 3 * x >= len(lines):
            continue
        for y in range(-1, 2):
            if j + 3 * y < 0 or j + 3 * y >= len(lines[i]) or (x == 0 and y == 0):
                continue
            if lines[i + x][j + y] == "M" and lines[i + 2 * x][j + 2 * y] == "A" and lines[i + 3 * x][j + 3 * y] == "S":
                count += 1
    return count


def crossmas(i, j):
    diag1 = lines[i - 1][j - 1] + lines[i][j] + lines[i + 1][j + 1]
    diag2 = lines[i - 1][j + 1] + lines[i][j] + lines[i + 1][j - 1]
    return (diag1 == "MAS" or diag1 == "SAM") and (diag2 == "MAS" or diag2 == "SAM")


print(sum([xmas(i, j) for i in range(len(lines)) for j in range(len(lines[i])) if lines[i][j] == "X"]))
print(sum([crossmas(i, j) for i in range(1, len(lines) - 1)
      for j in range(1, len(lines[i]) - 1) if lines[i][j] == "A"]))
