package com.organica.config.oauth2;

import com.organica.entities.User;
import com.organica.payload.UserDto;
import com.organica.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        // Lấy thông tin từ Google
        Map<String, Object> attributes = oauth2User.getAttributes();
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");

        // Kiểm tra xem user đã tồn tại chưa
        Optional<User> existingUser = userRepo.findByEmail(email);
        String randomPassword = UUID.randomUUID().toString();
        if (existingUser.isEmpty()) {
            // 1. Tạo UserDto từ thông tin Google
            UserDto userDto = new UserDto();
            userDto.setEmail(email);
            userDto.setName(name);
            userDto.setPassword(randomPassword);
            userDto.setContact("N/A");  // có thể để null nếu muốn
            userDto.setDate(new Date());
            userDto.setRole("USER");

            // 2. Map sang entity User
            User newUser = modelMapper.map(userDto, User.class);

            // 3. Lưu vào DB
            userRepo.save(newUser);
        }

        // 4. Trả về OAuth2User để tiếp tục xử lý đăng nhập
        return oauth2User;
    }
}
