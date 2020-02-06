package com.example.demo.thread.threadstate;

public class ThreadStateTest {
    public static void main(String[] args) {
        Thread t1 = new Thread(new ThreadState1());
        Thread t2 = new Thread(new ThreadState2());
        t1.start();
        t2.start();
    }
}
