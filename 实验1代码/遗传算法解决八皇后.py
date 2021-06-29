import numpy as np
import random
import time
import operator
 
def init():
    cache = {}
    m = np.zeros((8, 8), dtype=int)
    for i in range(0, 8):
        temp = random.randrange(0, 8)
        m[temp, i] = 1
        cache["queen" + str(i)] = [temp, i]
    return m, cache
 
 
def fitness_algorithm_single(coord_cache):
    weight = 0
    for i in range(0, 8):
        row, column = coord_cache["queen" + str(i)]
        for j in range(i + 1, 8):
            _row, _column = coord_cache["queen" + str(j)]
            if _row - row == j - i or _row - row == i - j:
                weight += 1
            if _row == row:
                weight += 1
    return 28 - weight  
    
def fitness_algorithm_for_list(list):
    for i in list:
        fitness = fitness_algorithm_single(i)
        i["fitness"] = [fitness]
    return list
 
 
def select_probability(list):
    total_fitness = 0
    specimen_list = list
    for i in specimen_list:
        fitness = i["fitness"]
        total_fitness += fitness[0]
    for j in specimen_list:
        fitness = j["fitness"]
        j["select"] = [fitness[0] / total_fitness]
    return specimen_list
 
 
def init_population(M):
    specimen_list = []  
    for i in range(M):
        m, coord_cache = init()
        specimen_list.append(coord_cache)
    specimen_list = fitness_algorithm_for_list(specimen_list)
   
    specimen_list = sorted(specimen_list, key=lambda keys: keys["fitness"], reverse=True)  
    specimen_list = select_probability(specimen_list) 
    return specimen_list
 
def roulette_algorithm(list):
    total_probability = 0
    p = random.random()
    for i in list:
        total_probability = total_probability + i["select"][0]
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
 
 
def cross_algorithm(origin_list, M):
    new_list = []
    for i in range(M):
        mother, father = sa_roulette(origin_list)
        son = {}
        split_index = random.randrange(0, 8)
        for j in range(split_index):
            son["queen" + str(j)] = father["queen" + str(j)]
        for k in range(split_index, 8):
            son["queen" + str(k)] = mother["queen" + str(k)]
        new_list.append(son)
    return new_list
 
 

def retain_and_eliminate(list, retain_prob, eliminate_prob):
    retain_list = []
    length = len(list)
    
    retain_index = int(length * retain_prob)
    for i in range(retain_index):
        retain_list.append(list[i])
    eliminate_index = int(length * eliminate_prob)
    
    for j in range(eliminate_index):
        list.pop()
    return retain_list, list
 
 
# 基因变异函数,每个样本的皇后位置会根据mutation_prob的概率随机调整
def mutation_algorithm(list, mutation_prob):
    for i in range(len(list)):
        if random.random() < mutation_prob:
            row = random.randrange(0, 8)
            column = random.randrange(0, 8)
            list[i]["queen" + str(column)] = [row, column]
 
    return list
 
 
def screening_population(population_list):
    for i in population_list:
        if fitness_algorithm_single(i) == 28:
            return True
    return False
 
 
def SA_algorithm(T=50, M=10, retain_prob=0.3, eliminate_prob=0.3, mutation_prob=0.1):
    """
    :param T: 最大繁殖代数
    :param M: 种群大小
    :param retain_prob: 每一代中优良基因直接保留的百分比
    :param eliminate_prob: 每一代中淘汰的百分比
    :param mutation_prob: 变异百分比
    :return:bool
    """
    population_list = init_population(M)  
    print("初始种群的状态为：", )
    for j in population_list:
        print(j)
    for i in range(T):
        if screening_population(population_list):
            return True
        print("当前种群的状态为：",)
        for j in population_list:
            print(j)
        retain_list, left_list = retain_and_eliminate(population_list, retain_prob, eliminate_prob)  # 保留优良基因和淘汰适应度低的基因
        print("直接保留的个体为：")
        for j in retain_list:
            print(j)
        print("淘汰之后剩余的个体")
        for j in left_list:
            print(j)
        cross_list = cross_algorithm(left_list, M - len(retain_list))  # 基因交叉,因为保留了一部分优良基因，这里产生的基因等于M-保留的基因数
        print("交叉产生的新个体为:")
        for j in cross_list:
            print(j)
        population_list = mutation_algorithm(retain_list + cross_list, mutation_prob)  # 基因变异
        population_list = fitness_algorithm_for_list(population_list)
        population_list = select_probability(population_list)
        population_list = sorted(population_list, key=lambda keys: keys["fitness"], reverse=True)  # 降序
        print("变异后产生的种群为:")
        for j in population_list:
            print(j)
    return False
 
 
def sa_algorithm_test(T, M, retain_prob, eliminate_prob, mutation_prob, num=1000):
    success_case = 0
    fail_case = 0
    tic = time.time()
    for i in range(num):
        if SA_algorithm(T, M, retain_prob, eliminate_prob, mutation_prob):
            success_case += 1
            print("第{0}个例子发现最优解".format(i))
        else:
            fail_case += 1
            print("第{0}个例子失败".format(i))
    toc = time.time()
    print("{0}个例子中成功解决的例子为：{1}".format(num, success_case))
    print("{0}个例子成功解决的百分比为：{1}".format(num, success_case / num))
    print("{0}个例子中失败的例子为：{1}".format(num, fail_case))
    print("{0}个例子失败的百分比为：{1}".format(num, fail_case / num))
    print("{0}个例子运行算法所需的时间为：{1}秒".format(num, toc - tic))
 
 
sa_algorithm_test(T=300, M=50, retain_prob=0.3, eliminate_prob=0.3, mutation_prob=0.3, num=1000)