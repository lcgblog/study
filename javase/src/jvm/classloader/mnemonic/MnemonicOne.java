package jvm.classloader.mnemonic;

public class MnemonicOne {
    public static void main(String[] args) {
        System.out.println(Child.a);
        System.out.println(Child.b);
        System.out.println(Child.c);
        System.out.println(Child.d);
        System.out.println(Child.f);
        System.out.println(Child.g);
        System.out.println(Child.g2);
        System.out.println(Child.h);
        System.out.println(Child.i);
        System.out.println(Child.i2);
        System.out.println(Child.j);
        System.out.println(Child.k);
        System.out.println(Child.l);
        System.out.println(Child.m);
        System.out.println(Child.m2);
        System.out.println(Child.test1);
    }
}

/**
 * javap -c className得到反编译汇编指令
 *
 * ldc - 将 int/float/String 常量值从常量池推送至栈顶
 * bipush - 将单字节(-128 - 127) 常量值推送至栈顶
 * sipush - 将短整型(两个字节2^16)（-32768 - 32767）常量值推送至栈顶
 * iconst_1 将int型1推送至栈顶 （0-5对应iconst_0 - iconst_5） -1 对应iconst_m1, 其他的是ldc
 * fconst_1 将float型1推送至栈顶(0-2对应fconst_0-fconst_2) ,其他的是ldc
 * lconst_1 将long型1推送至栈顶(0-1对应lconst_0,lconst_1),其他是ldc2_w
 * dconst_1 将double型1推送至栈顶(0-1对应dconst_0,dconst_1)，其他是ldc2_w
 */
class Child{
    public static final int a = 5; // -1 ~ 5 => iconst_m1,iconst_0,iconst_1,...,iconst_5
    public static final int b = 127; // -128 ~ -2, 6 ~ 127 => bipush xxx
    public static final int c = -129; // -32768 ~ -129, 128 ~ 32767 => sipush xxx
    public static final int d = 32768; // ldc #ref
    public static final float f = 1; // 0-2 => fconst_0,fconst_1,fconst_2 其他的是ldc
    public static final long g = 1;// 0-1 =》 lconst_0,lconst_1,其他是ldc2_w
    public static final long g2 = 123;// 自动转换为123L => ldc2_w #8
    public static final String h = "123"; // String => ldc
    public static final short i = 1;//自动转换为int型 => iconst_1
    public static final short i2 = 123;//自动转换为int型 => bipush
    public static final byte j = 127; // //自动转换为int型 => bipush
    public static final char k = 0; //自动转为int => iconst_0
    public static final boolean l = true; // 自动转为int false => iconst_0 , true => iconst_1
    public static final double m = 1; //  0 => dconst_0 , 1=> dconst_1
    public static final double m2 = 123; //  自动转为123.0d => ldc2_w #15

    public static int test1 = 1; //getstatic #17 说明没有final不会使用之前的那些助记符，至于有什么区别，暂时还不知道

}

