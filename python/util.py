from pathlib import Path
SCRIPT_DIR = Path(__file__).parent
INPUT_FILE = Path(SCRIPT_DIR, "input.txt")

def readLines(file):
    with open(Path(Path(file).parent, "input.txt"), 'r') as f:
        lines = f.readlines()
    return lines

def readInts(file):
    with open(Path(Path(file).parent, "input.txt"), 'r') as f:
        lines = map(lambda line: int(line), f.readlines())
    return lines