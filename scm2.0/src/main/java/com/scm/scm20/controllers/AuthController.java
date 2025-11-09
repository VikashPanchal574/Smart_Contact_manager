package com.scm.scm20.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.scm20.entities.user;
import com.scm.scm20.hellper.MessageType;
import com.scm.scm20.hellper.message;
import com.scm.scm20.repository.userRepo;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private userRepo userRepo;

    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam("token") String token , HttpSession session){
        
        user user = userRepo.findByEmailToken(token).orElse(null);

        if(user != null){
            if(user.getEmailToken().equals(token)){

                user.setEmailVerified(true);
                user.setEnabled(true);
                userRepo.save(user);
                session.setAttribute("message", message.builder().type(MessageType.green).content("Email Verified ! Now You Can Access your Account ").build());

                return "success_Page";
            }
            session.setAttribute("message", message.builder().type(MessageType.red).content("Email Not Verified ! Token is not asssociated with user ").build());

            return "error_page";
            
        }
        session.setAttribute("message", message.builder().type(MessageType.red).content("Email Not Verified ! Token is not asssociated with user ").build());
        
        return "error_page";

    }
}
    
