import math

lines = open("input.txt", "r").read().splitlines()

register_a = int(lines[0].split("A: ")[1])
register_b = int(lines[1].split("B: ")[1])
register_c = int(lines[2].split("C: ")[1])
instruction_pointer = 0

program = list(map(lambda x: int(x), lines[4].split(" ")[1].split(",")))

out = ""

def combo_operand(operand):
    if operand < 0 or operand > 6:
        print("Error: Operand out of range")
        raise Exception("Operand out of range")
    if operand < 4:
        return operand
    if operand == 4:
        return register_a
    if operand == 5:
        return register_b
    if operand == 6:
        return register_c


def adv(operand):
    global register_a, instruction_pointer
    register_a = int(register_a / math.pow(2, operand))
    instruction_pointer += 2


def bxl(operand):
    global register_b, instruction_pointer
    register_b ^= operand
    instruction_pointer += 2


def bst(operand):
    global register_b, instruction_pointer
    register_b = operand % 8
    instruction_pointer += 2


def jnz(operand):
    global instruction_pointer
    if (register_a == 0):
        instruction_pointer += 2
        return
    instruction_pointer = operand


def bxc():
    global register_b, instruction_pointer
    register_b ^= register_c
    instruction_pointer += 2


def out_(operand):
    global out, instruction_pointer
    out += str(operand % 8)
    instruction_pointer += 2
    return operand % 8


def bdv(operand):
    global register_b, instruction_pointer
    register_b = int(register_a / math.pow(2, operand))
    instruction_pointer += 2


def cdv(operand):
    global register_c, instruction_pointer
    register_c = int(register_a / math.pow(2, operand))
    instruction_pointer += 2


def run(b_goal = None):
    while instruction_pointer < len(program):
        instruction = program[instruction_pointer]
        operand = program[instruction_pointer + 1]
        if instruction == 0:
            adv(combo_operand(operand))
        elif instruction == 1:
            bxl(operand)
        elif instruction == 2:
            bst(combo_operand(operand))
        elif instruction == 3:
            jnz(operand)
        elif instruction == 4:
            bxc()
        elif instruction == 5:
            res = out_(combo_operand(operand))
            if b_goal != None:
                return True if res == b_goal else False
        elif instruction == 6:
            bdv(combo_operand(operand))
        elif instruction == 7:
            cdv(combo_operand(operand))
        # print(register_a, register_b, register_c, instruction_pointer, "---", out)


run()
print(out)

i = 0
j = 1
starts = [0]
for j in range(1, len(program) + 1):
    need_end = program[-j]
    new_starts = []
    for start in starts:
        for i in range(start * 8, start * 8 + 8):
            register_a = i
            register_b = 0
            register_c = 0
            instruction_pointer = 0
            out = ""
            if run(need_end):
                new_starts.append(i)
    starts = new_starts
    
print(starts[0])
