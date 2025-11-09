package com.scm.scm20.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.scm.scm20.repository.userRepo;

@Service
public class SecurityCustomUserDetailService implements UserDetailsService {

    @Autowired
    private userRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      // loading user
     return userRepo.findByEmail(username)
     .orElseThrow(()-> new UsernameNotFoundException("User not found"));

    }

}
