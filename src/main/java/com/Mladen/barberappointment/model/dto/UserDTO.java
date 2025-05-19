package com.Mladen.barberappointment.model.dto;


import com.Mladen.barberappointment.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserDTO {

    @NotBlank(message = "morate uneti ime")
    String fullName;

    @NotBlank(message = "morate uneti mejl")
    @Pattern(regexp = "^(?i)[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$" ,message = "unesite mejl dobar")
    String email;

    @NotBlank
            @Size(min=8,message = "Minimalno 8 karaktera")
    String password;

    public UserDTO() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User mapper()
    {
        User user=new User();
        user.setFullName(this.getFullName());
        user.setEmail(this.getEmail());
        return user;
    }
}
