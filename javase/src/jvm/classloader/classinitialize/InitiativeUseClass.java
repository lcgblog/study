package jvm.classloader.classinitialize;

import jvm.classloader.classinitialize.testclass.PassiveUser;

public class InitiativeUseClass {
    /**
     *主动使用：
     *
     *创建类的实例
     *取值或赋值某个类或者接口的静态变量
     *调用类的静态方法
     *反射,如Class.forName(className)
     *初始化类的子类
     *虚拟机启动时被表明为启动类的类（main()）
     *JDK1.7开始支持动态语言,java.lang.invoke.MethodHandle实例解析结果REF_getStatic,REF_putStatic,REF_invokeStatic句柄对应的类，若没有初始化，则初始化
     */

    static {
        System.out.println("InitiativeUseClass: I am invoked");
    }

    public static void main(String[] args)throws Exception {
//        new PassiveUser();

//        System.out.println(PassiveUser.b);
//        PassiveUser.b = 3;

//        PassiveUser.test();

//        Class.forName("jvm.classloader.classinitialize.testclass.PassiveUser");


    }
}
