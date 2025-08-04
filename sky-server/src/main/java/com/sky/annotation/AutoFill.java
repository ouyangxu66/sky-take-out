package com.sky.annotation;

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解:用于标识某个方法是否需要公共字段自动填充
 */
@Target(ElementType.METHOD)//：指定该注解只能用于方法上
@Retention(RetentionPolicy.RUNTIME)//：指定该注解在运行时保留，可以通过反射获取
public @interface AutoFill {
    //OperationType:枚举类,指定数据库操作类型:UPDATE INSERT
    OperationType value();



}
