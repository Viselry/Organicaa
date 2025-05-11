package com.organica.services.impl;


import com.organica.config.CustomUserDetailsService;
import com.organica.config.JwtUtils;
import com.organica.entities.Cart;
import com.organica.entities.Role;
import com.organica.entities.TotalRoles;
import com.organica.entities.User;
import com.organica.payload.SingIn;
import com.organica.payload.UserDto;
import com.organica.repositories.UserRepo;
import com.organica.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {



    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @Override
    public UserDto CreateUser(UserDto userDto) {
        User user= this.modelMapper.map(userDto, User.class);
        List<Role> list= new ArrayList<>();
                list.add(new Role(TotalRoles.ADMIN.name()));
        user.setRole(list);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Cart cart= new Cart();
        cart.setUser(user);
        user.setCart(cart);

        this.customUserDetailsService.save(user);
        return this.modelMapper.map(user,UserDto.class);
    }

    @Override
    public SingIn singIn(SingIn singIn) {
        System.out.println(singIn);

        User user = this.userRepo.findByEmail(singIn.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + singIn.getEmail()));

        String jwtToken = jwtUtils.generateTokenFromUsername(user.getName());

        singIn.setJwt(jwtToken);
        singIn.setUserId(user.getUserId());
        return singIn;
    }

}
