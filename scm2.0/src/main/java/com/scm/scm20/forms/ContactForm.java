package com.scm.scm20.forms;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// private String id;
//     private String name;
//     private String email;
//     private String phoneNumber;
//     private String address;
//     private String picture;
//     @Column(length = 1000)
//     private String description;
//     private boolean favorite = false;
//     private String websiteLink;
//     private String linkedInLink;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ContactForm {
  
    @NotBlank(message = " Name is required")
    private String name;

    @NotBlank(message = " Email is required")
    @Email(message = " Invalid email")
    private String email;
    @Pattern(regexp = "^[0-9]{8,12}$", message = "Phone number must be Valid") 
   // @Pattern(regexp = "^[0-9][10]$",message = "invalid phone number")
    @NotBlank(message = " Phone number is required")
    private String phoneNumber;
    
    private String address = "Madhya pradesh india";
    
    private String description;
    
    private boolean favorite;
    
    private String websiteLink;
    
    private String linkedInLink;
    
    private MultipartFile contactImage;

    private String picture;

    private String image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTltWWn6we_ep1wPBb7Glnwmms0K7w1mcmQD1VTuRc27YCX9xm3Bk4NitU65HiFT36Kjv0&usqp=CAU";

    


}
