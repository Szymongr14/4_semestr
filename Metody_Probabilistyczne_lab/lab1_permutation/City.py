class City:
    def __init__(self, name, population, latitude, longitude):
        self.name = name
        self.population = population
        self.latitude = latitude
        self.longitude = longitude

    def __str__(self):
        return f"{self.name} has a population of {self.population}"
