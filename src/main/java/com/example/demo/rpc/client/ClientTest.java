package com.example.demo.rpc.client;

import com.example.demo.rpc.Student;
import com.example.demo.rpc.StudentService;
import org.junit.Test;

public class ClientTest {
    @Test
    public void test(){
        RpcClientProxy proxy = new RpcClientProxy("127.0.0.1", 9998);
        StudentService service = proxy.getProxy(StudentService.class);
        System.out.println(service.getInfo());
        Student student = new Student();
        student.setAge(23);
        student.setName("hashmap");
        student.setSex("男");
        System.out.println(service.printInfo(student));
    }
}
