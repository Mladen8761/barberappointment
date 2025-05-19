package com.Mladen.barberappointment.service;

import com.Mladen.barberappointment.model.Roles;
import com.Mladen.barberappointment.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findByEmail(String email);
    void save(User user);
    Roles findByName(String name);
}
