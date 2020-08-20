package com.example.demo.core.concurrent;

import org.junit.Test;
import sun.misc.Unsafe;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
* @Title: UnsafeStudy
* @Description: java中的魔法类sun.misc.Unsafe，Unsafe为我们提供了访问底层的机制，这种机制仅供java核心类库使用，而不应该被普通用户使用
* @author peng.du@haiziwang.com
* @date 2020/8/20 9:35
* @version V1.0
* Copyright 2020 Kidswant Children Products Co., Ltd.
*/
public class UnsafeStudy {
    public static Unsafe getUnsafe() throws IllegalAccessException, NoSuchFieldException {
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe)f.get(null);
        return unsafe;
    }

    /**
     * 使用Unsafe实例化一个类
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @Test
    public void instanceTest() throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        User user = new User();
        System.out.println(user.age);
        Unsafe unsafe = getUnsafe();
        User user2 = (User)unsafe.allocateInstance(User.class);
        System.out.println(user2.age);
        //Unsafe.allocateInstance()只会给对象分配内存，并不会调用构造方法，所以这里只会返回int类型的默认值0
    }

    /**
     * 修改私有字段的值
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    @Test
    public void modifyPrivateFieldTest() throws NoSuchFieldException, IllegalAccessException {
        Unsafe unsafe = getUnsafe();
        User user = new User();
        Field age =  user.getClass().getDeclaredField("age");
        unsafe.putInt(user, unsafe.objectFieldOffset(age), 20);
        System.out.println(user.age);
        //一旦我们通过反射调用得到字段age，我们就可以使用Unsafe将其值更改为任何其他int值
    }

    /**
     * 抛出异常，我们知道如果代码抛出了checked异常，要不就用try...catch捕获它，要不就在方法签名上定义这个异常，但是通过Unsafe我们就可以抛出一个checked异常，同时却不用
     * 捕获或在方法签名上定义它
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    @Test
    public void throwExceptionTest() throws NoSuchFieldException, IllegalAccessException {
        Unsafe unsafe = getUnsafe();
        unsafe.throwException(new IOException());
    }

    /**
     * 如果进程在运行过程中JVM上的内存不足了，会导致频繁的进行GC。理想情况下，我们可以考虑使用堆外内存，这是一块不受JVM管理的内存
     * 使用Unsafe的allocateMemory()我们可以直接在堆外分配内存，这可能非常有用，但我们要记住，这个内存b不受JVM管理，因此我们要调用freeMemory()方法手动释放它
     */
    @Test
    public  void allocateTest() {
        OffHeapArray offHeapArray = new OffHeapArray(4);
        offHeapArray.set(0 ,1);
        offHeapArray.set(1, 2);
        offHeapArray.set(2, 3);
        offHeapArray.set(3, 4);
        offHeapArray.set(2, 5);
        int sum = 0;
        for (int i = 0; i < offHeapArray.size(); i++) {
            sum += offHeapArray.get(i);
        }
        System.out.println(sum);
        offHeapArray.freeMemory();
    }

    /**
     * JUC下面大量使用了CAS操作，他们的底层是调用的Unsafe的CompareAndSwapXXX()方法，这种方式广泛运用于无锁算法，与Java中标准的悲观锁机制相比，它可以利用CAS处理器指令提供极大的加速
     * @throws InterruptedException
     */
    @Test
    public void compareTest() throws InterruptedException {
        Counter counter = new Counter();
        ExecutorService threadPool = Executors.newFixedThreadPool(100);
        IntStream.range(0, 100).forEach(i ->threadPool.submit(() -> IntStream.range(0, 10000).forEach(j -> counter.increment())));
        threadPool.shutdown();
        Thread.sleep(2000);
        System.out.println(counter.getCount());
    }

    /**
     *JVM在上下文切换的时候使用了Unsafe中的两个非常牛逼方法park()和unpark()
     * 当一个线程正在等待某个操作时，JVM调用Unsafe的park()方法来阻塞此线程
     * 当阻塞中的线程需要再次运行时，JVM调用Unsafe的unpark()方法来唤醒此线程
     */
    @Test
    public void parkTest() throws NoSuchFieldException, IllegalAccessException {
        Unsafe unsafe = getUnsafe();
        unsafe.park(true, 1);
        unsafe.unpark(null);

    }



    class User{
        int age;
        public User(){
            this.age = 10;
        }
    }

    static class OffHeapArray{
        //一个int等于4个字节
        private static final int INT = 4;
        private long size;
        private long address;

        private static Unsafe unsafe;

        {
            try {
                unsafe = getUnsafe();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        //构造方法=，分配内存
        public OffHeapArray(long size) {
            this.size = size;
            //参数字节数
            address = unsafe.allocateMemory(size * INT);
        }

        public int get(long i) {
            return unsafe.getInt(address + i * INT);
        }

        public void set(long i, int value) {
            unsafe.putInt(address + i * INT, value);
        }

        public long size() {
            return size;
        }

        public void freeMemory() {
            unsafe.freeMemory(address);
        }
    }

    static class Counter {
        private volatile int count = 0;
        private static long offset;
        private static Unsafe unsafe;
        static {
            try {
                unsafe = getUnsafe();
                offset = unsafe.objectFieldOffset(Counter.class.getDeclaredField("count"));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        public void increment() {
            int before = count;
            while(!unsafe.compareAndSwapInt(this, offset, before, before + 1)) {
                before = count;
            }
        }

        public int getCount() {
            return count;
        }
    }
}
