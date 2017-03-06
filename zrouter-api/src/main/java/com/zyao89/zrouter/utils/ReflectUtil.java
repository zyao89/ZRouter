package com.zyao89.zrouter.utils;

import com.zyao89.zrouter.exception.ZRouterException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by zyao89 on 2017/3/5.
 * Contact me at 305161066@qq.com or zyao89@gmail.com
 * For more projects: https://github.com/zyao89
 */
public final class ReflectUtil
{
    /**
     * 实例化类
     *
     * @param clazz  类
     * @param params 参数
     * @param <T>    类型
     * @return 对象
     */
    public static <T> T newInstance(Class<T> clazz, Object... params)
    {
        if (clazz == null)
        {
            return null;
        }

        T instance = null;

        int argLength = params == null ? 0 : params.length;
        Class<?>[] paramTypes = new Class[argLength];
        for (int i = 0; i < argLength; i++)
        {
            paramTypes[i] = params[i].getClass();
        }

        try
        {
            Constructor<T> constructor = clazz.getDeclaredConstructor(paramTypes);
            if (!constructor.isAccessible())
            {
                constructor.setAccessible(true);
            }
            instance = constructor.newInstance(params);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return instance;
    }

    /**
     * 反射调用方法
     *
     * @param instance   对象
     * @param methodName 方法名
     * @param params     参数数组
     * @param <T>
     * @return 返回值结果
     */
    public static <T> Object invokeMethod(T instance, String methodName, Object... params)
    {
        if (instance == null)
        {
            return null;
        }

        Object result = null;
        Class<?> clazz = instance.getClass();

        try
        {
            Method method = clazz.getDeclaredMethod(methodName, obj2clz(params));
            if (method != null)
            {
                if (!method.isAccessible())
                {
                    method.setAccessible(true);
                }

                result = method.invoke(instance, params);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 方法参数对象转为类对象
     *
     * @param params 方法参数对象
     * @return 类对象数组
     */
    public static Class<?>[] obj2clz(Object... params)
    {
        if (params == null)
        {
            return null;
        }

        Class<?>[] classes = new Class[params.length];
        for (int i = 0; i < params.length; i++)
        {
            classes[i] = params[i].getClass();
        }

        return classes;
    }

    /**
     * 反射调用方法
     *
     * @param object     反射调用的对象实例
     * @param method     反射调用的对象方法
     * @param methodArgs 反射调用的对象方法的参数列表
     * @return 反射调用执行的结果
     */
    public static Object invokeMethod(Object object, Method method, Object... methodArgs) throws ZRouterException
    {
        if (method == null)
        {
            return null;
        }

        if (!method.isAccessible())
        {
            method.setAccessible(true);
        }

        Object result = null;
        try
        {
            result = method.invoke(object, methodArgs);
        }
        catch (Exception e)
        {
            throw new ZRouterException("Invoke method [" + method.getName() + "] Failed!", e);
        }

        return result;
    }

    /**
     * 获取方法
     * @param clazz 类
     * @param methodName 方法名
     * @param methodParamsClazz 方法参数类型
     * @return 方法
     */
    public static Method getMethod (Class<?> clazz, String methodName, Class<?>... methodParamsClazz)
    {
        try
        {
            return clazz.getDeclaredMethod(methodName, methodParamsClazz);
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
