package com.organica.config.oauth2;

import com.organica.config.JwtUtils;
import com.organica.entities.User;
import com.organica.repositories.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtUtils jwtUtils; // Lớp bạn dùng để sinh JWT

    @Autowired
    private UserRepo userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found after OAuth login"));

        // Tạo JWT
        String jwt = jwtUtils.generateTokenFromUsername(user.getUsername());

        // Redirect về frontend kèm token
        String redirectUrl = "http://localhost:3000/oauth2/redirect?token=" + jwt;
        response.sendRedirect(redirectUrl);
    }
}

