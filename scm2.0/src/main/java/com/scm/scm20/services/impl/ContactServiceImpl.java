package com.scm.scm20.services.impl;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.scm.scm20.entities.contact;
import com.scm.scm20.entities.user;
import com.scm.scm20.repository.contactrepo;
import com.scm.scm20.services.ContactService;

//import lombok.var;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private contactrepo  contactrepo;


    @Override
    public contact save(contact contact) {
        String contactId = UUID.randomUUID().toString();
        contact.setId(contactId);
        return  contactrepo.save(contact);
    }

    @Override
    public contact update(contact contact) {
        var contactOld = contactrepo.findById(contact.getId())
        .orElseThrow(()-> new ResolutionException("contact not found"));
    
        contactOld.setName(contact.getName());
        contactOld.setEmail(contact.getEmail());
        contactOld.setPhoneNumber(contact.getPhoneNumber());
        contactOld.setAddress(contact.getAddress());
        contactOld.setDescription(contact.getDescription());
        contactOld.setFavorite(contact.isFavorite());
        contactOld.setLinkedInLink(contact.getLinkedInLink());
        contactOld.setWebsiteLink(contact.getWebsiteLink());
        //contactOld.setLinks(contact.getLinks());
        contactOld.setCloudinaryimagePublicId(contact.getCloudinaryimagePublicId());

        return contactrepo.save(contactOld);



    }

    @Override
    public List<contact> getAll() {
      
        return contactrepo.findAll();
    }

    @Override
    public contact getById(String id) {
       return contactrepo.findById(id).orElseThrow(()-> new RuntimeException("contact not found"));
    }

    @Override
    public void delete(String id) {
        var contact = contactrepo.findById(id).orElseThrow(()-> new RuntimeException("contact not found"));
        contactrepo.delete(contact);
    }

  

    @Override
    public List<contact> getByUserId(user userId) {
    
        return null;
         //contactrepo.findByUser(userId);
    }

    @Override
    public Page<contact> getByUser(user user , int page , int size , String sortBy , String diraction) {
       Sort sort = diraction.equals("desc")?Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
       var pageable = PageRequest.of(page, size, sort);
       return contactrepo.findByUser(user, pageable);
    }


    @Override
    public Page<contact> searchByName(String nameKeyword, int size, int page, String sortBy, String order, user user) {
        Sort sort = order.equals("desc")?Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, Sort.by(sortBy));
       
       return contactrepo.findByUserAndNameContaining(user, nameKeyword, pageable);
    }

    @Override
    public Page<contact> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy, String order, user user) {
                Sort sort = order.equals("desc")?Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
                var pageable1 = PageRequest.of(page, size, Sort.by(sortBy));
               
               return contactrepo.findByUserAndPhoneNumberContaining(user,phoneNumberKeyword, pageable1);
    }

    @Override
    public Page<contact> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order, user user) {
        Sort sort = order.equals("des")?Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable1 = PageRequest.of(page, size, Sort.by(sortBy));
       
       return contactrepo.findByUserAndEmailContaining(user,emailKeyword,  pageable1);
    }

   
    

  

}
