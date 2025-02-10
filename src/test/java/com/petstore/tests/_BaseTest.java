package com.petstore.tests;

import com.petstore.listeners.ListenerClass;
import com.petstore.listeners.MethodInterceptor;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import java.lang.reflect.Method;

@Listeners(value = {
        ListenerClass.class, MethodInterceptor.class})
public class _BaseTest {

    @BeforeMethod
    public void beforeMethod(Method method) {
        System.out.println("STARTING TEST: " + method.getName());
        System.out.println("THREAD ID: " + Thread.currentThread().getId());
    }
}
