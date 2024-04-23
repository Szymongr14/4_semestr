import random
from collections import Counter
ITERATOR = 10000

random.seed(None)
results = []

result = None
for i in range(0, ITERATOR + 1):
    random_number = random.randint(0, 100)
    # print(random_number)

    if random_number < 20:
        result = 1
    elif random_number < 50:
        result = 2
    elif random_number < 90:
        result = 3
    else:
        result = 4

    # print(result)
    results.append(result)
results.sort()

counter = Counter(results)

for value, count in counter.items():
    print(f"{value}: {round(count/ITERATOR*100, 2)}%\n")
