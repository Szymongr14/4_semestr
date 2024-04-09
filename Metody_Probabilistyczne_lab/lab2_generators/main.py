def lcg(a, seed, c, m):
    result = (a * seed + c) % m
    return result


def generate_ranges():
    for i in range(10):
        counts[i] = counts[i] / N * 100
        print(f"Range {(i * _range / 10) + left} - {((i + 1) * _range / 10) + left}: {round(counts[i], 2)}% numbers")


counts = [0 for _ in range(10)]
a = 15
x = 15
a = 69069
c = 1
M = pow(2, 31)
left = 0
right = M
_range = right - left

N = 100000

for i in range(N):
    x = lcg(a, x, c, M)
    for j in range(10):
        if (_range / 10 * j) + left <= x < (_range / 10 * (j + 1)) + left:
            counts[j] += 1

generate_ranges()
print(counts)

counts = [0 for _ in range(10)]
p = 7
q = 3
b = [1, 0, 0, 1, 1, 0, 1]

for i in range(N):
    for j in range(7, 31):
        b.append((b[j - p] ^ b[j - q]))
    b_bits = ''.join(str(bit) for bit in b)
    b_int = int(b_bits, 2)
    # print(b_int)
    b = b[-7:]
    for j in range(10):
        if (_range / 10 * j) + left <= b_int < (_range / 10 * (j + 1)) + left:
            counts[j] += 1

print(counts)
generate_ranges()
