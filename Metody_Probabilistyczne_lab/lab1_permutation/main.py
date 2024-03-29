from City import City
from combination_with_repetition import create_combination_with_repetition
from variations import create_variations


def count_distance(list1) -> int:
    distance = 0
    for j in range(0, len(list1) - 1):
        current_city = list1[j]
        next_city = list1[j + 1]
        distance += City.count_distance_to_other_city(current_city, next_city)
    return distance


def count_avg_population(arr):
    sum = 0
    for city in arr:
        sum += city.population
    return sum / len(arr)


N = 4
cities = []
with open('italy_cities.txt', 'r') as file:
    lines = file.readlines()[1:]
    for i in range(0, N):
        data = lines[i].split()
        city = City(int(data[0]), data[1], int(data[2]), float(data[3]), float(data[4]))
        cities.append(city)

variations = create_variations(cities, 3)

# printing variations
i = 1
for element in variations:
    print(f"{i}. ", end="")
    for city in element:
        print(f"{city}, ", end="")
    print("\n")
    i += 1

# finding the shortest path
min_distance = float('+inf')
best_solution = []
current_distance = 0
for permutation in variations:
    current_distance = count_distance(permutation)
    if current_distance < min_distance:
        min_distance = current_distance
        best_solution = permutation

print("Shortest path:")
for city in best_solution:
    print(city, end=", ")
print(f"\nWith distance of: {min_distance}")

combinations_with_repetition = create_combination_with_repetition(cities, 3)
# printing combinations with repetition
i = 1
for element in combinations_with_repetition:
    if len(element) == 0: continue
    print(f"{i}. ", end="")
    for city in element:
        print(f"{city}, ", end="")
    print("\n")
    i += 1

combination_without_repetition = [v for v in combinations_with_repetition if len(set(v)) == len(v)]
# finding max avg population
max_avg = float('-inf')
max_avg_population_cities = []
current_avg = 0
for cities in combination_without_repetition:
    if len(cities) == 0: continue
    current_avg = count_avg_population(cities)
    if max_avg < current_avg:
        max_avg = current_avg
        max_avg_population_cities = cities

print("Max average population:")
for city in max_avg_population_cities:
    print(city, end=", ")
print(f"\nWith avg populationh of: {max_avg}")
