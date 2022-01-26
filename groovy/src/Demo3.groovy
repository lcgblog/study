def func(a,b){
    a+b
}

println func(1,2)
println func (1,2)
//println func 1,2 //compile error
println ({
    func 3,2
}())
println '------'
def func2(a,b){
    println(a)
    println(b)
}

func2 3,4

println '------'

def "func3:3"(){
    println "func3"
    "func4"
}
println "func3:3"()

println '------'

def method(Closure c){
    result = c.call()
    println result
    println result.class
}

method{
    given:'aaa'
}


println '------'

def func4(a,b,c){
    println([a,b,c])
}
def args = [1,2]
func4(*args,3)
