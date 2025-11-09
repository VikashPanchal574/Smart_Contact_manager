package com.scm.scm20.services;

import java.util.List;
import java.util.Optional;

import com.scm.scm20.entities.user;

public interface UserService {
user saveUser(user user);

Optional<user> getUserById(String id);

user getUserByEmail(String email);

void deleteuser (String id);

Optional<user> updateUser(user user);

List<user> getAllUsers();

boolean isuserExist(String id);

boolean isuserExistByEmail(String email);



}
