n = int(input())
array = []

array = [int(x) for x in input().split()]
array.sort()


print(n)
for i in range(n):
    right_ch = (i + 2)
    if (i == n - 1):
        right_ch = -1
    print(str(array[i]) + ' ' + str(-1) + ' ' + str(right_ch))
print(1)