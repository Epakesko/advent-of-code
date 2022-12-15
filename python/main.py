from pathlib import Path
import sys
import time
import day01.sol
import day02.sol
#IMPORT DAYS

days = [day01, day02] #SOLVED DAYS

def runDay(day, singleDay):
    start = time.perf_counter()
    part1 = day.part1()
    mid = time.perf_counter()
    part2 = day.part2()
    end = time.perf_counter()

    part1Color = colors.GREEN
    part1Time = mid - start
    if(part1Time > 1.5):
        part1Color = colors.RED 
    elif(part1Time > 0.5):
        part1Color = colors.YELLOW

    part2Color = colors.GREEN
    part2Time = end - mid
    if(part2Time > 1.5):
        part2Color = colors.RED 
    elif(part2Time > 0.5):
        part2Color = colors.YELLOW

    if(singleDay): 
        print(f"{colors.HEADER}{day.name} - part1:{colors.ENDC} {part1} {part1Color}({part1Time:0.4f}s){colors.ENDC}, {colors.HEADER}part2:{colors.ENDC} {part2} {part2Color}({part2Time:0.4f}s){colors.ENDC}")
    else:
        print(format_row.format("", day.name, f"{part1} {part1Color}({part1Time:0.4f}s){colors.ENDC}", f"{part2} {part2Color}({part2Time:0.4f}s){colors.ENDC}", ""))
        

class colors:
    HEADER = '\033[36m'
    GREEN = "\033[32m"
    YELLOW = "\033[33m"
    RED = "\033[31m"
    ENDC = '\033[0m'

format_header = colors.HEADER + "{:<1}{:^30}{:^30}{:^30}{:>1}" + colors.ENDC
format_row = "{:<1}{:^30}{:^39}{:^39}{:>1}"

if(len(sys.argv) > 1):
    match sys.argv[1]:
        case "1":
            runDay(day01.sol, True)
        case "2":
            runDay(day02.sol, True)
        #RUN DAYS
        case _:
            print(sys.argv[1] + " is not done. Please provide the day as a simple one or two digit number.")
else:
    print(format_header.format("", "", "Solving year 2022", "", ""))
    print(format_header.format("", "_" * 30, "_" * 30, "_" * 30, ""))
    print(format_header.format("|", "DAY", "PART1 (TIME)", "PART2 (TIME)", "|"))
    print(format_header.format("", "‾" * 30, "‾" * 30, "‾" * 30, ""))
    globalStart = time.perf_counter()
    for day in days:
        runDay(day.sol, False)
    globalEnd = time.perf_counter()
    print("")
    print(format_header.format("", "", f"Full runtime: {globalEnd - globalStart:0.4f}s", "", ""))
    print(format_header.format("", "‾" * 30, "‾" * 30, "‾" * 30, ""))