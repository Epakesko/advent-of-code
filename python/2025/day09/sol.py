line = list(map(lambda x: int(x), list(open("input.txt", "r").read().splitlines()[0])))
length = len(line)

position = 0
file_id = 0
file_id_backwards = length // 2
block_left_idx = 0
block_right_idx = length - 1
left_count = 0
right_count = 0
s = 0
while True:
    # print(block_left_idx)
    if line[block_left_idx] == 0:
        block_left_idx += 1
        left_count = 0
        continue
    elif block_left_idx % 2 == 0:
        s += position * file_id
        left_count += 1
        # print("file", position, file_id, sum, left_count, right_count, block_left_idx, block_right_idx, line[block_left_idx], line[block_right_idx])
        if left_count + (right_count if block_left_idx == block_right_idx else 0) == line[block_left_idx]:
            file_id += 1
            block_left_idx += 1
            left_count = 0
    else:
        s += position * file_id_backwards
        right_count += 1
        left_count += 1
        # print("space", position, file_id_backwards, sum, left_count, right_count, block_left_idx, block_right_idx, line[block_left_idx], line[block_right_idx])
        if right_count == line[block_right_idx]:
            file_id_backwards -= 1
            block_right_idx -= 2
            right_count = 0
        if left_count == line[block_left_idx]:
            block_left_idx += 1
            left_count = 0
    position += 1
    if block_left_idx > block_right_idx:
        break
print(s)


# position = 0
# file_id = 0
# file_id_backwards = length // 2
# block_left_idx = 0
# block_right_idx = length - 1
# sum = 0
# while True:
#     # print(block_left_idx)
#     if line[block_left_idx] == 0:
#         block_left_idx += 1
#         left_count = 0
#         continue
#     elif block_left_idx % 2 == 0:
#         sum += calculate_file_value(position, line[block_left_idx], file_id)
#         position += line[block_left_idx]
#         file_id += 1
#     else:
#         while line[block_right_idx] > line[block_left_idx]:
#             block_right_idx -= 2
#             file_id_backwards -= 1
#         if block_right_idx < block_left_idx:
#             position += line[block_left_idx]
#             continue
#         sum += calculate_file_value(position, line[block_right_idx], file_id_backwards)
#         position += line[block_left_idx]
#         sum += position * file_id_backwards
#         right_count += 1
#         left_count += 1
#         # print("space", position, file_id_backwards, sum, left_count, right_count, block_left_idx, block_right_idx, line[block_left_idx], line[block_right_idx])
#         if right_count == line[block_right_idx]:
#             file_id_backwards -= 1
#             block_right_idx -= 2
#             right_count = 0
#         if left_count == line[block_left_idx]:
#             block_left_idx += 1
#             left_count = 0
#     position += 1
#     if block_left_idx > block_right_idx:
#         break
# print(sum)

def calculate_file_value(start_pos, file_length, file_id):
    return sum([start_pos + i for i in range(file_length)]) * file_id


files = []
for i in range(length):
    files.append((line[i], -1 if i % 2 == 1 else i // 2))

for i in range(length - 1, -1, -1):
    if files[i][1] == -1:
        continue
    for j in range(i):
        if files[j][1] == -1 and files[j][0] >= files[i][0]:
            files[j] = (files[j][0] - files[i][0], -1)
            temp = files[i][1]
            files[i] = (files[i][0], -1)
            files.insert(j, (files[i][0], temp))
            i += 1
            break

s = 0
position = 0
for file in files:
    if file[1] == -1:
        position += file[0]
        continue
    s += calculate_file_value(position, file[0], file[1])
    position += file[0]
print(s)
