package com.scm.scm20.controllers;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.scm20.entities.contact;
import com.scm.scm20.entities.user;
import com.scm.scm20.forms.ContactForm;
import com.scm.scm20.forms.ContactSearchForm;
import com.scm.scm20.hellper.AppConstents;
import com.scm.scm20.hellper.Helper;
import com.scm.scm20.hellper.MessageType;
import com.scm.scm20.hellper.message;
import com.scm.scm20.repository.contactrepo;
import com.scm.scm20.services.ContactService;
import com.scm.scm20.services.UserService;
import com.scm.scm20.services.imageService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
//import lombok.var;

@Controller
@RequestMapping("/user/contact")
public class ContactController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ContactService contactService;

    @Autowired
    private contactrepo contactrepo;

    @Autowired
    private UserService userService;

    @Autowired
    private imageService imageService;

    @RequestMapping("/add")
    public String addContactView(Model model) {
        ContactForm contactForm = new ContactForm();
        model.addAttribute("contactForm", contactForm);
        return "user/add-contact";
    }

    @PostMapping("/add")
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult bindingResult,
            Authentication authentication, HttpSession session) {

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> logger.error(error.toString()));
            session.setAttribute("message", message.builder()
                    .content("please corract the following errors")
                    .type(MessageType.red)
                    .build());
            return "user/add-contact";
        }

        String username = Helper.GetEmailOfLogedInUser(authentication);
        user user = userService.getUserByEmail(username);
        // image processing

        // code for uploading image

        
        logger.info("file information : {}", contactForm.getContactImage().getOriginalFilename());

        contact contact = new contact();
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setFavorite(contactForm.isFavorite());
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
            
            String filename = UUID.randomUUID().toString();
            String fileURL = imageService.uploadImage(contactForm.getContactImage(), filename);
            contact.setPicture(fileURL);
            contact.setCloudinaryimagePublicId(filename);
        }
       
        
        contact.setUser(user);
        
        contactService.save(contact);

        session.setAttribute("message", message.builder()
                .content("Contact added sucsessfully")
                .type(MessageType.green)
                .build());

        System.out.println(contactForm);
        return "redirect:/user/contact/add";

    }

    // view contacts
    @RequestMapping
    public String viewContact(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = AppConstents.PAGE_SIZE + "") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Authentication authentication, Model model) {

        // load all user
        String username = Helper.GetEmailOfLogedInUser(authentication);
        user user = userService.getUserByEmail(username);
        Page<contact> contacts = contactService.getByUser(user, page, size, sortBy, direction);

        model.addAttribute("contacts", contacts);
        model.addAttribute("pageSize", AppConstents.PAGE_SIZE);
        model.addAttribute("contactSearchForm", new ContactSearchForm());

        return "user/contact";
    }

    @RequestMapping("/search")
    public String searchHandler(
            @ModelAttribute ContactSearchForm contactSearchForm,
            @RequestParam(value = "size ", defaultValue = AppConstents.PAGE_SIZE + "") int size,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Model model,
            Authentication authentication

    ) {

        var user = userService.getUserByEmail(Helper.GetEmailOfLogedInUser(authentication));

        logger.info("field {} keyword {}", contactSearchForm.getField(), contactSearchForm.getValue());

        Page<contact> contacts = null;
        if (contactSearchForm.getField().equalsIgnoreCase("name")) {
            contacts = contactService.searchByName(contactSearchForm.getValue(), size, page, sortBy, direction, user);
        } else if (contactSearchForm.getField().equalsIgnoreCase("phone")) {

            contacts = contactService.searchByPhoneNumber(contactSearchForm.getValue(), size, page, sortBy, direction,
                    user);
        } else if (contactSearchForm.getField().equalsIgnoreCase("email")) {
            contacts = contactService.searchByEmail(contactSearchForm.getValue(), size, page, sortBy, direction, user);
        }
        System.out.println(contactSearchForm.getField());
        // logger.info("contacts {}",contacts.getContent());
        model.addAttribute("pageSize", AppConstents.PAGE_SIZE);
        model.addAttribute("contacts", contacts);
        model.addAttribute("contactSearchForm", contactSearchForm);

        return "user/search";
    }

    @RequestMapping("/delete/{contactid}")
    public String deleteContact(@PathVariable("contactid") String contactid) {
        contactService.delete(contactid);
        logger.info("contactId {} deleted", contactid);
        return "redirect:/user/contact";
    }

    @RequestMapping("/view/{contactId}")
    public String updateContactFormView(@PathVariable("contactId") String contactId, Model model) {
        var contact = contactService.getById(contactId);

        ContactForm contactForm = new ContactForm();
        contactForm.setName(contact.getName());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setEmail(contact.getEmail());
        contactForm.setAddress(contact.getAddress());
        contactForm.setDescription(contact.getDescription());
        contactForm.setFavorite(contact.isFavorite());
        contactForm.setLinkedInLink(contact.getLinkedInLink());
        contactForm.setWebsiteLink(contact.getWebsiteLink());
        contactForm.setPicture(contact.getPicture());

        model.addAttribute("contactForm", contactForm);
        model.addAttribute(contactId, contactId);

        return "user/update_contact_view";
    }

    @PostMapping("/update/{contactId}")
    public String updateContact(@PathVariable("contactId") String contactId,
    @Valid @ModelAttribute("contactForm") ContactForm contactForm,  BindingResult result ,Model model ) {
     
                if(result.hasErrors()){
                    return "user/update_contact_view";
                }

        var con = contactService.getById(contactId);
        con.setId(contactId);
        con.setName(contactForm.getName());
        con.setPhoneNumber(contactForm.getPhoneNumber());
        con.setEmail(contactForm.getEmail());
        con.setAddress(contactForm.getAddress());
        con.setDescription(contactForm.getDescription());
        con.setFavorite(contactForm.isFavorite());
        con.setLinkedInLink(contactForm.getLinkedInLink());
        con.setWebsiteLink(contactForm.getWebsiteLink());
        if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
            con.setPicture(contactForm.getPicture());

            String filename = UUID.randomUUID().toString();
            String imageUrl = imageService.uploadImage(contactForm.getContactImage(), filename);
            con.setCloudinaryimagePublicId(filename);
            con.setPicture(imageUrl);
            contactForm.setPicture(imageUrl);
        } else {
            logger.info("file is empty");
        }
        var updateCon = contactService.update(con);
        logger.info("updated contact {} ", updateCon);

        model.addAttribute("message", message.builder().content("content updated").type(MessageType.green).build());

        return "redirect:/user/contact/view/" + contactId;
    }

}