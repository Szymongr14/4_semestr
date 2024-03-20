import time

from City import City


def count_distance(list1) -> int:
    distance = 0
    for j in range(0, len(list1) - 1):
        current_city = list1[j]
        next_city = list1[j + 1]
        distance += City.count_distance_to_other_city(current_city, next_city)
    return distance


def permutation(lst):
    if len(lst) == 0:
        return []
    if len(lst) == 1:
        return [lst]
    l = []
    for i in range(len(lst)):
        m = lst[i]
        remLst = lst[:i] + lst[i + 1:]
        for p in permutation(remLst):
            l.append([m] + p)
    return l


N = 10
cities = []
with open('miasta.in', 'r') as file:
    lines = file.readlines()[1:]  # Skip the header line
    for i in range(0, N):
        data = lines[i].split()
        city = City(data[1], int(data[2]), float(data[3]), float(data[4]))
        cities.append(city)

start_time = time.time()
permutations = permutation(cities)
end_time = time.time()

min_distance = float('+inf')
best_solution = 0
current_distance = 0
for permutation in permutations:
    current_distance = count_distance(permutation)
    if current_distance < min_distance:
        min_distance = current_distance
        best_solution = permutation

print("Best solution:")
for city in best_solution:
    print(city)
print(f"Time: {end_time - start_time}s")
