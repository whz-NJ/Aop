package com.migu;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyBeanPostProcessor implements BeanPostProcessor
{
    /**
     * bean 代表Spring容器创建对象
     * beanName 代表配置对象对应 id属性
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException
    {
        if (beanName.equals("lifeCycleBean"))
        {
            System.out.println("后处理器 初始化后执行...");
        }
        return bean;
    }

    @Override public Object postProcessBeforeInitialization(final Object bean,
            String beanName) throws BeansException
    {
        // 针对bean id 为 lifeCycleBean的对象 进行代理，配置文件中bean的id为lifeCycleBean就做一下处理
        if (beanName.equals("lifeCycleBean"))
        {
            System.out.println("后处理器 初始化前执行...");
            /*给传进来的bean对象做一个动态代理.bean.getClass().getClassLoader表示要被执行代理的类，也就是我们的IOC容器创建的bean对象。
            bean.getClass().getInterfaces()表示我们的要代理的类所实现的所有的而接口，我们最后new出来的代理类会按照这个参数实现这些所有
            的接口。这也是为什么动态代理模式必须要用接口的原因了。new InvocationHandler() {}表示真正要执行的方法。
            最后用的是return 就是把生成出来的的代理类返回了。所以执行好这个方法后其实的返回的是new 出来的的代理类，而不是之前的bean对象了。
            （这句话非常重要）
            */
            return Proxy.newProxyInstance(bean.getClass().getClassLoader(),
                    bean.getClass().getInterfaces(), new InvocationHandler()
                    {
                        @Override public Object invoke(Object proxy, Method method,
                                Object[] args) throws Throwable
                        {
                            //模拟代理方法（额外要执行的方法）
                            System.out.println("执行代理.....");

                            //执行要真正的方法
                            return method.invoke(bean, args);

                        }
                    });
        }
        return bean;
    }
}