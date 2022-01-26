arr = [1..10]
println arr.class
println arr.toList()
println arr[0].toList()

arr2 = 1..10
println arr2.class
println arr2.toList()

arr3 = (1..10)
println arr3.class
println arr3.toList()

arr4 = (1)
println arr4.class

arr5 = [1,2,3]
println arr5.class
println arr5

arr6 = [1,2,3] as LinkedList
println arr6.class
println arr6