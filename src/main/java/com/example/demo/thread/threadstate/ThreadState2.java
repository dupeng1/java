package com.example.demo.thread.threadstate;

import sun.misc.Unsafe;

public class ThreadState2 implements Runnable {

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
        }catch (Exception e){

        }
        System.out.println("ThreadState2---begin---lock");
        synchronized (ThreadStateTest.class){
            try {
                System.out.println("ThreadState2---get---lock");
                Thread.sleep(20000);
            }catch (Exception e){

            }
            ThreadStateTest.class.notify();
        }
        System.out.println("ThreadState2---end");
    }
}
