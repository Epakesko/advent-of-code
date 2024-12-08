import time

start = time.time_ns()
lines = open("input.txt", "r").readlines()
list1 = []
list2 = []
counts = {}

for line in lines:
    num1, num2 = map(lambda x: int(x), line.split("   "))
    list1.append(num1)
    list2.append(num2)
    counts[num2] = counts.setdefault(num2, 0) + 1

sortedList1 = sorted(list1)
sortedList2 = sorted(list2)
print("preparation: ", (time.time_ns() - start)/1000000, "ms")

start = time.time_ns()
print(sum([abs(sortedList1[i] - sortedList2[i]) for i in range(len(sortedList1))]))
print("1st part: ", (time.time_ns() - start)/1000000, "ms")

start = time.time_ns()
print(sum([num * counts.setdefault(num, 0) for num in list1]))
print("2nd part: ", (time.time_ns() - start)/1000000, "ms")