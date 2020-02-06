package com.example.demo.proxy;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class ClassUtil {

    //添加一个文件夹到类路径
    public static <T> void addClassPath(File classFolder) throws Exception {
        //获取URLClassLoader的addURL方法
        Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        boolean accessible = method.isAccessible();
        try {
            //如果方法没有权限访问，则设置可访问权限
            if (accessible == false) {
                method.setAccessible(true);
            }
            // 设置类加载器
            URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
            // 将当前类路径加入到类加载器中
            method.invoke(classLoader, classFolder.toURI().toURL());
        } finally {
            //把方法的权限设置回去
            method.setAccessible(accessible);
        }
    }
}
