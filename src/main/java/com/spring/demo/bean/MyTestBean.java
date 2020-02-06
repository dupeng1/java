package com.spring.demo.bean;

/**
 * bean是Spring中最核心的东西，因为Spring就像是个大水桶，而bean就像是容器中的水，水桶脱离了水便也没什么用处了，当前类是bean的定义
 * 这么看来bean并没有什么特别之处，的确，Spring的目的就是让我们的bean能成为一个纯粹的POJO，这也是Spring所追求的。
 */
public class MyTestBean {
    private String testStr = "testStr";

    public String getTestStr() {
        return testStr;
    }

    public void setTestStr(String testStr) {
        this.testStr = testStr;
    }
}
