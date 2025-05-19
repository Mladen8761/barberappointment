package com.Mladen.barberappointment.repository;

import com.Mladen.barberappointment.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.management.relation.Role;

public interface RolesRepository extends JpaRepository<Roles,Integer> {
    Roles findByName(String name);
}
