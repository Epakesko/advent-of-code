import re

line = ''.join(open("input.txt", "r").read().splitlines())

pattern = re.compile(r"mul\((\d+),(\d+)\)")
print(sum(map(lambda mul: int(mul[0]) * int(mul[1]), re.findall(pattern, line))))

text = re.sub(r"don't\(\).*?(do\(\)|\Z)", "", line)
print(sum(map(lambda mul: int(mul[0]) * int(mul[1]), re.findall(pattern, text))))