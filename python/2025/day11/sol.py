line = open("input.txt", "r").read().splitlines()[0]
nums = list(map(lambda x: int(x), line.split(" ")))
dict = {}

for num in nums:
    dict[num] = 1

for _ in range(25):
    newnums = []
    for num in nums:
        if num == 0:
            newnums.append(1)
        elif len(str(num)) % 2 == 0:
            stringnum = str(num)
            newnums.append(int(stringnum[:len(stringnum) // 2]))
            newnums.append(int(stringnum[len(stringnum) // 2:]))
        else:
            newnums.append(num * 2024)
    nums = newnums

print(len(nums))

for _ in range(75):
    newdict = {}
    for elem in dict:
        if elem == 0:
            newdict.setdefault(1, 0)
            newdict[1] += dict[elem]
        elif len(str(elem)) % 2 == 0:
            stringnum = str(elem)
            num1 = int(stringnum[:len(stringnum) // 2])
            num2 = int(stringnum[len(stringnum) // 2:])
            newdict.setdefault(num1, 0)
            newdict[num1] += dict[elem]
            newdict.setdefault(num2, 0)
            newdict[num2] += dict[elem]
        else:
            newdict.setdefault(elem * 2024, 0)
            newdict[elem * 2024] += dict[elem]
    dict = newdict

print(sum(dict.values()))
