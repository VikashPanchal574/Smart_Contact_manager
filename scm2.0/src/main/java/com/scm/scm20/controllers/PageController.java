package com.scm.scm20.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.scm.scm20.Application;
import com.scm.scm20.entities.user;
import com.scm.scm20.forms.UserForm;
import com.scm.scm20.hellper.MessageType;
import com.scm.scm20.hellper.message;
import com.scm.scm20.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PageController {

    private final Application application;

    @Autowired
    private UserService userService;

    PageController(Application application) {
        this.application = application;
    }

    @RequestMapping("/home")
    public String home(Model model) {
        System.out.println("Home page handller");
        model.addAttribute("name", "substring technologies");
        model.addAttribute("youtubechennel", "learn code with vikash");
        model.addAttribute("githubLink", "https://github.com/");
        return "home";
    }

    @RequestMapping("/about")
    public String aboutPage() {
        System.out.println("This is About Page");
        return "about";
    }

    @RequestMapping("/service")
    public String servicePage() {
        System.out.println("This is Service Page");
        return "service";
    }

    @RequestMapping("/register")
    public String registerPage(Model model) {
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        System.out.println("This is register Page");
        return "register";

    }

    @RequestMapping("/login")
    public String loginPage() {
        System.out.println("This is login Page");
        return "login";
    }
    // @PostMapping("/login")
    // public String login() {
    //     return "login";
    // }

    @RequestMapping("/contact")
    public String contactPage() {
        System.out.println("This is contact Page");
        return "contact";
    }
    // processingRegister

    @PostMapping("/do-register")
    public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult result,
            HttpSession session) {

        if (result.hasErrors()) {
            System.out.println("=====================================================");
            System.out.println(result.hasErrors());
            return "register";
         }
        

            user user1 = new user();
            user1.setName(userForm.getName());
            user1.setEmail(userForm.getEmail());
            user1.setPassword(userForm.getPassword());
            user1.setPhoneNumber(userForm.getNumber());
            user1.setAbout(userForm.getAbout());
            user1.setEnabled(false);
            user1.setProfilePic("/images/defaultProfilePic.png");
            userService.saveUser(user1);

            message ms = message.builder().content("Registration Success").type(MessageType.green).build();
            session.setAttribute("message", ms);
            return "redirect:/register";
    }
}
