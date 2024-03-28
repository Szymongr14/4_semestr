def permutation(lst, k):
    # Base case: if k is 0, we have found a permutation of length k
    if k == 0:
        return [[]]

    # Base case: if the list is empty, there are no permutations
    if len(lst) == 0:
        return []

    # Recursive case: generate permutations
    l = []
    for i in range(len(lst)):
        m = lst[i]
        remLst = lst[:i] + lst[i + 1:]
        for p in permutation(remLst, k - 1):
            l.append([m] + p)
    return l


# Example usage
lst = ['a', 'b', 'c', 'd', 'e']
k = 3  # Number of elements in each permutation

# Generate permutations of 3 elements from lst
result = permutation(lst, k)

# Print the result
for perm in result:
    print(perm)
