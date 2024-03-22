from math import sqrt


class City:
    def __init__(self, name, population, latitude, longitude):
        self.name = name
        self.population = population
        self.latitude = latitude
        self.longitude = longitude

    def __str__(self):
        return f"{self.name}"

    @staticmethod
    def count_distance_to_other_city(city1, city2):
        distance = sqrt(pow(city1.latitude - city2.latitude, 2) + pow(city1.longitude - city2.longitude, 2))
        return distance
