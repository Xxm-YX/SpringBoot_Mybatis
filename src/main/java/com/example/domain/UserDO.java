package com.example.domain;


import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter

public class UserDO{

    private UUID userId;

    private String userName;

    private String pwdHashCode;

    private String saltString;

    private String userTel;


    @Override
    public String toString() {
        return "UserDO{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", pwdHashCode='" + pwdHashCode + '\'' +
                ", saltString='" + saltString + '\'' +
                ", userTel='" + userTel + '\'' +
                '}';
    }
}
