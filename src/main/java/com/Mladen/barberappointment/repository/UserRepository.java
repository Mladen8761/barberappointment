package com.Mladen.barberappointment.repository;

import com.Mladen.barberappointment.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmail(String email);
}
