package com.fh.shop.admin.po;

import lombok.Data;
@Data
public class Employee {


        //value与数据库主键列名一致，若实体类属性名与表主键列名一致可省略value
        private Integer id;
        //若没有开启驼峰命名，或者表中列名不符合驼峰规则，可通过该注解指定数据库表中的列名，exist标明数据表中有没有对应列
        private String lastName;
        private String email;
        private Integer gender;
        private Integer age;

}
