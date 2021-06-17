package com.example.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(/*callSuper = true*/)
public class UserLoginDTO extends UserDO {

    @JsonIgnore
    private String authCode;


}
