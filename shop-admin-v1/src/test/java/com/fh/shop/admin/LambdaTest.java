package com.fh.shop.admin;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class LambdaTest {


    @Test
    public void test1(){
        List<String> names = new ArrayList<>();
        names.add("qwer");
        names.add("df");
        names.add("zxcv");

        //名字长度大于3的
        List<String> newNames = names.stream().filter(x -> x.length() > 3).collect(Collectors.toList());

        newNames.forEach(x -> System.out.println(x));
    }

    @Test
    public void test2(){
        List<String> names = new ArrayList<>();
        names.add("qwer");
        names.add("df");
        names.add("zxcv");

        //将集合中的字符串都转换为对应的长度信息，返回一个List<Integer>
        List<Integer> collect = names.stream().map(x -> x.length()).collect(Collectors.toList());
        collect.forEach(x -> System.out.println(x));
    }

    @Test
    public void test3(){
        String ids = "13,55,62,14";
        String[] idArr = ids.split(",");
        List<Long> idList = Arrays.stream(idArr).map(x -> Long.parseLong(x)).collect(Collectors.toList());
        idList.forEach(x -> System.out.println(x));
    }

    @Test
    public void test4(){
        String ids = "13,55,62,14";

        //转换为List<Long> 最后生成的list集合中只包含大于23的数字
        String[] idArr = ids.split(",");
        List<Long> idList = Arrays.stream(idArr).map(x -> Long.parseLong(x)).filter(x -> x>23).collect(Collectors.toList());
        idList.forEach(x -> System.out.println(x));
    }

    @Test
    public void test5(){
        String ids = "13,55,62,14";

        //转换为List<Long> 最后生成的list集合中只包含大于23的数字,并且将所有的数字+10
        String[] idArr = ids.split(",");
        List<Long> idList = Arrays.stream(idArr).map(x -> Long.parseLong(x)).filter(x -> x>23).map(x -> x+10).collect(Collectors.toList());
        idList.forEach(x -> System.out.println(x));
    }

    @Test
    public void test6(){
        String ids = "13,55,12,62,14";

        //转换为List<Long> 排序
        String[] idArr = ids.split(",");
        List<Long> idList = Arrays.stream(idArr).map(x -> Long.parseLong(x)).sorted().collect(Collectors.toList());
        idList.forEach(x -> System.out.println(x));
    }

    @Test
    public void test7(){
        String ids = "13,55,12,12,62,14";

        //转换为List<Long> 排序,删除重复项,
        String[] idArr = ids.split(",");
        List<Long> idList = Arrays.stream(idArr).map(x -> Long.parseLong(x)).sorted().distinct().collect(Collectors.toList());
        idList.forEach(x -> System.out.println(x));
    }

    @Test
    public void test8(){
        String ids = "13,55,12,12,62,14";

        //转换为List<Long> 排序,删除重复项,统计个数
        String[] idArr = ids.split(",");
        long count = Arrays.stream(idArr).map(x -> Long.parseLong(x)).distinct().count();

        System.out.println(count);
    }

    @Test
    public void test9(){
        List<Student> students = new ArrayList<>();

        Student s1 = new Student();
        s1.setName("三岁");
        s1.setAge(18);

        Student s2 = new Student();
        s2.setName("四岁");
        s2.setAge(28);

        Student s3 = new Student();
        s3.setName("万岁");
        s3.setAge(18800);

        Student s4 = new Student();
        s4.setName("九岁");
        s4.setAge(48);

        students.add(s1);
        students.add(s2);
        students.add(s3);
        students.add(s4);

        List<Student> collect = students.stream().skip(1).limit(2).collect(Collectors.toList());
        System.out.println(collect);
    }

    @Test
    public void test10(){
        List<Student> students = new ArrayList<>();

        Student s1 = new Student();
        s1.setName("三岁");
        s1.setAge(18);
        s1.setHobby("吃");

        Student s2 = new Student();
        s2.setName("四岁");
        s2.setAge(28);
        s2.setHobby("写代码");

        Student s3 = new Student();
        s3.setName("万岁");
        s3.setAge(18800);
        s3.setHobby("吃");

        Student s4 = new Student();
        s4.setName("九岁");
        s4.setAge(48);
        s4.setHobby("玩");

        students.add(s1);
        students.add(s2);
        students.add(s3);
        students.add(s4);
        //求年龄最大的学生
        //Student student = students.stream().max((x, y) -> (x.getAge() - y.getAge())).get();
        //求年龄最小的学生
        //Student student2 = students.stream().min((x, y) -> (x.getAge() - y.getAge())).get();
        //System.out.println(student);
        //System.out.println(student2);

//        List<Student> collect = students.stream().skip(1).limit(2).collect(Collectors.toList());
//        System.out.println(collect);

   //按爱好进行分组 Map<String,List<String>>
         Map<String, List<String>> item = new HashMap<>();
         students.stream().collect(Collectors.groupingBy(x -> x.getHobby())).forEach((x,y) -> {
             List<String> collect = y.stream().map(z -> z.getName()).collect(Collectors.toList());
             item.put(x,collect);
         });
        System.out.println(item);


        List<String> names = new ArrayList<>();
        names.add("sdddddsd");
        names.add("sdddddsd");
        names.add("wsdf");
        names.add("wsdwsdeff");
        //过滤重复数据 distinct 去除重复项
        List<String> collect2 = names.stream().distinct().collect(Collectors.toList());
        System.out.println(collect2);

        //将集合转为set类型 set中不允许有重复数据
        Set<String> collect3 = names.stream().collect(Collectors.toSet());
        System.out.println(collect3);

        //每种爱好的人有多少个
        Map<String, Long> collect4 = students.stream().collect(Collectors.groupingBy(x -> x.getHobby(), Collectors.counting()));
        System.out.println(collect4);

        //每种爱好的人的平均年龄
        Map<String, Double> collect5 = students.stream().collect(Collectors.groupingBy(x -> x.getHobby(), Collectors.averagingInt(x -> x.getAge())));
        System.out.println(collect5);
        //Collectors.summingDouble（指定求和的属性 x -> x.getPrice()）:计算总和，通常和分组结合使用
        Map<String, Long> collect6 = students.stream().collect(Collectors.groupingBy(x -> x.getHobby(), Collectors.summingLong(x -> x.getAge())));
        System.out.println(collect6);
        //Collectors.maxBy（） ： 通常和分组结合使用，分组后求各组的最大值
        Map<String, Optional<Student>> collect7 = students.stream().collect(Collectors.groupingBy(x -> x.getHobby(), Collectors.maxBy((x, y) -> (x.getAge() - y.getAge()))));
        Map<String, Student> stu = new HashMap<>();
        collect7.forEach((x,y) -> {
            stu.put(x,y.get());
        });
        System.out.println(stu);
    }

}
