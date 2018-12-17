package com.efida.sports.dmp.excel;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.evake.excel.annotation.ExcelAttribute;

/**
 * 测试对象
 * @author wang yi
 * @desc 
 * @version $Id: TestModel.java, v 0.1 2018年8月16日 上午10:55:08 wang yi Exp $
 */
public class TestModel {
    @ExcelAttribute(name = "ID", column = "B")
    private Long    id;

    @ExcelAttribute(name = "姓名", column = "C", isMark = true)
    private String  name;

    @ExcelAttribute(name = "年龄", column = "D", isMark = true)
    private Integer age;

    @ExcelAttribute(name = "生日", column = "E")
    private Date    birthday;

    @ExcelAttribute(name = "备注", column = "G")
    private String  mark;

    public TestModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "TestModel [id=" + id + ", name=" + name + ", age=" + age + ", birthday="
               + new SimpleDateFormat("yyyy-MM-dd").format(birthday) + ", mark=" + mark + "]";
    }

}
