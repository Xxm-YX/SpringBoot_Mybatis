package com.example.entity;

import com.example.anno.MyJsonCreator;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Getter
@Slf4j
public enum UserTypeEnum{

    STUDENT(1, "学生"),
    TEACHER(2, "老师"),
    ;

//
    private final Integer type;
    @MyJsonCreator
    private final String desc;

    UserTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    /**
     * 强制指定按哪个字段进行反序列化
     * @return
     */
//    @Override
//    public String getValue() {
//        return this.desc;
//    }

    /**
     * 静态方法+@JsonCreator指定根据哪个字段反序列化(将字节码转换成对象，就传入的描述来换成对象)
//     * @param desc
     * @return
     */
    @JsonCreator
    public static UserTypeEnum getEnum(String desc){
        for (UserTypeEnum item : values()) {
            if(item.getDesc().equals(desc)){
                log.info("进来了, desc:{}, item:{}", desc, item.toString());
                return item;
            }
        }
        return null;
    }

    /**
     * 统一序列化，序列化成 type值
     * @return
     */
    @Override
    public String toString() {
       return String.valueOf(this.type);
    }

    public static void main(String[] args) throws IOException {
        // 模拟Postman发送JSON请求
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "{\n" +
                "    \"name\": \"bravoPostJson\",\n" +
                "    \"age\": 18,\n" +
                "    \"userType\": \"老师\"\n" +
                "}";
        System.out.println("aaa"+json);

        // 请求：反序列化
        UserDTO userDTO = objectMapper.readValue(json, UserDTO.class);
        System.out.println("bbb"+userDTO);

        // 响应：序列化
        String returnJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(userDTO);
        System.out.println("ccc"+returnJson);

    }
}