import requests
import json
import sys
import os
import re

headers = { "user-agent": "github.com/Epakesko/advent-of-code by bendeguz.takacs@gmail.com" }
cookies={'session': "53616c7465645f5f5fe2b845d799bdee83b2c5fb0f2112884d302ee52927fd4e158bd16e21c8ad5e5c4f143ec5ff11c7a0792f7bb2cce064fed9380ba0b7ce1a"}
base_url = "https://adventofcode.com/2022/day/"
day = sys.argv[1]

# Create a new folder
day_name = "day" + day.zfill(2)
os.mkdir(day_name)
print(base_url + day.zfill(2) + "/input")
response = requests.get(base_url + day + "/input", headers=headers, cookies=cookies)
with open(os.path.join(day_name, "input.txt"), "w") as f:
    f.write(response.text)

with open("template.py", "r") as f:
    template = f.read()

with open(os.path.join(day_name, "sol.py"), "w") as f:
    f.write(template)

# Modify main.py by replacing a couple lines with regex
with open("main.py", "r") as f:
    main = f.read()

# Replace "old_line_1" with "new_line_1"
main = re.sub(r"(days = \[.*)(\] #SOLVED DAYS)", r"\1, {}\2".format(day_name), main)

# Replace "old_line_2" with "new_line_2"
main = re.sub(r"(#RUN DAYS)", r'case "{}":\n            runDay({}.sol, True\n        \1)'.format(day, day_name), main)

with open("main.py", "w") as f:
    f.write(main)