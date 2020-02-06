package com.example.demo.thread.threadstate;

public class ThreadState1 implements Runnable{
    @Override
    public void run() {
        System.out.println("ThreadState1---begin---lock");
        synchronized (ThreadStateTest.class){
            try {
                System.out.println("ThreadState1---get---lock");
                System.out.println("ThreadState1---begin---wait");
                ThreadStateTest.class.wait(10000);
                System.out.println("ThreadState1---end---wait");
            }catch (Exception e){

            }
        }
    }
}
