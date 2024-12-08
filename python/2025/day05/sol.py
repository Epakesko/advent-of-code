lines = open("input.txt", "r").read().splitlines()
empty = lines.index("")
dict = {}

c = 0
c2 = 0

def bubble_sort(arr):
    for n in range(len(arr) - 1, 0, -1):
        swapped = False  
        for i in range(n):
            if arr[i] in dict[arr[i + 1]]:
                arr[i], arr[i + 1] = arr[i + 1], arr[i]
                swapped = True
        if not swapped:
            break

for i in range(empty):
    pages = lines[i].split("|")
    dict[pages[0]] = dict.setdefault(pages[0], []) + [pages[1]]

for i in range(empty + 1, len(lines)):
    pages = lines[i].split(",")
    goodline = True
    for j in range(empty): 
        requiredpages = lines[j].split("|")
        x = pages.index(requiredpages[0]) if requiredpages[0] in pages else None
        y = pages.index(requiredpages[1]) if requiredpages[1] in pages else None
        if x is not None and y is not None and y < x:
            goodline = False
            bubble_sort(pages)
            c2 += int(pages[len(pages) // 2])
            break
    if goodline:
        c += int(pages[len(pages) // 2])

print(c)
print(c2)