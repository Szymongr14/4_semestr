class MinMaxAgent:
    def __init__(self, my_token, max_tree_depth):
        self.my_token = my_token
        self.max_tree_depth = max_tree_depth

    def decide(self, connect4) -> int:
        decision = self.minmax(connect4, self.max_tree_depth, True)
        return decision

    def minmax(self, connect4, depth, maximizing_player):
        if depth == 0 or connect4.game_over():
            match connect4.wins:
                case self.my_token:
                    return 1
                case None:
                    return self.rate_state(connect4)
                case _:
                    return -1
        if maximizing_player:
            max_value = float("-inf")
            for possible_move in connect4.possible_drops():
                connect4.drop_token(possible_move)
                value = self.minmax(connect4, depth - 1, False)
                connect4.undo_move()
                max_value = max(value, max_value)
            return max_value
        else:
            min_value = float("+inf")
            for possible_move in connect4.possible_drops():
                connect4.drop_token(possible_move)
                value = self.minmax(connect4, depth - 1, True)
                connect4.undo_move()
                min_value = min(value, min_value)
            return min_value

    def rate_state(self, connect4):
        middle = connect4.center_column()
        return self.my_token
