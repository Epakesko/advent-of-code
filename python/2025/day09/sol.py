line = list(map(lambda x: int(x), list(open("input.txt", "r").read().splitlines()[0])))
length = len(line)

position = 0
file_id = 0
file_id_backwards = length // 2
block_left_idx = 0
block_right_idx = length - 1
left_count = 0
right_count = 0
sum = 0
while True:
    #print(block_left_idx)
    if line[block_left_idx] == 0:
        block_left_idx += 1
        left_count = 0
        continue
    elif block_left_idx % 2 == 0:
        sum += position * file_id
        left_count += 1
        #print("file", position, file_id, sum, left_count, right_count, block_left_idx, block_right_idx, line[block_left_idx], line[block_right_idx])
        if left_count + (right_count if block_left_idx == block_right_idx else 0) == line[block_left_idx]:
            file_id += 1
            block_left_idx += 1
            left_count = 0
    else:
        sum += position * file_id_backwards
        right_count += 1
        left_count += 1
        #print("space", position, file_id_backwards, sum, left_count, right_count, block_left_idx, block_right_idx, line[block_left_idx], line[block_right_idx])
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
print(sum)
        
