package com.example.entity;

import com.example.anno.EnumValue;
import lombok.Getter;

@Getter
public enum WeekDayEnum {
    MONDAY(1,"星期一"),
    TUESDAY(2,"星期二"),
    WEDNESDAY(3,"星期三"),
    THURSDAYDay(4,"星期四"),
    FRIDAY (5,"星期五"),
    SATURDAYDay(6,"星期六"),
    SUNDAY (7,"星期天")
    ;

    //标记最终把code作为枚举的值插入数据库
    @EnumValue
    private final Integer code;
    private final String desc;

    WeekDayEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
