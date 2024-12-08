import time

start = time.time_ns()
lines = open("input.txt", "r").readlines()

def isSafe(line, skip = -1):
    prevnum = -1
    increase = 0

    for i in range(len(line.split(" "))):
        if(skip == i):
            continue

        num = line.split(" ")[i]

        if(prevnum == -1):
            prevnum = int(num)
            continue
        elif(increase == 0):
            increase = 1 if int(num) > prevnum else -1
    
        diff = (int(num) - prevnum) * increase
        prevnum = int(num)

        if(diff < 1 or diff > 3):
            return False
        
    return True
print("preparation: ", (time.time_ns() - start)/1000000, "ms")

start = time.time_ns()
print(sum([isSafe(line) for line in lines]))
print("1st part: ", (time.time_ns() - start)/1000000, "ms")

start = time.time_ns()
print(sum([any([isSafe(line, i) for i in range(-1, len(line))]) for line in lines]))
print("2nd part: ", (time.time_ns() - start)/1000000, "ms")