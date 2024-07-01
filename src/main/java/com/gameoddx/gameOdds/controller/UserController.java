package com.gameoddx.gameOdds.controller;


import com.gameoddx.gameOdds.model.User;
import com.gameoddx.gameOdds.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        System.out.println("Req:"+ user);
        return userService.createNewUser(user);
    }
}
