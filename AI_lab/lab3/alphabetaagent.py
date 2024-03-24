class AlphaBetaAgent:
    def __init__(self, my_token, max_tree_depth):
        self.my_token = my_token
        self.max_tree_depth = max_tree_depth
        self.best_move = None

    def decide(self, connect4) -> int:
        self.minmax(connect4, self.max_tree_depth, True, float('-inf'), float('+inf'))
        return self.best_move

    def minmax(self, connect4, depth, maximizing_player, alpha, beta):
        if depth == 0 or connect4._check_game_over():
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
                connect4.drop_token(possible_move, True)
                value = self.minmax(connect4, depth - 1, False, alpha, beta)
                connect4.undo_last_move()
                if value > max_value:
                    max_value = value
                    self.best_move = possible_move
                alpha = max(value, alpha)
                if value >= beta:
                    break
            return max_value
        else:
            min_value = float("+inf")
            for possible_move in connect4.possible_drops():
                connect4.drop_token(possible_move, True)
                value = self.minmax(connect4, depth - 1, True, alpha, beta)
                connect4.undo_last_move()
                min_value = min(value, min_value)
                beta = min(beta, value)
                if value <= alpha:
                    break
            return min_value

    def rate_state(self, connect4):
        middle_column = connect4.center_column()
        tokens = 0
        for element in middle_column:
            if element == self.my_token:
                tokens += 1

        return tokens * 0.001
