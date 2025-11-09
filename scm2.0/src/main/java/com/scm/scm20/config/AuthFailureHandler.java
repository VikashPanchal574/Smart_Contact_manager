package com.scm.scm20.config;

import java.io.IOException;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.scm.scm20.hellper.MessageType;
import com.scm.scm20.hellper.message;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AuthFailureHandler implements AuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
       if(exception instanceof DisabledException){
        //user is disabled
       HttpSession session = request.getSession();
       session.setAttribute("message", message.builder().content("Account is disabled Email with Account Verification Link sent to your Email id !").type(MessageType.red).build());
       response.sendRedirect("/login");
       }
       else{
       response.sendRedirect("/login?error=true");
    }
}

}
