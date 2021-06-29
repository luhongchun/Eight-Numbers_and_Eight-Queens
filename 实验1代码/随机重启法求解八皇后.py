import random
import math
from queue import PriorityQueue
import time
class EQ(object):

    def __init__(self):
       self.Astare = 0
       self.stimulatingAnneale = 0
       self.randomClimbHille = 0
       self.randomClimbHille_time = 0
       self.climbinge = 0

       self.puzzle = [(0, 0)]*8
       self.new()
       
       for i in range(100):
          print ("----------", i+1, "----------")
        
          self.new_state()
          self.randomClimbHill()
      

       print ("The Success rate of random-climbing is :", self.randomClimbHille, "%")
       print ("The Totaltime of random-climbing is :", self.randomClimbHille_time, "s")

    def climbHill(self):
       best = []
       next = self.getNeighbor(self.puzzle)
       min = self.getEvaluation(self.puzzle)
       if min == 0:
           return -1
       for state in next:
           now = self.getEvaluation(state)
           if now <= min:
              min = now
              best = state
       if len(best) != 0:
          self.puzzle = best

    def climbing(self):
       totalCosts = 0
       last = self.puzzle
       while True:
           if totalCosts > 10000:
              return
           totalCosts += 1
           test = self.climbHill()
           if last == self.puzzle:
              break
           last = self.puzzle
       if self.getEvaluation(self.puzzle) == 0:
          self.climbinge += 1
       print ("End state for climbing: ", self.puzzle)
       print ("Total costs for climbing: ", totalCosts)

    def randomClimbHill(self):
       last = self.puzzle
       totalCosts = 0
    #    totaltime = 0
       tic = time.time()
       while True:
           totalCosts += 1
           test = self.climbHill()
           if last == self.puzzle:
              if test == -1:
                 break
              else:
                 self.new_state()
           last = self.puzzle
           if totalCosts > 40000:
              return
       if self.getEvaluation(self.puzzle) == 0:
          self.randomClimbHille += 1
       toc = time.time()  
       print ("End state for random-climbing: ", self.puzzle)
       print ("Total costs for random-climbing: ", totalCosts)
       print("所需的时间为：",toc - tic)
       self.randomClimbHille_time += toc - tic
    def stimulatingAnneal(self):
       totalCosts = 0
       T = 0
       while True:
           # print (self.puzzle)
           T += 0.1
           next = self.getNeighbor(self.puzzle)
           now = self.getEvaluation(self.puzzle)
           if now == 0:
              break
           ra = random.randint(0, len(next) - 1)
           choose = next[ra]
           nextCost = self.getEvaluation(choose)
           deltaE = nextCost - now

           if nextCost == 0:
              self.puzzle = choose
              break
           if deltaE < 0:
              self.puzzle = choose
              totalCosts += 1
           else:
              bound = math.exp(-deltaE / T)
              compare = random.random()
              if compare >= bound:
                 self.puzzle = choose
                 totalCosts += 1
           if T > 10000:
              return
       if self.getEvaluation(self.puzzle) == 0:
          self.stimulatingAnneale += 1
       print ("End state for annealing: ", self.puzzle)
       print ("Total costs for annealing: ", totalCosts)

    def Astar(self):
       open = []
       openValue = []
       close = []
       closeValue = []
       pQ = PriorityQueue()
       totalCosts = 0

       pQ.put((self.getEvaluation(self.puzzle), self.puzzle))
       open.append(self.puzzle)
       openValue.append(0)
       while not pQ.empty():
           element = pQ.get()
           totalCosts += 1
           # print (element[1])
           if element[1] not in close:
             close.append(element[1])
             closeValue.append(element[0])
           for i in self.getNeighbor(element[1]):
              if i not in close and i not in open:
                 evaluate = self.getEvaluation(i)
                 open.append(i)
                 openValue.append(evaluate)
                 pQ.put((evaluate, i))
              elif i not in close and i in open:
                 evaluate = self.getEvaluation(i)
                 if evaluate < openValue[open.index(i)]:
                    openValue[open.index(i)] = evaluate
                    pQ.put((evaluate, i))
              elif i in close:
                 evaluate = self.getEvaluation(i)
                 if evaluate < closeValue[close.index(i)]:
                    closeValue.remove(closeValue[close.index(i)])
                    close.remove(i)
                    open.append(i)
                    openValue.append(evaluate)
                    pQ.put((evaluate, i))
           if totalCosts > 400:
              return

           if element[0] == 0:
              self.puzzle = element[1]
              break
       if self.getEvaluation(self.puzzle) == 0:
          self.Astare += 1
       print ("End state for Astar: ", self.puzzle)
       print ("Total costs for Astar: ", totalCosts)

    def getNeighbor(self, puzzle):
       next = []
       for i in range(8):
           for j in range(i + 1, 8):
              new = puzzle[:]
              swap = new[i]
              new[i] = (swap[0], new[j][1])
              new[j] = (new[j][0], swap[1])
              next.append(new)
       # print (next)
       return next

    def getEvaluation(self, puzzle):
       Cost = 0
       for i in range(8):
           attack = puzzle[i]
           for j in range(i + 1, 8):
              # if attack[1] == puzzle[i][1]:
              #    Cost += 1
              if abs(attack[0] - puzzle[j][0]) == abs(attack[1] - puzzle[j][1]):
                 Cost += 1
       return Cost

    def new(self):
       for i in range(8):
          self.puzzle[i] = (i + 1, i + 1)

    def new_state(self):
       puzzle = [(0, 0)] * 8
       for i in range(8):
           puzzle[i] = (i + 1, i + 1)
       for i in range(8):
           t1 = random.randint(1, 7)
           t2 = random.randint(1, 7)
           if t1 != t2:
              swap = puzzle[t1]
              puzzle[t1] = (swap[0], puzzle[t2][1])
              puzzle[t2] = (puzzle[t2][0], swap[1])

       self.puzzle = puzzle
       return self.puzzle

if __name__=="__main__":
    EQ()