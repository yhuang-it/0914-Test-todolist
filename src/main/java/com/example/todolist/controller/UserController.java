package com.example.todolist.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.todolist.entity.User;
import com.example.todolist.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("api/v1/registration")
    public String register(@RequestBody User user) {
        return userService.register(user);
    }
}
