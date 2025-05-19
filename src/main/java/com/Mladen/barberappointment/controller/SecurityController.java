package com.Mladen.barberappointment.controller;

import com.Mladen.barberappointment.model.Roles;
import com.Mladen.barberappointment.model.User;
import com.Mladen.barberappointment.model.dto.UserDTO;
import com.Mladen.barberappointment.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.SQLOutput;
import java.util.Arrays;

@Controller
public class SecurityController {

    UserService userService;
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public SecurityController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/showLoginForm")
    public String loginForm()
    {
        return "login-form";
    }
    @GetMapping("/showRegistrationForm")
    public String registrationForm(Model model)
    {
        model.addAttribute("userDTO",new UserDTO());
        return "registration-form";
    }
    @PostMapping("/processRegistrationForm")
    public String register(@Valid @ModelAttribute("userDTO") UserDTO userDTO, BindingResult bindingResult, Model model)
    {
        if(bindingResult.hasErrors())
        {
            model.addAttribute("userDTO",userDTO);
            return "registration-form";
        }
        User user= userDTO.mapper();
        if(userService.findByEmail(user.getEmail())!=null)
        {
            model.addAttribute("message","postoji nalog sa ovim mejlom");
            return "registration-form";
        }
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRoles(Arrays.asList(userService.findByName("ROLE_CLIENT")));
        userService.save(user);

        return "redirect:/showLoginForm";
    }
    @GetMapping("/error")
    public String errorPage()
    {
        return "error";
    }
}
