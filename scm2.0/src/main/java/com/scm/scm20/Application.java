package com.scm.scm20;
import com.scm.scm20.entities.user;
import com.scm.scm20.hellper.AppConstents;
import com.scm.scm20.repository.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class Application  implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private userRepo userRepo;


    @Override
    public void run(String... args) throws Exception {
        user user = new user();
        user.setUserId(UUID.randomUUID().toString());
        user.setName("admin");
        user.setEmail("admin@gmail.com");
        user.setPassword(("admin"));
        user.setRoleList(List.of(AppConstents.ROLE_USER));
        user.setEmailVerified(true);
        user.setEnabled(true);
        user.setAbout("This is dummy user created initially");
        user.setPhoneVerified(true);

        userRepo.findByEmail("admin@gmail.com").ifPresentOrElse(user1 -> {},() -> {
            userRepo.save(user);
            System.out.println("user created");
        });


    }
}
