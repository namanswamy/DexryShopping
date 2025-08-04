package com.ecommerce.dexry.service;

import com.ecommerce.dexry.model.User;
import com.ecommerce.dexry.model.enums.Role;
import com.ecommerce.dexry.repository.UserRepository;
import com.ecommerce.dexry.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String register(User user){
        if(userRepository.existsByUsername(user.getUsername())){
            return "Username already exists!";
        }

        if(userRepository.existsByEmail(user.getEmail())){
            return "Email already exists!";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.addRole(Role.USER);
        userRepository.save(user);
        return "User registered successfully!";
    }

    public Map<String,String> login (String username, String password){
        return userRepository.findByUsername(username)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .map(user -> {
                    String token = jwtUtil.generateToken(user.getUsername(), user.getRoles());
                    Map<String, String> response = new HashMap<>();
                    response.put("token", token);
                    return response;
                })
                .orElse(null);
    }

}
