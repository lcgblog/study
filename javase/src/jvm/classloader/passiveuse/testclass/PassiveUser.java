package jvm.classloader.passiveuse.testclass;

public class PassiveUser extends jvm.classloader.passiveuse.testclass.PassiveParent {
    public static final int a = 1;
    static{
        System.out.println("PassiveUser: I am invoked!");
    }
}
