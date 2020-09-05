package jvm.classloader.classinitialize.testclass;

public class PassiveUser extends jvm.classloader.classinitialize.testclass.PassiveParent {
    public static final int a = 1;
    public static int b = 2;
    static{
        System.out.println("PassiveUser: I am invoked!");
    }

    public static void test(){

    }
}
