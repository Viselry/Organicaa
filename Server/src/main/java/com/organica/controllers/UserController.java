package com.organica.controllers;

import com.organica.entities.User;
import com.organica.payload.SingIn;
import com.organica.payload.UserDto;
import com.organica.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<UserDto> CreateUser(@RequestBody UserDto userDto){
        System.out.println(userDto);
        UserDto userDto1 = this.userService.CreateUser(userDto);

        return new ResponseEntity<>(userDto1, HttpStatusCode.valueOf(200));
    }


    @PostMapping("/signin")
    public ResponseEntity<SingIn> CreateUser(@RequestBody SingIn singIn){
        SingIn singIn1 = this.userService.SingIn(singIn);
        System.out.println(singIn1);
        return new ResponseEntity<>(singIn1, HttpStatusCode.valueOf(200));
    }
}
