package com.example.demo.rpc;

public interface StudentService {
    /**
     * 获取信息
     * @return
     */
    public Student getInfo();

    /**
     * 打印student的信息并返回一个boolean值
     * @param student
     * @return
     */
    public boolean printInfo(Student student);
}
