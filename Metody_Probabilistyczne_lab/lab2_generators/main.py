import time


def lcg(a, seed, c, m, left, right):
    m = right - left - 1
    result = (a * seed + c) % m
    return result + left


results = []
left = 14
right = 100
range_ = right - left

counts = [0 for _ in range(10)]

for i in range(100000):
    result = lcg(397204094, time.time(), 0, pow(2, 31) - 1, left, right)
    for j in range(10):
        if (range_/10 * j) + left <= result < (range_/10 * (j+1)) + left:
            counts[j] += 1

print(counts)

for i in range(10):
    counts[i] = counts[i] / 100000 * 100
    print(f"Range {(i * range_ / 10) + left} - {((i + 1) * range_ / 10) + left}: {round(counts[i], 2)}% numbers")
