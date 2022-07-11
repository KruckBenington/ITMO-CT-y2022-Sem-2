n, m = map(int, input().split())

array = []

for i in range(n):
    l, r = map(int, input().split())
    array.append([min(l, r), -1, 0])
    array.append([max(l, r), 1, 0])
    
s = [int(x) for x in input().split()]
    
for i in range(len(s)):
    array.append([s[i], 0, i])

answer = [0] * m

lines_crossing = 0

array.sort()

for i in range(len(array)):
    lines_crossing -= array[i][1]
    if (array[i][1] == 0):
        answer[array[i][2]] = lines_crossing 

for i in range(m):
    print(answer[i], end = ' ')
    