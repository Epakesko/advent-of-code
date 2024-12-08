import math

lines = open("input.txt", "r").readlines()


def solve(nums, target):
    if len(nums) == 1 and nums[0] == target:
        return True
    elif len(nums) < 2:
        return False
    elif nums[-1] > target:
        return False
    elif target % nums[-1] == 0:
        return solve(nums[:-1], target // nums[-1]) or solve(nums[:-1], target - nums[-1])
    else:
        return solve(nums[:-1], target - nums[-1])


def solve2(current, nums, target):
    if len(nums) == 0:
        if current == target:
            return True
        else:
            return False
    elif current > target:
        return False
    else:
        return solve2(current + nums[0], nums[1:], target) or solve2(current * nums[0], nums[1:], target) or (solve2(int(str(current) + str(nums[0])), nums[1:], target) if len(nums) > 0 else False)


def solve3(nums, target):
    if len(nums) == 1 and nums[0] == target:
        return True
    elif len(nums) < 2 or nums[-1] > target:
        return False
    elif target % nums[-1] == 0 and solve3(nums[:-1], target // nums[-1]):
        return True
    elif (target - nums[-1]) % (math.pow(10, len(str(nums[-1])))) == 0 and solve3(nums[:-1], (target - nums[-1]) // (math.pow(10, len(str(nums[-1]))))):
        return True
    return solve3(nums[:-1], target - nums[-1])


c = 0
c2 = 0
for line in lines:
    parts = line.split(": ")
    result = int(parts[0])
    nums = list(map(lambda x: int(x), parts[1].strip().split(" ")))
    if solve(nums, result):
        c += result
    # if solve2(nums[0], nums[1:], result):
    #    c2 += result

for line in lines:
    parts = line.split(": ")
    result = int(parts[0])
    nums = list(map(lambda x: int(x), parts[1].strip().split(" ")))
    if solve3(nums, result):
        c2 += result

print(c)
print(c2)
