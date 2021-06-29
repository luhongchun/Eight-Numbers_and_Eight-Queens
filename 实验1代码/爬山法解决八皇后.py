import copy
import numpy as np
import random
import time
 
def init():
    cache = {}
    m = np.zeros((8, 8), dtype=int)
    for i in range(0, 8):
        temp = random.randrange(0, 8)
        m[temp, i] = 1
        cache["queen" + str(i)] = [temp, i]
    return m, cache
 
 
def compute_weight_single(coord_cache):
    weight = 0
    for i in range(0, 8):
        x, y = coord_cache["queen" + str(i)]
        for j in range(i + 1, 8):
            _x, _y = coord_cache["queen" + str(j)]
            if _x - x == j - i or _x - x == i - j:
                weight += 1
            if _x == x:
                weight += 1
    return weight
 
def compute_weight_matrix(coord_cache):
    weight_matrix = np.zeros((8, 8))
    for i in range(0, 8):
        for j in range(0, 8):
            temp_coord_cache = coord_cache.copy()
            temp_coord_cache["queen" + str(i)] = [j, i]
            weight = compute_weight_single(temp_coord_cache)
            weight_matrix[j, i] = weight
    return weight_matrix
 
 
def next_move(cache, weight_matrix):
    coord_cache = cache
    min = np.min(weight_matrix)
    for i in range(0, 8):
        for j in range(0, 8):
            if weight_matrix[j, i] == min:
               
                coord_cache["queen" + str(i)] = [j, i]
                return coord_cache
 
 
def draw(coord_cache):
    m = np.zeros((8, 8), dtype=int)
    for i in range(8):
        row, column = coord_cache["queen" + str(i)]
        row, column = int(row), int(column)
        m[row][column] = 1
    return m
 
 
def climbing_algorithm(a):
    l = 0
    a = 0
    m, coord_cache = init()
    while True:
        weight = compute_weight_single(coord_cache)  
       
        if weight == 0: 
            a = 1
            return l,a
        weight_matrix = compute_weight_matrix(coord_cache)  
        if weight_matrix.min() >= weight:
            a = 0
            return l,a
        else:
            coord_cache = next_move(coord_cache, weight_matrix)  
            l += 1
            
def climbing_algorithm_test(num):
    tic = time.time()
    success_case = 0
    fail_case = 0
    move = 0
    a = 0
    for i in range(num):
        l,a = climbing_algorithm(a)
        if a == 1:
            print("第{0}个例子成功找到最优解".format(i))
            success_case += 1
            move = move+l
        else:
            print("第{0}个例子失败".format(i))
            fail_case += 1
    toc = time.time()
    print("{0}个例子中成功解决的例子为：{1}".format(num, success_case))
    print("{0}个例子成功解决的百分比为：{1}".format(num, success_case / num))
    print("{0}个例子中失败的例子为：{1}".format(num, fail_case))
    print("{0}个例子失败的百分比为：{1}".format(num, fail_case / num))
    print("{0}个例子运行算法所需的时间为：{1}秒".format(num, toc - tic))
    print("成功例子平均移动步数为：{0}".format( move/ success_case))

climbing_algorithm_test(1000)
