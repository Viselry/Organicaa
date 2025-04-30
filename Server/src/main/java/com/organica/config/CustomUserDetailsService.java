package com.organica.config;

import com.organica.entities.User;
import com.organica.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepository;

    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return userRepository.findByName(name);
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
