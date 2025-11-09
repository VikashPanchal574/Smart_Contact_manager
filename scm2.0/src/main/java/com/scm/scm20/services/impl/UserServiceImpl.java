package com.scm.scm20.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.scm20.entities.user;
import com.scm.scm20.hellper.AppConstents;
import com.scm.scm20.hellper.Helper;
import com.scm.scm20.hellper.ResourceNotFoundException;
import com.scm.scm20.repository.userRepo;
import com.scm.scm20.services.EmailService;
import com.scm.scm20.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private userRepo userRepo; 

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    //private Logger logger = LoggerFactory.getLogger(UserService.class);
    @Override
    public user saveUser(user user) {
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRoleList(List.of(AppConstents.ROLE_USER));
        String emailtoken = UUID.randomUUID().toString();
        user.setEmailToken(emailtoken);
        user save = userRepo.save(user); 
        String emaillink = Helper.getLinkForEmailVerification(emailtoken);
        emailService.SendEmail(save.getEmail(), "Verify Account Smart Contact Manager", emaillink);
        return save;


    }


    @Override
    public Optional<user> getUserById(String id) {
       return userRepo.findById(id);
    }

    @Override
    public user getUserByEmail(String email) {
       
        return userRepo.findByEmail(email).orElseThrow(null);
    }

    @Override
    public void deleteuser(String id) {
        user user2 = userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("user not found")); 
        userRepo.delete(user2);
     
    }
    @Override
    public Optional<user> updateUser(user user) {
    user user2 = userRepo.findById(user.getUserId()).orElseThrow(()-> new ResourceNotFoundException("user not found")); 
    user2.setName(user.getName());
    user2.setEmail(user.getEmail());
    user2.setAbout(user.getAbout());
    user2.setPassword(user.getPassword());
    user2.setPhoneNumber(user.getPhoneNumber());
    user2.setProfilePic(user.getProfilePic());
    user2.setEmailVerified(user.isEmailVerified());
    user2.setEnabled(user.isEnabled());
    user2.setProvider(user.getProvider());
    user2.setProviderId(user.getProviderId());
    user2.setPhoneVerified(user.isPhoneVerified());

    return Optional.ofNullable(userRepo.save(user2));
}


    @Override
    public List<user> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public boolean isuserExist(String id) {
        user user2 = userRepo.findById(id).orElse(null);
        return user2 != null ?true :false;
    }

    @Override
    public boolean isuserExistByEmail(String email) {
       user user3 = userRepo.findByEmail(email).orElse(null);
       return user3 != null ? true : false;
    }

}
