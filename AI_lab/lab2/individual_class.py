class Individual:

    def __init__(self, fitness, chance_to_be_picked, genome):
        self.fitness = fitness
        self.genome = genome
        self.chance_to_be_picked = chance_to_be_picked
