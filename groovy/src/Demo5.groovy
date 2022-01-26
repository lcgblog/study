//闭包
c = {
    Integer a,String b,c -> println([a,b,c])
}
println c.getMaximumNumberOfParameters()
println c.getParameterTypes()
//调用
c 1,'b','c'
c.call(1,'b','c')
c (1,'b','c')
//curry
d = c.curry(3)
d 'b','c'
