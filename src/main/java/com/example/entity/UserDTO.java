package com.example.entity;

import lombok.Data;

@Data
public class UserDTO {
    /**
     * 姓名
     */
    private String name;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 用户类型，枚举
     */
    private UserTypeEnum userType;
}
