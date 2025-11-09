package com.scm.scm20.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scm.scm20.entities.user;
import java.util.Optional;


@Repository
public interface userRepo extends JpaRepository<user, String> {

    Optional<user> findByEmail(String email);

    Optional<user> findByEmailToken(String id);

}
