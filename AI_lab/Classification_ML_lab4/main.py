import math

import numpy as np

from decision_tree import DecisionTree
from load_data import generate_data, load_titanic
from random_forest import RandomForest


def main():
    np.random.seed(123)

    train_data, test_data = load_titanic()

    dt = DecisionTree({"depth": 14})
    dt.train(*train_data)
    dt.evaluate(*train_data)
    dt.evaluate(*test_data)

    rf = RandomForest({"ntrees": 10, "feature_subset": math.ceil(np.sqrt(len(test_data[0][0]))), "depth": 14})
    rf.train(*train_data)
    rf.evaluate(*train_data)
    rf.evaluate(*test_data)


if __name__ == "__main__":
    main()
