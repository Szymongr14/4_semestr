import random

n = 100000

X = [1, 2, 3, 4]
F_X = [0.5, 0.7, 0.9, 1]
F_Y_1 = [0.2, 0.2, 0.2, 1]
F_Y_2 = [1, 1, 1, 1]
F_Y_3 = [0, 0.5, 0.5, 1]
F_Y_4 = [0, 0, 1, 1]

x_temp = 0
y_temp = 0

wyniki = [[0 for _ in range(4)] for _ in range(4)]

for _ in range(n):
    y = random.random()
    for j in range(4):
        if y < F_X[j]:
            x_temp = X[j]
            if x_temp == 1:
                F = F_Y_1
            elif x_temp == 2:
                F = F_Y_2
            elif x_temp == 3:
                F = F_Y_3
            else:
                F = F = F_Y_4

            y2 = random.random()
            for i in range(4):
                if y2 < F[i]:
                    y_temp = X[i]
                    break
            break
    wyniki[x_temp-1][y_temp-1] +=1


print("   1 ", " 2 ", " 3 ", " 4 ")
print("1", wyniki[0])
print("2", wyniki[1])
print("3", wyniki[2])
print("4", wyniki[3])