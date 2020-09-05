package jvm.classloader.classinitialize.testclass;

public class PassiveUser extends jvm.classloader.classinitialize.testclass.PassiveParent {
    public static final int a = 1;
    static{
        System.out.println("PassiveUser: I am invoked!");
    }
}
