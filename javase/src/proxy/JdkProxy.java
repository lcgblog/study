package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkProxy {

    public static void main(String[] args) {
        UserImpl user = new UserImpl();
        Object userProxy = Proxy.newProxyInstance(UserImpl.class.getClassLoader(),
                UserImpl.class.getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object result = null;
                System.out.println("洗手");
                result = method.invoke(user);
                System.out.println("睡觉");
                return result;
            }
        });
        ((User)userProxy).eat();
        ((User2)userProxy).eat2();
    }

    static class UserImpl implements User,User2{
        public void eat(){
            System.out.println("吃饭");
        }
        public void eat2(){
            System.out.println("吃饭2");
        }
    }

    interface User {
        public void eat();
    }
    interface User2 {
        public void eat2();
    }
}
