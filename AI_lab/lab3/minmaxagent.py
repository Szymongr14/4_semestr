class MinMaxAgent:
    def __init__(self, my_token, max_tree_depth):
        self.my_token = my_token
        self.max_tree_depth = max_tree_depth
        self.best_move = None

    def decide(self, connect4) -> int:
        self.minmax(connect4, self.max_tree_depth, True)
        return self.best_move

    def minmax(self, connect4, depth, maximizing_player):
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
                value = self.minmax(connect4, depth - 1, False)
                connect4.undo_last_move()
                if value > max_value:
                    max_value = value
                    if depth == self.max_tree_depth:
                        self.best_move = possible_move
            return max_value
        else:
            min_value = float("+inf")
            for possible_move in connect4.possible_drops():
                connect4.drop_token(possible_move, True)
                value = self.minmax(connect4, depth - 1, True)
                connect4.undo_last_move()
                min_value = min(value, min_value)
            return min_value

    def rate_state(self, connect4):
        current_board = connect4.board
        my_distance = 0
        opponent_distance = 0

        my_tokens_in_fours = 0
        opponent_tokens_in_fours = 0

        # counting distance to the middle
        for i in range(connect4.height):
            for j in range(connect4.width):
                if current_board[i][j] == self.my_token:
                    my_distance += abs(j - connect4.width / 2) + abs(i - connect4.height / 2)
                elif current_board[i][j] != '_':
                    opponent_distance += abs(j - connect4.width / 2) + abs(i - connect4.height / 2)

        # counting how many tokens each player has in a four
        for four in connect4.iter_fours():
            for token in four:
                if token == self.my_token:
                    my_tokens_in_fours += 1
                elif token != '_':
                    opponent_tokens_in_fours += 1

        return (1 / opponent_distance - 1 / my_distance) + (1/opponent_tokens_in_fours*2 - 1/my_tokens_in_fours*2)
