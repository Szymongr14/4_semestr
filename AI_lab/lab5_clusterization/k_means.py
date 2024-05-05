import numpy as np


def initialize_centroids_forgy(data, k):
    chosen_centroids = []
    for i in range(k):
        random_index = np.random.randint(0, len(data))
        chosen_centroids.append(data[random_index])
    return chosen_centroids


def initialize_centroids_kmeans_pp(data, k):
    chosen_centroids = []
    random_index = np.random.randint(0, len(data))
    first_centroid = data[random_index]
    chosen_centroids.append(first_centroid)
    if k == 1:
        return chosen_centroids

    while len(chosen_centroids) < k:
        distances = []
        for point in data:
            min_distance: float = float('inf')
            for centroid in chosen_centroids:
                current_distance: float = count_distance(point, centroid)
                if current_distance < min_distance:
                    min_distance = current_distance
            distances.append(min_distance)
        distances = np.array(distances)
        probabilities = distances / np.sum(distances)
        random_index = np.random.choice(len(data), p=probabilities)
        chosen_centroids.append(data[random_index])

    return chosen_centroids


def count_distance(point1, point2) -> int:
    return np.sqrt(np.sum((point1 - point2) ** 2))


def assign_to_cluster(data, centroids):
    assignments = []
    for point in data:
        min_distance: float = float('inf')
        closest_centroid_index = None
        for i, centroid in enumerate(centroids):
            current_distance: float = count_distance(point, centroid)
            if current_distance < min_distance:
                min_distance = current_distance
                closest_centroid_index = i
        assignments.append(closest_centroid_index)

    return assignments


def update_centroids(data, assignments, centroids):
    for centroid_index in np.unique(assignments):
        cluster_points = data[assignments == centroid_index]
        new_centroid = np.mean(cluster_points, axis=0)
        centroids[centroid_index] = new_centroid
    return centroids


def mean_intra_distance(data, assignments, centroids):
    assignments = np.array(assignments)
    centroids = np.array(centroids)
    return np.sqrt(np.sum((data - centroids[assignments, :]) ** 2))


def k_means(data, num_centroids, kmeansplusplus=False):
    # centroids initialization
    if kmeansplusplus:
        centroids = initialize_centroids_kmeans_pp(data, num_centroids)
    else:
        centroids = initialize_centroids_forgy(data, num_centroids)

    assignments = assign_to_cluster(data, centroids)
    for i in range(100):  # max number of iteration = 100
        print(f"Intra distance after {i} iterations: {mean_intra_distance(data, assignments, centroids)}")
        centroids = update_centroids(data, assignments, centroids)
        new_assignments = assign_to_cluster(data, centroids)
        if np.all(new_assignments == assignments):  # stop if nothing changed
            break
        else:
            assignments = new_assignments

    return new_assignments, centroids, mean_intra_distance(data, new_assignments, centroids)
