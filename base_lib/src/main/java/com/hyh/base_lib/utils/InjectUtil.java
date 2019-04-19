package com.hyh.base_lib.utils;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;

import com.hyh.base_lib.annotation.FindViewByIdAno;
import com.hyh.base_lib.annotation.OnClick;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


public class InjectUtil {

    public static void injectView(Object activityOrFragment) {
        Class<?> activityOrFragmentClass = activityOrFragment.getClass();
        if (activityOrFragmentClass != null) {
            Field[] declaredFields = activityOrFragmentClass.getDeclaredFields();
            if (declaredFields != null) {
                for (Field field : declaredFields) {
                    FindViewByIdAno findViewByIdAno = field.getAnnotation(FindViewByIdAno.class);
                    if (findViewByIdAno != null) {
                        int id = findViewByIdAno.value();
                        Object fieldObj = null;
                        try {
                            fieldObj = activityOrFragmentClass.getMethod("findViewById", int.class)
                                    .invoke(activityOrFragment, id);
                            field.setAccessible(true);
                            field.set(activityOrFragment, fieldObj);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public static void injectEvent(final Fragment activityOrFragment) {
        Class<?> clazz = activityOrFragment.getClass();
        //获取所有方法（私有方法也可以获取到）
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            //获取方法上面的OnClick注解
            OnClick click = method.getAnnotation(OnClick.class);
            //有则继续下面代码
            if (click != null) {
                //获取注解中的数据，因可以给多个button绑定点击事件，因此定义的注解类时使用的是int[]数据类型
                int[] viewId = click.value();
                method.setAccessible(true);
                //设置一个代理对象,当调用setOnClickListener时，把代理对象传进去，当点击发生时，就会invoke方法，可以调用带有onClick注解的method方法
                Object listener = Proxy.newProxyInstance(View.OnClickListener.class.getClassLoader(),
                        new Class[]{View.OnClickListener.class}, new InvocationHandler() {
                            @Override
                            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                                return method.invoke(activityOrFragment, args);
                            }
                        });
                try {
                    for (int id : viewId) {
                        //获取相应的控件
                        Object v = clazz.getMethod("findViewById", int.class)
                                .invoke(activityOrFragment, id);
                        Method setClickListener = v.getClass().getMethod("setOnClickListener", View.OnClickListener.class);
                        setClickListener.invoke(v, listener);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
