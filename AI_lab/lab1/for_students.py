import numpy as np
import matplotlib.pyplot as plt

from data import get_data, inspect_data, split_data


def plot_graph(title):
    x = np.linspace(min(x_test), max(x_test), 100)
    y = float(theta_best[0]) + float(theta_best[1]) * x
    plt.plot(x, y)
    plt.scatter(x_test, y_test)
    plt.xlabel('Weight')
    plt.ylabel('MPG')
    plt.title(title)
    plt.show()


def count_mse(features, labels):
    return np.sum((labels - features.dot(theta_best)) ** 2) / len(labels)


data = get_data()
inspect_data(data)

train_data, test_data = split_data(data)

# Simple Linear Regression
# predict MPG (y, dependent variable) using Weight (x, independent variable) using closed-form solution
# y = theta_0 + theta_1 * x - we want to find theta_0 and theta_1 parameters that minimize the prediction error

# We can calculate the error using MSE metric:
# MSE = SUM (from i=1 to n) (actual_output - predicted_output) ** 2

# get the columns
y_train = train_data['MPG'].to_numpy()
x_train = train_data['Weight'].to_numpy()

y_test = test_data['MPG'].to_numpy()
x_test = test_data['Weight'].to_numpy()

# TODO: calculate closed-form solution

# create ones column
ones_column = np.ones((len(x_train), 1))
# stack ones column with x_train
x_train_with_ones = np.hstack((ones_column, x_train.reshape(-1, 1)))
# counting theta using normal equation
theta_best = np.linalg.inv(x_train_with_ones.T.dot(x_train_with_ones)).dot(x_train_with_ones.T).dot(y_train)
print("Theta_best for Normal Equation method: ", theta_best)

# TODO: calculate error

x_test_with_ones_column = np.hstack((np.ones((len(x_test), 1)), x_test.reshape(-1, 1)))
mse = count_mse(x_test_with_ones_column, y_test)
print("MSE with normal equation for test set is: ", mse)

# plot the regression line
plot_graph("Normal equation")

# TODO: standardization
# counting mean and standard deviation for x_train and y_train
train_x_mean = np.mean(x_train)
train_x_std = np.std(x_train)
train_y_mean = np.mean(y_train)
train_y_std = np.std(y_train)

train_x_normalized = (x_train - train_x_mean) / train_x_std
train_y_normalized = (y_train - train_y_mean) / train_y_std

# standardization x_test and y_test with train set mean and standard deviation
x_test = (x_test - train_x_mean) / train_x_std
y_test = (y_test - train_y_mean) / train_y_std

# TODO: calculate theta using Batch Gradient Descent

theta_best = np.random.rand(2)
learning_rate = 0.01
train_x_normalized = np.hstack((ones_column, train_x_normalized.reshape(-1, 1)))
treshold = 1.0e-5
theta_best_prev = theta_best
iterations = 0

while True:
    iterations += 1
    gradients = 2 / len(x_train) * train_x_normalized.T.dot(train_x_normalized.dot(theta_best) - train_y_normalized)
    theta_best = theta_best - learning_rate * gradients
    if abs(theta_best_prev - theta_best).all() <= treshold:
        break
    theta_best_prev = theta_best

# theta_best = [0,0]
# TODO: calculate error

x_test_with_ones_column_normalized = np.hstack((np.ones((len(x_test), 1)), x_test.reshape(-1, 1)))
mse = count_mse(x_test_with_ones_column_normalized, y_test)
print("MSE with gradient descent (iterations:", iterations, ") for test set is: ", mse)

print("Theta_best for Batch Gradient Descent method: ", theta_best)
# plot the regression line
plot_graph("Gradient Descent")
