package com.scm.scm20.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.scm20.services.UserService;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    private Logger logger = LoggerFactory.getLogger(UserController.class);

   
    @RequestMapping(value = "/dashboard")
    public String userdashboard() {
        System.out.println("user deshboard");
        return "user/dashboard";
    }

    @RequestMapping(value = "/profile")
    public String userprofile() {
         
        System.out.println("user profile");
        return "user/profile";
    }



}
