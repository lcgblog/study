package jvm.classloader.passiveuse.testclass;

public class PassiveParent {
    public static int b = 1;
    static{
        System.out.println("PassiveParent: I am invoked!");
    }
}
