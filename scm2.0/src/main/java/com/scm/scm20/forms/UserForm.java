package com.scm.scm20.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserForm {

    @NotBlank(message = "Username is required")
    @Size(min = 3, message = "Minimum 3 characters")
    private String name;
    @Email(message = "Invalid Email Address")
    @NotBlank(message = "Email is Required")
    private String email;
    @NotBlank(message = "Password is required")
    @Size( min = 8, message = "Minimum 8 characters")
    private String password;
    @Size(min = 8 , max = 12 , message = "Invalid Phone Number")
    @Pattern(regexp = "^[0-9]{8,12}$", message = "Phone number must be Valid")
    private String number;
    @NotBlank
    private String about;
}
