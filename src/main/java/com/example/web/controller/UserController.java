package com.example.web.controller;

import com.example.entity.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
//@RequestMapping("/api/web/user")
public class UserController {

    @GetMapping("/zyx")
    public UserDTO get(UserDTO userDTO) {
        log.info(userDTO.toString());
        return userDTO;
    }

    @PostMapping("/postForm")
    public UserDTO postForm(UserDTO userDTO) {
        log.info(userDTO.toString());
        return userDTO;
    }

    @PostMapping("/postJson")
    public  UserDTO postJson(@RequestBody UserDTO userDTO) {
        log.info(userDTO.toString());
        return userDTO;
    }

    @GetMapping("/")
    public String test(){
        return "aaa";
    }
}
