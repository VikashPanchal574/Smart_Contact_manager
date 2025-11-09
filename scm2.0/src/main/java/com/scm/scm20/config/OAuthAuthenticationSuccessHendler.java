package com.scm.scm20.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.scm20.entities.providers;
import com.scm.scm20.entities.user;
import com.scm.scm20.hellper.AppConstents;
import com.scm.scm20.repository.userRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class OAuthAuthenticationSuccessHendler implements AuthenticationSuccessHandler {

    @Autowired
    private userRepo userRepo;

    Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHendler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        logger.info("OAuthAuthenticationSuccessHendler");

        var oauth2AuthenticateToken = (OAuth2AuthenticationToken) authentication;
        String provider = oauth2AuthenticateToken.getAuthorizedClientRegistrationId();

        var  oauthuser = (DefaultOAuth2User) authentication.getPrincipal();

        oauthuser.getAttributes().forEach((key,value)->{
            logger.info(key + ":" +value);

        }) ;
    

        user user=new user();
        user.setUserId(UUID.randomUUID().toString());
        user.setRoleList(List.of(AppConstents.ROLE_USER));
        user.setEnabled(true);
        user.setEmailVerified(true);
        user.setPassword("Dummy");

        if(provider.equalsIgnoreCase("google")){
            //google
            user.setEmail(oauthuser.getAttribute("email").toString());
            user.setProfilePic(oauthuser.getAttribute("picture").toString());
            user.setName(oauthuser.getAttribute("name").toString());
            user.setProviderId(oauthuser.getName());
            user.setProvider(providers.GOOGLE);
            user.setAbout("this account is created by google");
        
        }
        else if(provider.equalsIgnoreCase("github")){
            // github
            String email = oauthuser.getAttribute("email") !=null ? oauthuser.getAttribute("email").toString()
            :oauthuser.getAttribute("login").toString()+"@gmail.com";
            String picture = oauthuser.getAttribute("avatar_url").toString();
            String name = oauthuser.getAttribute("login").toString();
            String providerId = oauthuser.getName();
        
            user.setEmail(email);
            user.setProfilePic(picture);
            user.setName(name);
            user.setProviderId(providerId);
            user.setProvider(providers.GITHUB);
            user.setAbout("this account is created by github");
            
        }
         user user2 = userRepo.findByEmail(user.getEmail()).orElse(null);

        if (user2 == null) {
            userRepo.save(user);
            logger.info("user saved");
        }

        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");

    }

}
