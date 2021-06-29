import copy
 
import numpy as np
import random
import time
import math
 
def init():
    init_state = np.array([0, 1, 2, 3, 4, 5, 6, 7, 8])
    target_state = np.array([0, 1, 2, 3, 4, 5, 6, 7, 8])
    np.random.shuffle(init_state)
    np.random.shuffle(target_state)
    init_state = np.reshape(init_state, (3, 3))
    target_state = np.reshape(target_state, (3, 3))
    return init_state, target_state
 
 
def compute_manhattan_distance(init_state, target_state):
    total_distance = 0
    for i in range(1, 9):
        (init_row, init_column) = np.where(init_state == i)
        (target_row, target_column) = np.where(target_state == i)
        total_distance += abs(target_row - init_row) + abs(target_column - init_column)
    return int(total_distance)
 
 
def compute_manhattan_distance_all(init_state, target_state):
    distances = {}
    coord_cache = {}
    zero_row, zero_column = np.where(init_state == 0)
    if zero_row - 1 >= 0:  
        up_row, up_column = zero_row - 1, zero_column
        coord_cache["up"] = [up_row, up_column]
    if zero_row + 1 <= 2:
        down_row, down_column = zero_row + 1, zero_column
        coord_cache["down"] = [down_row, down_column]
    if zero_column - 1 >= 0:
        left_row, left_column = zero_row, zero_column - 1
        coord_cache["left"] = [left_row, left_column]
    if zero_column + 1 <= 2:
        right_row, right_column = zero_row, zero_column + 1
        coord_cache["right"] = [right_row, right_column]
    for i in coord_cache.keys():  
        temp = init_state.copy()
        temp[zero_row, zero_column] = temp[coord_cache[i][0], coord_cache[i][1]]
        temp[coord_cache[i][0], coord_cache[i][1]] = 0
        distances[i] = compute_manhattan_distance(temp, target_state)
    return distances
 
 
def minimal_manhattan_distance(distances):
    temp = copy.deepcopy(distances)
    minimal_value = 1000
    for i in temp.keys():
        if distances[i] < minimal_value:
            minimal_value = distances[i]
    return minimal_value
 
def next_move(init_state, distances):
    current_state = init_state.copy()
    zero_row, zero_column = np.where(current_state == 0)
    minimal_value = 1000
    key = ""
    for i in distances.keys():
        if distances[i] < minimal_value:
            minimal_value = distances[i]
            key = i
    if key == "up":  
        print("向上移动")
        up_row, up_column = zero_row - 1, zero_column
        temp = current_state[up_row, up_column]
        current_state[zero_row, zero_column] = temp
        current_state[up_row, up_column] = 0
    if key == "down":
        print("向下移动")
        down_row, down_column = zero_row + 1, zero_column
        temp = current_state[down_row, down_column]
        current_state[zero_row, zero_column] = temp
        current_state[down_row, down_column] = 0
    if key == "left":
        print("向左移动")
        left_row, left_column = zero_row, zero_column - 1
        temp = current_state[left_row, left_column]
        current_state[zero_row, zero_column] = temp
        current_state[left_row, left_column] = 0
    if key == "right":
        print("向右移动")
        right_row, right_column = zero_row, zero_column + 1
        temp = current_state[right_row, right_column]
        current_state[zero_row, zero_column] = temp
        current_state[right_row, right_column] = 0
    return current_state
 
def climbing_algorithm(a):
    l = 0
    a = 0
    init_state, target_state = init()
    print("初始状态为：\n", init_state)
    print("目标状态为：\n", target_state)
    while True:
        current_manhattan_distance = compute_manhattan_distance(init_state, target_state)  
        print("当前状态距离目标的曼哈顿距离为：", current_manhattan_distance)
        if current_manhattan_distance == 0:  
            a = 1
            return l,a
        distances = compute_manhattan_distance_all(init_state, target_state)  
        print("distances:", distances)
        print("distances里面的最小值为：", minimal_manhattan_distance(distances))
        if current_manhattan_distance <= minimal_manhattan_distance(distances):
            a = 0
            return l,a  
        init_state = next_move(init_state, distances)
        l += 1
        print("移动后的新状态为：\n", init_state)
 
 
def climbing_algorithm_test(num):
    tic = time.time()
    success_case = 0
    fail_case = 0
    move = 0
    a = 0
    for i in range(num):
        print("第{0}个例子启动".format(i))
        l,a = climbing_algorithm(a)
        if a == 1:
            success_case += 1
            move = move+l
            print("第{0}个例子成功找到最优解".format(i))
        else:
            fail_case += 1
            print("第{0}个例子失败".format(i))
    toc = time.time()
    print("{0}个例子中成功解决的例子为：{1}".format(num, success_case))
    print("{0}个例子成功解决的百分比为：{1}".format(num, success_case / num))
    print("{0}个例子中失败的例子为：{1}".format(num, fail_case))
    print("{0}个例子失败的百分比为：{1}".format(num, fail_case / num))
    print("{0}运行算法所需的时间为：{1}秒".format(num, toc - tic))
    print("成功例子平均移动步数为：{0}".format(move/ success_case))
 
climbing_algorithm_test(1000)