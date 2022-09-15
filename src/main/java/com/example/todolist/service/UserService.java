package com.example.todolist.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.todolist.entity.User;
import com.example.todolist.entity.UserRole;
import com.example.todolist.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final String USER_NOT_FOUND_MSG = "user with email %s not found";

    public String register(User user) {
        boolean userExists = userRepository.findByEmail(user.getEmail()isPresent());

        if (userExists) {
            throw new IllegalStateException("Email is already used");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());

        user.setRole(UserRole.USER);
        userRepository.save(user);

        return "Registered";
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
        .orElseThrow(()-> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }
    
}
