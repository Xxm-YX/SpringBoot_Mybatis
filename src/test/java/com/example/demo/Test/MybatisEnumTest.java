package com.example.demo.Test;

import com.example.dao.UserMapper;
import com.example.domain.UserDO;
import com.example.domain.UserLoginDTO;
import com.example.entity.WeekDayEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class MybatisEnumTest {

    @Autowired
    private UserMapper userMapper;

//    @Test
//    public void testInsert() {
//        UserDO userDO = new UserDO();
//        userDO.setName("MyBatis枚举测试");
//        userDO.setAge(18);
//        userDO.setRestDay(WeekDayEnum.FRIDAY);
//
//        userMapper.insertUser(userDO);
//    }
//
//    @Test
//    public void testSelect() {
//        UserDO userDO = userMapper.selectUserById(1L);
//        System.out.println(userDO);
//    }

    @Test
    public void testClone() throws JsonProcessingException {
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setUserName("aaa");
//        userLoginDTO.setAuthCode("bbb");
        userLoginDTO.setPwdHashCode("cccc");

        UserDO userDO =  new UserDO();
//        System.out.println(userDO);
//        userDO = userLoginDTO;
//        System.out.println(userDO);
//        System.out.println(userDO.getUserName());
//        System.out.println(userDO.getPwdHashCode());


        ObjectMapper objectMapper = new ObjectMapper();
        UserDO userDO1 = objectMapper.readValue(objectMapper.writeValueAsString(userLoginDTO), UserDO.class);

        System.out.println(userDO1);
    }

    @Test
    public void testUUID(){
        for (int i = 0; i < 5; i++) {

            UUID uuid = UUID.randomUUID();
            System.out.println(uuid);
        }
    }
}
