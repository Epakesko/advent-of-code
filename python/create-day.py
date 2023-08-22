import requests
import sys
import os
import re
from bs4 import BeautifulSoup

headers = { "user-agent": "github.com/Epakesko/advent-of-code by bendeguz.takacs@gmail.com" }
cookies={'session': os.environ.get("AOC_AUTH_TOKEN")}
base_url = "https://adventofcode.com/2022/day/"
day = sys.argv[1]

# Create a new folder
day_name = "day" + day.zfill(2)
try:
    os.mkdir(day_name)
except FileExistsError:
    # If the folder already exists, ignore the error and continue
    pass

# Get day title
response = requests.get(base_url + day, headers=headers, cookies=cookies)
soup = BeautifulSoup(response.text, "html.parser")
h2 = soup.find("h2")
day_title = re.sub(r"--- Day \d+: (.*) ---", r"\1", h2.text)

# Create input.txt
response = requests.get(base_url + day + "/input", headers=headers, cookies=cookies)
with open(os.path.join(day_name, "input.txt"), "w+") as f:
    f.write(response.text)

# Create solution script based on the template
with open("template.py", "r") as f:
    template = f.read()
with open(os.path.join(day_name, "sol.py"), "w") as f:
    f.write(re.sub(r"#NAME", '"{}"'.format(day_title), template))

# Add the new day to the main script
with open("main.py", "r") as f:
    main = f.read()
main = re.sub(r"(days = \[.*)(\] #SOLVED DAYS)", r"\1, {}\2".format(day_name), main)
main = re.sub(r"(#RUN DAYS)", r'case "{}":\n            runDay({}.sol, True)\n        \1'.format(day, day_name), main)
main = re.sub(r"(#IMPORT DAYS)", r'import {}.sol\n\1'.format(day_name), main)
with open("main.py", "w") as f:
    f.write(main)