package com.example.Product.Master.services;


import com.example.Product.Master.config.JwtService;
import com.example.Product.Master.dto.AuthResponseDTO;
import com.example.Product.Master.dto.LoginRequestDTO;
import com.example.Product.Master.dto.LoginResponseDTO;
import com.example.Product.Master.dto.RegisterRequestDTO;
import com.example.Product.Master.entity.UserEntity;
import com.example.Product.Master.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDTO register(RegisterRequestDTO request){

        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("Email already registered");
        }

        if(!request.getPassword().equals(request.getConformPassword())){
            throw new RuntimeException("Password and Conform password are not same");
        }

        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));


        userRepository.save(user);

        String jwtToken = jwtService.generateToken(
                org.springframework.security.core.userdetails.User
                        .withUsername(user.getEmail())
                        .password(user.getPassword())
                        .authorities("ROLE_ADMIN")
                        .build()
        );

        return new AuthResponseDTO(jwtToken);
    }

    public AuthResponseDTO login(LoginRequestDTO request){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));


        String jwtToken = jwtService.generateToken(
                org.springframework.security.core.userdetails.User
                        .withUsername(user.getEmail())
                        .password(user.getPassword())
                        .authorities("ROLE_ADMIN")
                        .build()
        );

        return new AuthResponseDTO(jwtToken);
    }

    public LoginResponseDTO getLoggedInUser(){

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));


        LoginResponseDTO loginUser = new LoginResponseDTO();

        loginUser.setEmail(user.getEmail());
        loginUser.setUsername(user.getUsername());

        return loginUser;
    }
}
