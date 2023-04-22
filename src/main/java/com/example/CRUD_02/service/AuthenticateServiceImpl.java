package com.example.CRUD_02.service;

import com.example.CRUD_02.model.*;
import com.example.CRUD_02.repository.UserRepository;
import com.example.CRUD_02.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticateServiceImpl implements AuthenticateService {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;


    @Override
    public AuthResponse authenticateUser(AuthRequest authRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                    ));
        } catch (BadCredentialsException ex) {
            return AuthResponse.builder().message(ex.getMessage()).build();
        }

        User user = userRepository.findUserByUsername(authRequest.getUsername()).orElseThrow();
        final String jwtToken = jwtUtil.generateToken(user);
        return AuthResponse.builder()
                .jwtToken(jwtToken)
                .message("User authenticated successfully")
                .build();
    }

    @Override
    public ResponseDTO registerUser(AuthRequest authRequestDTO) {
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(authRequestDTO.getUsername())
                .password(authRequestDTO.getPassword())
                .roles(Role.USER.toString())
                .build();

        User user = new User(userDetails.getUsername(), userDetails.getPassword(), Role.USER);
        // Check if the username exists or not
        if(userRepository.findUserByUsername(authRequestDTO.getUsername()).isPresent()){
            return ResponseDTO.builder().message("Username already exists.").build();
        }
        userRepository.save(user);
        return ResponseDTO.builder().message("User Successfully created.").build();
    }
}
