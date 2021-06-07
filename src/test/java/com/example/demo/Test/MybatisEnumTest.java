package com.example.demo.Test;

import com.example.dao.UserMapper;
import com.example.entity.UserDO;
import com.example.entity.WeekDayEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MybatisEnumTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testInsert() {
        UserDO userDO = new UserDO();
        userDO.setName("MyBatis枚举测试");
        userDO.setAge(18);
        userDO.setRestDay(WeekDayEnum.FRIDAY);

        userMapper.insertUser(userDO);
    }

    @Test
    public void testSelect() {
        UserDO userDO = userMapper.selectUserById(1L);
        System.out.println(userDO);
    }
}
