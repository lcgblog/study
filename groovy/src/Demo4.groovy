class Filter{
    private String a;
    private String b;

    def sayHello(){
        println a + ' ' + b
    }
}

Filter filter1 = new Filter(a:"hello", b:"tom")
filter1.sayHello()

def sayHello = filter1.&sayHello
sayHello()

