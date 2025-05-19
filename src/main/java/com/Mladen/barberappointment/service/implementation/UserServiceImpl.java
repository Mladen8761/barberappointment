package com.Mladen.barberappointment.service.implementation;

import com.Mladen.barberappointment.model.Roles;
import com.Mladen.barberappointment.model.User;
import com.Mladen.barberappointment.repository.RolesRepository;
import com.Mladen.barberappointment.repository.UserRepository;
import com.Mladen.barberappointment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    RolesRepository rolesRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, RolesRepository rolesRepository) {
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByEmail(username);
        if(user==null)
        {
            throw new UsernameNotFoundException("nema usera sa tim username-om");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles()
                        .stream()
                        .map(roles-> new SimpleGrantedAuthority(roles.getName()))
                        .collect(Collectors.toList()));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public Roles findByName(String name) {
        return rolesRepository.findByName(name);
    }
}
