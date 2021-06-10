package com.fh.shop.admin;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class JdkTest {

         List<Student> list = Arrays.asList(
                new Student(1,"初一", "男",20, 18, "吃"),
                new Student(2,"十五", "女",233, 14, "玩"),
                new Student(3,"二月", "男",1562, 20, "睡")
        );


    @Test
    public void test0() {
        list.stream();
    }

    @Test
    public void test1() {
        list.forEach(System.out::println);
    }

    @Test
    public void test3() {
        List<String> names = list.stream().map(Student::getName).collect(Collectors.toList());
        names.stream().forEach(System.out::println);
    }

    @Test
    public void test4() {
        IntStream intStream = list.stream().mapToInt(Student::getPhone);
        Stream<Integer> integerStream = intStream.boxed();
        Optional<Integer> max   = integerStream.max(Integer::compareTo);
        System.out.println(max.get());
    }

    @Test
    public void test5() {
        //flatMap合并成一个流
        //将流中的每一个元素 T 映射为一个流，再把每一个流连接成为一个流
        List<String> list2 = new ArrayList<>();
        list2.add("aaa bbb ccc");
        list2.add("ddd eee fff");
        list2.add("ggg hhh iii");
        list2.add("ggg hhh iii");
        list2 = list2.stream().map(s -> s.split(" ")).flatMap(Arrays::stream).collect(Collectors.toList());
        System.out.println(list2);
    }

    @Test
    public void test6() {
        //去除重复项
        List<String> list2 = new ArrayList<>();
        list2.add("aaa bbb ccc");
        list2.add("ddd eee fff");
        list2.add("ggg hhh iii");
        list2.add("ggg hhh iii");

        list2.stream().distinct().forEach(System.out::println);
    }

    @Test
    public void test7() {
        // sorted排序
        //asc排序
        list.stream().sorted(Comparator.comparingInt(Student::getPhone)).forEach(System.out::println);
        System.out.println("------------------------------------------------------------------");
        //desc排序
        list.stream().sorted(Comparator.comparingInt(Student::getPhone).reversed()).forEach(System.out::println);
    }

    @Test
    public void test8() {
        //skip跳过几个 limit取第几个
        list.stream().skip(1).limit(1).forEach(System.out::println);
    }

    @Test
    public void test9() {
        //只要有其中任意一个符合条件 返回true或false
        boolean isHave = list.stream().anyMatch(student -> student.getPhone() == 19);
        System.out.println(isHave);
    }

    @Test
    public void test10() {
        //count计数
        long count = list.stream().count();
        System.out.println(count);
    }

    @Test
    public void test11(){
        // 求平均值
        Double average = list.stream().collect(Collectors.averagingLong(Student::getAge));
        System.out.println(average);
    }

    @Test
    public void test12(){
        ArrayList<Student> stuList = new ArrayList<>();
        Student stu = new Student();
        stu.setName("麻子");
        stu.setAge(30);
        stu.setHobby("写代码");

        Student stu2 = new Student();
        stu2.setName("麻子trdf");
        stu2.setAge(38);
        stu2.setHobby("睡觉");

        stuList.add(stu);
        stuList.add(stu2);

        stuList.forEach(x -> System.out.println("姓名："+x.getName()+"；年龄："+ x.getAge()+"；爱好："+x.getHobby()));
    }

    @Test
    public void test13(){
        Map<String,String> map = new HashMap<>();
        map.put("name","张三");
        map.put("age","16");
        map.put("sex","男");

//        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
//        while (iterator.hasNext()){
//            Map.Entry<String, String> next = iterator.next();
//            System.out.println(next.getKey() + ":"+next.getValue());
//
//        }
        //map.forEach((x,y) -> System.out.println(x+":"+y));


//        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
////        while (iterator.hasNext()){
////            Map.Entry<String, String> next = iterator.next();
////            System.out.println(next.getKey()+":");
////            System.out.println(next.getValue()+";;;;;");
////        }

        map.forEach((x,y) ->{
            System.out.println(x+":");
            System.out.println(y+";");
        });


    }

    @Test
    public void test14() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "张三");
        map.put("age", "16");
        map.put("sex", "男");

        Map<String, String> map2 = new HashMap<>();
        map2.put("name", "张三22");
        map2.put("age", "25");
        map2.put("sex", "男");

        Map<String, String> map3 = new HashMap<>();
        map3.put("name", "张三33");
        map3.put("age", "17");
        map3.put("sex", "女");

        List<Map> userList = new ArrayList<>();
        userList.add(map);
        userList.add(map2);
        userList.add(map3);

        userList.forEach(x -> x.forEach((xx,yy)-> System.out.println(xx+":"+yy)));

    }

    @Test
    public void test15() {
        List<String> names = new ArrayList<>();
        names.add("qqqqq");
        names.add("wwwww");
        names.add("eeeee");
        names.removeIf(x -> x.equals("qqqqq"));
        names.forEach(x -> System.out.println(x));

    }

    @Test
    public void test16(){
        ArrayList<Student> stuList = new ArrayList<>();
        Student stu = new Student();
        stu.setName("麻子");
        stu.setAge(30);
        stu.setHobby("写代码");

        Student stu2 = new Student();
        stu2.setName("麻子trdf");
        stu2.setAge(38);
        stu2.setHobby("睡觉");

        Student stu3 = new Student();
        stu3.setName("色的法国");
        stu3.setAge(18);
        stu3.setHobby("吃");

        stuList.add(stu);
        stuList.add(stu2);
        stuList.add(stu3);

        //删除年龄大于20的学生
        stuList.removeIf(x -> x.getAge() < 20);
        stuList.forEach(x -> System.out.println(x.getName()+";"+x.getAge()));


    }
}



























