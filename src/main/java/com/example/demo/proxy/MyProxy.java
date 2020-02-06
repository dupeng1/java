package com.example.demo.proxy;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Iterator;
import java.util.Set;

public class MyProxy {
    private static final String CLASS_BASE_PATH = "D:\\test";
    private static final String PACKAGE_NAME = "com.example.demo.proxy.generate";
    public static <T> T newProxyInstance(Class<T> clazz, InvocationHandler invocationHandler) {
        String proxyClassName = clazz.getSimpleName() + "$MyProxy";
        try {
            //一、 生成java文件
            generateProxyJavaFile(clazz, proxyClassName);

            //二、 编译
            compileJavaFile();

            //三、 添加class文件所在目录添加到类路径
            ClassUtil.addClassPath(new File(CLASS_BASE_PATH));

            //四、 加载类并创建对象
            Class proxyClass = Class.forName(PACKAGE_NAME + "." + proxyClassName);
            return (T) proxyClass.getConstructor(InvocationHandler.class).newInstance(invocationHandler);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //生成代理类的java文件
    private static void generateProxyJavaFile(Class clazz, String proxyClassName) throws IOException {

        //构造一个类，实现传入接口，addSuperinterface功能是添加一个实现接口
        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(proxyClassName).addSuperinterface(clazz);
        //构建一个属性，用于保存执行对象
        FieldSpec fieldSpec = FieldSpec.builder(InvocationHandler.class, "handler", Modifier.PRIVATE).build();

        //添加到类中
        classBuilder.addField(fieldSpec);

        //构建一个构造器，初始化执行对象
        MethodSpec constructor = MethodSpec.constructorBuilder()
                //添加权限修饰符
                .addModifiers(Modifier.PUBLIC)
                //添加参数
                .addParameter(InvocationHandler.class, "handler")
                //方法体内容
                .addStatement("this.handler = handler")
                .build();

        //把构造器添加到类中
        classBuilder.addMethod(constructor);

        //获取传入接口的所有公有方法（自己的，外部可以访问的方法，不包括继承的方法）
        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            MethodSpec.Builder methodSpec = MethodSpec.methodBuilder(method.getName())
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(Override.class)
                    .returns(method.getReturnType());
            //生成handler.invoke()执行语句（实际执行方法），添加到方法体
            StringBuilder invokeString = new StringBuilder("\tthis.handler.invoke(this, " + clazz.getName() + ".class.getMethod(\"" + method.getName() + "\",");
            //存储执行方法参数列表
            StringBuilder paramNames = new StringBuilder();
            //这部分如果看不太懂，可以对照生成后的java文件
            for (Parameter parameter : method.getParameters()) {
                //添加外部方法参数列表
                methodSpec.addParameter(parameter.getType(), parameter.getName());
                //添加实际执行方法中
                invokeString.append(parameter.getType() + ".class, ");
                //存到执行方法参数列表中
                paramNames.append(parameter.getName() + ",");
            }
            //把最后一个逗号替换为)
            int lastCommaIndex = invokeString.lastIndexOf(",");
            invokeString.replace(lastCommaIndex, invokeString.length(), "), ");
            lastCommaIndex = paramNames.lastIndexOf(",");
            paramNames.replace(lastCommaIndex, lastCommaIndex + 1, ")");
            //把属性名追加到最后一个参数列表
            invokeString.append(paramNames);
            //添加方法体，执行InvocationHandler的invoke方法，并抓取异常
            methodSpec.addCode("try{\n");
            methodSpec.addStatement(invokeString.toString());
            methodSpec.addCode("} catch (NoSuchMethodException e) {\n");
            methodSpec.addCode("\te.printStackTrace();\n");
            methodSpec.addCode("}\n");

            //添加到类中
            classBuilder.addMethod(methodSpec.build());
        }

        //生成java文件，第一个参数是包名
//        String path = MyProxy.class.getResource("/").toString();
        JavaFile javaFile = JavaFile.builder(PACKAGE_NAME, classBuilder.build()).build();

        //把java文件写到执行路径下（默认会把包生成文件夹）
        javaFile.writeTo(new File(CLASS_BASE_PATH));
    }
    //把java文件编译成.class文件
    private static void compileJavaFile() throws FileNotFoundException {
        //1.获取javac编译器
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        //2.通过javac编译器获取一个编译java文件管理器
        StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);

        //3.获取java文件对象
        //-调用一个工具类，从指定路径下，递归获取所有指定后缀的文件
        Set<File> javaFiles = FileUtil.getFilesForSuffixx(new File(CLASS_BASE_PATH), ".java");
        //-这里就一个java文件，就直接使用了
        Iterator<File> iterator = javaFiles.iterator();
        Iterable<? extends JavaFileObject> it = manager.getJavaFileObjects(iterator.next().getAbsoluteFile());

        //4.编译
        JavaCompiler.CompilationTask task = compiler.getTask(null, manager,
                null, null, null, it);
        task.call();
    }
}
