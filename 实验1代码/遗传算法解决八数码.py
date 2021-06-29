import copy
 
import numpy as np
import random
import time
import operator
 

def init():
    init_state = np.array([0, 1, 2, 3, 4, 5, 6, 7, 8])
    target_state = np.array([0, 1, 2, 3, 4, 5, 6, 7, 8])
    np.random.shuffle(init_state)
    np.random.shuffle(target_state)
    init_state = np.reshape(init_state, (3, 3))
    target_state = np.reshape(target_state, (3, 3))
    return init_state, target_state
 
def fitness_algorithm_single(init_state, target_state):
    total_distance = 0
    for i in range(1, 9):
        (init_row, init_column) = np.where(init_state == i)
        (target_row, target_column) = np.where(target_state == i)
        total_distance += abs(target_row - init_row) + abs(target_column - init_column)
    return int(total_distance)
 
 

def move_and_fitness(num, current_state, target_state):
    temp_state = copy.deepcopy(current_state)
    zero_row, zero_column = np.where(temp_state == 0)
    zero_row, zero_column = int(zero_row), int(zero_column)
    if num == 0:  
        if zero_row - 1 >= 0:  
            up_row, up_column = zero_row - 1, zero_column
            temp = temp_state[up_row][up_column]
            temp_state[zero_row][zero_column] = temp
            temp_state[up_row][up_column] = 0
            fitness = fitness_algorithm_single(temp_state, target_state)
            return fitness, temp_state
        else:
            fitness = 10000
            return fitness, temp_state
    if num == 1:
        if zero_row + 1 <= 2:
            down_row, down_column = zero_row + 1, zero_column
            temp = temp_state[down_row][down_column]
            temp_state[zero_row][zero_column] = temp
            temp_state[down_row][down_column] = 0
            fitness = fitness_algorithm_single(temp_state, target_state)
            return fitness, temp_state
        else:
            fitness = 10000
            return fitness, temp_state
    if num == 2:
        if zero_column - 1 >= 0:
            left_row, left_column = zero_row, zero_column - 1
            temp = temp_state[left_row][left_column]
            temp_state[zero_row][zero_column] = temp
            temp_state[left_row][left_column] = 0
            fitness = fitness_algorithm_single(temp_state, target_state)
            return fitness, temp_state
        else:
            fitness = 10000
            return fitness, temp_state
    if num == 3:
        if zero_column + 1 <= 2:
            right_row, right_column = zero_row, zero_column + 1
            temp = temp_state[right_row][right_column]
            temp_state[zero_row][zero_column] = temp
            temp_state[right_row][right_column] = 0
            fitness = fitness_algorithm_single(temp_state, target_state)
            return fitness, temp_state
        else:
            fitness = 10000
            return fitness, temp_state
 
 
def fitness_algorithm_for_population(init_state, target_state, population_list):
    for i in population_list:
        temp_state = copy.deepcopy(init_state)
        min_fitness = 10000
        for j in i:
            fitness, temp_state = move_and_fitness(j, temp_state, target_state)
            if fitness < min_fitness:
                min_fitness = fitness
        i.append(min_fitness)
    return population_list
 

def init_solution(L):

    list = []
    for i in range(L):
        list.append(random.randrange(0, 4))
    return list
 
 

def init_population(M, L):
    """
    :param M: 种群大小
    :param L: 解的长度，即基因长度
    :return: M个种群组成的列表
    """
    population_list = []
    for i in range(M):
        population_list.append(init_solution(L))
    return population_list
 
def select_algorithm(population_list):
    total = 0
    for i in population_list:
        fitness = i[-1]
        fitness = -1 * fitness + 100
        total += fitness
    for j in population_list:
        fitness = j[-1]
        fitness = -2 * fitness + 100
        select_prob = fitness / total
        j.append(select_prob)
    population_list = sorted(population_list, key=lambda key: key[-1], reverse=True)
    return population_list
 

def retain_and_eliminate(list, retain_prob, eliminate_prob):
    retain_list = []
    length = len(list)
    retain_index = int(length * retain_prob)
    for i in range(retain_index):
        temp = copy.deepcopy(list[i])
        retain_list.append(temp)
    eliminate_index = int(length * eliminate_prob)
    
    for j in range(eliminate_index):
        list.pop()
    for k in retain_list:
        del k[-1]
        del k[-1]
    return retain_list, list
 
def cross_algorithm(origin_list, M):
    new_list = []
    for i in range(M):
        mother, father = sa_roulette(origin_list)
        son = []
        split_index = random.randrange(0, len(mother) - 2)  
        for j in range(split_index):
            son.append(mother[j])
        for k in range(split_index, len(mother) - 2):
            son.append(father[k])
        new_list.append(son)
    return new_list
 
 
def roulette_algorithm(list):
    total_probability = 0
    p = random.random()
    for i in list:
        total_probability = total_probability + i[-1]
        if total_probability > p:
            break
    return i
 
 
def sa_roulette(list):
  
    count = 0
    temp = list[0]
    for i in list:
        if operator.eq(temp, i):
            count += 1
    if count == len(list):
        mother = list[0]
        father = list[1]
        return mother, father
    # *******************************************************************************************
    mother = roulette_algorithm(list)
    while True:
        father = roulette_algorithm(list)
        if operator.ne(mother, father):
            break
    return mother, father
 
def mutation_algorithm(list, mutation_prob):
    for i in list:
        for j in range(len(i) - 1):
            if random.random() < mutation_prob:
                direction = random.randrange(0, 4)
                i[j] = direction
    return list
 
 
def SA_algorithm(T=200, M=20, L=50, retain_prob=0.3, eliminate_prob=0.3, mutation_prob=0.3):
    
    init_state, target_state = init()
    print("初始状态为:\n", init_state)
    print("目标状态为:\n", target_state)
    population_list = init_population(M, L)
    print("种群初始化为")
    for j in population_list:
        print(j)
    for i in range(T):
        print("当前是第{0}代".format(i))
        population_list = fitness_algorithm_for_population(init_state, target_state, population_list)  
        for j in population_list:
            print(j)
        for i in population_list:
            if i[-1] == 0:  
                return True
        population_list = select_algorithm(population_list)  
        print("当前种群状态为：")
        for j in population_list:
            print(j)
        retain_list, population_list = retain_and_eliminate(population_list, retain_prob,
                                                            eliminate_prob)  
        print("直接保留的样本为：")
        for j in retain_list:
            print(j)
        print("淘汰后剩下的样本为：")
        for j in population_list:
            print(j)
        population_list = cross_algorithm(population_list, M - len(retain_list))  # 交叉
        print("交叉后产生的样本为:")
        for j in population_list:
            print(j)
        population_list = mutation_algorithm(retain_list + population_list, mutation_prob)  # 变异
        print("变异后产生的样本为")
        for j in population_list:
            print(j)
 
    return False
 
 
def sa_algorithm_test(T, M, L, retain_prob, eliminate_prob, mutation_prob, num):
    success_case = 0
    fail_case = 0
    tic = time.time()
    for i in range(num):
        if SA_algorithm(T, M, L, retain_prob, eliminate_prob, mutation_prob):
            success_case += 1
            print("第{0}个例子发现最优解".format(i))
        else:
            fail_case += 1
            print("第{0}个例子没有发现最优解".format(i))
    toc = time.time()
    print("{0}个例子中成功解决的例子为：{1}".format(num, success_case))
    print("{0}个例子成功解决的百分比为：{1}".format(num, success_case / num))
    print("{0}个例子中失败的例子为：{1}".format(num, fail_case))
    print("{0}个例子失败的百分比为：{1}".format(num, fail_case / num))
    print("{0}个例子运行算法所需的时间为：{1}秒".format(num, toc - tic))
 
 
sa_algorithm_test(T=100, M=30, retain_prob=0.3, eliminate_prob=0.3, mutation_prob=0.03, num=100, L=200)