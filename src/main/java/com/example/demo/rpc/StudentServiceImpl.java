package com.example.demo.rpc;

@Service(StudentService.class)
public class StudentServiceImpl implements StudentService{
    @Override
    public Student getInfo() {
        Student person = new Student();
        person.setAge(25);
        person.setName("说出你的愿望吧~");
        person.setSex("男");
        return person;
    }

    @Override
    public boolean printInfo(Student student) {
        if (student != null) {
            System.out.println(student);
            return true;
        }
        return false;
    }
}
