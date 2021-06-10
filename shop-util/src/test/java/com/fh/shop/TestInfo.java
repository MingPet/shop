package com.fh.shop;


import com.fh.shop.common.Info;
import com.fh.shop.common.Student;
import org.junit.Test;

public class TestInfo {

    @Test
    public void test1(){
        Student student = new Student();
        student.setAge(11);
        student.setStuName("wqwewe");
        Info<Student> success = Info.success(student);
        Student data = success.getData();

        System.out.println(data+":"+data);
    }
}
