package com.Mladen.barberappointment.controller;

import com.Mladen.barberappointment.service.UserService;
import com.Mladen.barberappointment.service.implementation.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    UserService userService;
    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }



    @GetMapping("/")
    public String home(Model model, Principal principal, HttpSession session)
    {
        if(session.getAttribute("user")!=null)
        {
            model.addAttribute("fullName",userService.findByEmail(principal.getName()).getFullName());

        }
        return "index";
    }

}
