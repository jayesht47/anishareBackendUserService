package com.anishare.userservice.controller;

import com.anishare.userservice.DTO.AnimeListDTO;
import com.anishare.userservice.VO.Anime;
import com.anishare.userservice.entity.User;
import com.anishare.userservice.exceptions.DuplicateUserNameException;
import com.anishare.userservice.exceptions.UserNotFoundException;
import com.anishare.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;



    @GetMapping("/test")
    public String testController(){
        return "Hello from userService";
    }

    @GetMapping("/user/{userId}")
    public User getUserById(@PathVariable("userId") Long userId) throws UserNotFoundException {
        User userInDb =  userService.findUserByUserId(userId);

        if(userInDb == null)
        {
            throw new UserNotFoundException(userId);
        }

        log.info("User Details :: "+ userInDb);
        return userInDb;
    }

    @PostMapping("/saveUser")
    public User saveUser(@RequestBody User user) throws DuplicateUserNameException {
        return userService.saveUser(user);
    }

    @PostMapping("/saveAnimeList/{userId}")
    public void saveUserList(@RequestBody AnimeListDTO animeList, @PathVariable("userId") Long userId) throws DuplicateUserNameException {
        userService.saveUserList(animeList, userId);
    }

    @GetMapping("getAnimeList/{userId}")
    public List<Anime> getUserAnimeList(@PathVariable("userId") Long userId) {
        return userService.getUserAnimeList(userId);
    }

}
