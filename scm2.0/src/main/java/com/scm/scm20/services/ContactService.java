package com.scm.scm20.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.scm.scm20.entities.contact;
import com.scm.scm20.entities.user;

public interface ContactService {

 contact save(contact contact);

 contact update(contact contact);

 List<contact> getAll();

 contact getById(String id);

 void delete(String id);

 Page<contact> searchByName(String nameKeyword , int size , int page , String sortBy , String order , user user);

 Page<contact> searchByPhoneNumber(String phoneNumberKeyword , int size , int page , String sortBy , String order , user user);

 Page<contact> searchByEmail(String emailKeyword , int size , int page , String sortBy , String order , user user);

 List<contact> getByUserId(user userId);
    
Page <contact> getByUser(user user , int page , int size, String sortBy , String diraction);


}
