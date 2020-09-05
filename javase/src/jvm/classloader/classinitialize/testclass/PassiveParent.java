package jvm.classloader.classinitialize.testclass;

public class PassiveParent {
    public static int b = 1;
    static{
        System.out.println("PassiveParent: I am invoked!");
    }

    public static void testParent(){

    }
}
