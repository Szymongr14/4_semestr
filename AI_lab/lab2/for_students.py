from copy import deepcopy
from itertools import compress
import random
import time
import matplotlib.pyplot as plt
from individual_class import Individual

from data import *


def initial_population(individual_size, population_size):
    return [[random.choice([True, False]) for _ in range(individual_size)] for _ in range(population_size)]


def fitness(items, knapsack_max_capacity, individual):
    total_weight = sum(compress(items['Weight'], individual))
    if total_weight > knapsack_max_capacity:
        return 0
    return sum(compress(items['Value'], individual))


def population_best(items, knapsack_max_capacity, population):
    best_individual = None
    best_individual_fitness = -1
    for individual in population:
        individual_fitness = fitness(items, knapsack_max_capacity, individual)
        if individual_fitness > best_individual_fitness:
            best_individual = individual
            best_individual_fitness = individual_fitness
    return best_individual, best_individual_fitness


def draw_parent(individuals, sum_of_fitnesses) -> Individual:
    pick = random.randint(0, sum_of_fitnesses)
    current = 0
    for individual_i in individuals:
        current += individual_i.fitness
        if current >= pick:
            return individual_i


def crossover_parents(genome1, genome2):
    middle = random.randint(0, len(genome1)-1)
    return genome1[:middle] + genome2[middle:], genome2[:middle] + genome1[middle:]


def mutate(genome):
    random_item = random.randint(0, len(genome)-1)
    genome[random_item] = not genome[random_item]
    return genome


items, knapsack_max_capacity = get_big()
print(items)

population_size = 100
generations = 200
n_selection = 20
n_elite = 2

start_time = time.time()
best_solution = None
best_fitness = 0
population_history = []
best_history = []
population = initial_population(len(items), population_size)

individuals = []
children = []

for _ in range(generations):
    population_history.append(deepcopy(population))
    sum_of_fitnesses = 0

    # counting fitness for each individual
    for individual in population:
        individuals.append(Individual(fitness(items, knapsack_max_capacity, individual), 0, individual))
        sum_of_fitnesses += individuals[-1].fitness

    # counting chance to be picked based on fitness for each individual
    for individual in individuals:
        individual.chance_to_be_picked = individual.fitness / sum_of_fitnesses

    # drawing parents with roulette wheel selection
    for _ in range(n_selection):
        parent1 = draw_parent(individuals, sum_of_fitnesses)
        parent2 = draw_parent(individuals, sum_of_fitnesses)
        child1, child2 = crossover_parents(parent1.genome, parent2.genome)
        children.append(mutate(child1))
        children.append(mutate(child2))

    best_individual, best_individual_fitness = population_best(items, knapsack_max_capacity, population)
    # adding the best individual from previous generation
    for _ in range(n_elite):
        children.append(best_individual)

    if best_individual_fitness > best_fitness:
        best_solution = best_individual
        best_fitness = best_individual_fitness
    best_history.append(best_fitness)
    population = deepcopy(children)
    children.clear()
    individuals.clear()


end_time = time.time()
total_time = end_time - start_time
print('Best solution:', list(compress(items['Name'], best_solution)))
print('Best solution value:', best_fitness)
print('Time: ', total_time)

# plot generations
x = []
y = []
top_best = 10
for i, population in enumerate(population_history):
    plotted_individuals = min(len(population), top_best)
    x.extend([i] * plotted_individuals)
    population_fitnesses = [fitness(items, knapsack_max_capacity, individual) for individual in population]
    population_fitnesses.sort(reverse=True)
    y.extend(population_fitnesses[:plotted_individuals])
plt.scatter(x, y, marker='.')
plt.plot(best_history, 'r')
plt.xlabel('Generation')
plt.ylabel('Fitness')
plt.show()
