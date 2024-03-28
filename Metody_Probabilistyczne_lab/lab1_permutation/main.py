from City import City


def count_distance(list1) -> int:
    distance = 0
    for j in range(0, len(list1) - 1):
        current_city = list1[j]
        next_city = list1[j + 1]
        distance += City.count_distance_to_other_city(current_city, next_city)
    return distance


def permutation(elements_set, k):
    if k == 0:
        return [[]]
    if len(elements_set) == 0:
        return []
    if len(elements_set) == 1:
        return [elements_set]
    l = []
    for i in range(len(elements_set)):
        m = elements_set[i]
        remaining_set = elements_set[:i] + elements_set[i + 1:]
        for p in permutation(remaining_set, k - 1):
            l.append([m] + p)
    return l


N = 4
cities = []
with open('italy_cities.txt', 'r') as file:
    lines = file.readlines()[1:]
    for i in range(0, N):
        data = lines[i].split()
        city = City(data[1], int(data[2]), float(data[3]), float(data[4]))
        cities.append(city)


variations = permutation(cities, 3)
i = 1
for element in variations:
    print(f"{i}. ", end="")
    for city in element:
        print(f"{city}, ", end="")
    print("\n")
    i += 1

permutations = variations
min_distance = float('+inf')
best_solution = 0
current_distance = 0
for permutation in permutations:
    current_distance = count_distance(permutation)
    if current_distance < min_distance:
        min_distance = current_distance
        best_solution = permutation

print("Shortest path:")
for city in best_solution:
    print(city, end=", ")
print(f"\nWith distance of: {min_distance}")
