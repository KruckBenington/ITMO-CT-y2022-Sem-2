n = int(input())
array = []
crossings = 0
answer = [0, 0]
check_stack = []
is_OK = 1

for i in range(n):
    h1, m1, s1, h2, m2, s2 = map(int, input().split())
    crossings = crossings + 1 if h1 * 3600 + m1 * 60 + s1 >= h2 * 3600 + m2 * 60 + s2  else crossings
    array.append([h1 * 3600 + m1 * 60 + s1, 1, i])
    array.append([h2 * 3600 + m2 * 60 + s2, -1, i])

array.sort()

for i in range(len(array)): 
    crossings += array[i][1]
    if (crossings == n):
        answer[1] = array[i][0]
    if (i == len(array) - 1 and crossings == n):
        answer[0] += 86400 - answer[1]
    if (array[i][1] == -1 and crossings == n - 1):
        answer[0] += array[i][0] - answer[1]


print(answer[0])
