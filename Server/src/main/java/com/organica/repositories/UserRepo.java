package com.organica.repositories;

import com.organica.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepo extends JpaRepository<User, Integer> {
    public User findByEmail(String e);

    public User findByName(String name);
}
