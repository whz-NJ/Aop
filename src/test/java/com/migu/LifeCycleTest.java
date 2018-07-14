package com.migu;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LifeCycleTest {
    @Test
    public void testInitDestroy() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        IHello lifeCycleBean = (IHello) applicationContext.getBean("lifeCycleBean");
        System.out.println(lifeCycleBean);
        lifeCycleBean.sayHello();

        // 必须手动调用 容器销毁的方法 --- web服务器tomcat，自动调用容器销毁
        applicationContext.close();
    }
}