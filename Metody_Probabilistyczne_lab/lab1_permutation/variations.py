def create_variations(elements_set, k):
    if k == 0:
        return [[]]
    if len(elements_set) == 0:
        return []
    if len(elements_set) == 1:
        return [elements_set]
    l = []
    for i in range(len(elements_set)):
        m = elements_set[i]
        remaining_set = elements_set[:i] + elements_set[i + 1:]
        for p in create_variations(remaining_set, k - 1):
            l.append([m] + p)
    return l
