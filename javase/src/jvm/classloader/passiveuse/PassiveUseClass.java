package jvm.classloader.passiveuse;

import jvm.classloader.passiveuse.testclass.PassiveUser;

public class PassiveUseClass {
    public static void main(String[] args) {
        System.out.println(PassiveUser.a);//访问放入常量池的静态变量，被动使用，该类不会被初始化
        PassiveUser[] passiveUser = new PassiveUser[10];//定义数组，被动使用，该类不会被初始化
        System.out.println(PassiveUser.b);//访问父类的静态变量，自动被动使用，子类不会被初始化
    }
}
