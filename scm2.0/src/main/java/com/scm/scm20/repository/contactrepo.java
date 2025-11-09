package com.scm.scm20.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.scm.scm20.entities.contact;
import com.scm.scm20.entities.user;

@Repository
public interface contactrepo extends JpaRepository<contact, String> {

    Page<contact> findByUser(user user, Pageable pageable);

    Page<contact> findByUserAndNameContaining(user user, String namekeyword , Pageable pageable);
    Page<contact> findByUserAndEmailContaining(user user , String emailkeyword , Pageable pageable);

    Page<contact> findByUserAndPhoneNumberContaining(user user ,String phoneNumberkeyword , Pageable pageable);


    // @Query("SELECT C FORM contact C WHERE C.user = :user")  
    // List<contact> findByUserId(@Param("userid") String userid);

}
