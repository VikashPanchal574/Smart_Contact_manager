package com.scm.scm20.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.scm.scm20.Application;
import com.scm.scm20.services.impl.SecurityCustomUserDetailService;

@Configuration
public class SecurityConfig {

    private final Application application;
    
    @Autowired
    private OAuthAuthenticationSuccessHendler oAuthAuthenticationSuccessHendler;


     @Autowired
     private SecurityCustomUserDetailService securityCustomUserDetailService;

     @Autowired
     private AuthFailureHandler authFailureHandler;
    SecurityConfig(Application application) {
        this.application = application;
    }


     @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        // user detail service ka object:
        daoAuthenticationProvider.setUserDetailsService(securityCustomUserDetailService);
        // password encoder ka object
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }
 

   //url configure for public and private
   @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // configuraton

        // urls configure kiay hai ki koun se public rangenge aur koun se private
        // rangenge
        httpSecurity.authorizeHttpRequests(authorize -> {
            // authorize.requestMatchers("/home", "/register", "/services").permitAll();
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });

        

          
         httpSecurity.formLogin(formLogin -> {
            formLogin.loginPage("/login");
            formLogin.loginProcessingUrl("/authentication");
            formLogin.successForwardUrl("/user/dashboard");
           //formLogin.failureUrl("/login?error=true");
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");
            
            formLogin.failureHandler(authFailureHandler);
        
        });
    
        httpSecurity.csrf(AbstractHttpConfigurer :: disable);
         httpSecurity.logout(logout -> {
            logout.logoutUrl("/logout");
            logout.logoutSuccessUrl("/login?logout=true");

         });

         httpSecurity.oauth2Login(oauth ->{
            oauth.loginPage("/login")
            .successHandler(oAuthAuthenticationSuccessHendler);


         });
    
    
    
    
         return httpSecurity.build();

    


    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}
