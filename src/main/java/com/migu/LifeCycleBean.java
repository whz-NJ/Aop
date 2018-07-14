package com.migu;

public class LifeCycleBean implements IHello
{
    public LifeCycleBean() {
        System.out.println("LifeCycleBean 构造...");
    }
    //配置文件中init-method="setup"
    public void setup() {
        System.out.println("LifeCycleBean 初始化...");
    }
    //配置文件中destroy-method="teardown"
    public void teardown() {
        System.out.println("LifeCycleBean 销毁...");
    }
    //被代理的方法
    @Override
    public void sayHello()
    {
        System.out.println("hello ,itcast...");
    }
}