import requests
import json

headers = { "user-agent": "github.com/Epakesko/advent-of-code by bendeguz.takacs@gmail.com" }
cookies={'session': "53616c7465645f5f5fe2b845d799bdee83b2c5fb0f2112884d302ee52927fd4e158bd16e21c8ad5e5c4f143ec5ff11c7a0792f7bb2cce064fed9380ba0b7ce1a"}
url = "https://adventofcode.com/2022/day/1/input"

response = requests.get(url, headers=headers, cookies=cookies)

print(response.text)