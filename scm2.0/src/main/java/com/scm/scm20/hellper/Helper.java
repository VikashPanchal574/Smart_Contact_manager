package com.scm.scm20.hellper;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {

    public static String GetEmailOfLogedInUser(Authentication authentication) {

      
        if (authentication instanceof OAuth2AuthenticationToken) {

            var aOAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
            var clientId = aOAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

            var oauth2user = (OAuth2User) authentication.getPrincipal();
            String username = "";

            if (clientId.equalsIgnoreCase("google")) {
                System.out.println("Getting from Google");
                 username = oauth2user.getAttribute("email").toString();
            
            } else if (clientId.equalsIgnoreCase("github")) {
                System.out.println("Getting from GitHub");
                String email = oauth2user.getAttribute("email") != null ? oauth2user.getAttribute("email").toString()
                        : oauth2user.getAttribute("login").toString() + "@gmail.com";
                        username = oauth2user.getAttribute("email");
            }
            return username;
        } else {
            System.out.println("Getting data from local database");
            return authentication.getName();
        }
        // sign in with google

        // sign in with github

    }


    public static String getLinkForEmailVerification(String emailtoken){
    
        String link = "http://smart-contact-manager.ap-south-1.elasticbeanstalk.com/auth/verify-email?token=" + emailtoken;
    
        return link;
    }
}





