n = int(input())

array = []

for i in range(n):
    x, l, r = map(int, input().split())
    array.append([x, l, r])

root = int(input())


def rec(array, cur_point):
    if ((array[cur_point - 1][1] != -1 and  
         array[array[cur_point - 1][1] - 1][0] >= array[cur_point - 1][0])
        or 
        (array[array[cur_point - 1][2] - 1][0] <= array[cur_point - 1][0])
        and array[cur_point - 1][2] != -1):
        return False
    else:
        if ((array[cur_point - 1][1] == -1 or rec(array, array[cur_point - 1][1])) 
            and (array[cur_point - 1][2] == -1 or rec(array, array[cur_point - 1][2]))):
            return True
    return False

print("YES" if rec(array, root) else "NO")