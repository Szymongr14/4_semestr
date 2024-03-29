result = [[]]


def combination_with_repetition_body(current_combination, arr, index, r, start, end):
    global result

    if start > len(arr) - 1:
        return

    if index == r:
        if current_combination:
            result.append([current_combination[j] for j in range(r)])
        return

    current_combination[index] = arr[start]
    combination_with_repetition_body(current_combination, arr, index + 1, r, start, end)
    combination_with_repetition_body(current_combination, arr, index, r, start + 1, end)


def create_combination_with_repetition(arr, r):
    chosen = [0] * r
    combination_with_repetition_body(chosen, arr, 0, r, 0, len(arr) - 1)
    return result
