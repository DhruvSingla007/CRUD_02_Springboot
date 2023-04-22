package com.example.CRUD_02.service;

import com.example.CRUD_02.model.AuthRequest;
import com.example.CRUD_02.model.AuthResponse;
import com.example.CRUD_02.model.Role;
import com.example.CRUD_02.model.User;
import com.example.CRUD_02.repository.UserRepository;
import com.example.CRUD_02.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
            throw new Exception("Incorrect username or password", ex);
        }

        User user = userRepository.findUserByUsername(authRequest.getUsername()).orElseThrow();
        final String jwtToken = jwtUtil.generateToken(user);
        return AuthResponse.builder().jwtToken(jwtToken).build();
    }

    @Override
    public AuthResponse registerUser(AuthRequest authRequestDTO) {


        try {
            var user = org.springframework.security.core.userdetails.User.builder()
                    .username(authRequestDTO.getUsername())
                    .password(authRequestDTO.getPassword())
                    .roles(Role.USER.toString())
                    .build();

            System.out.println(user);
            System.out.println(user.getAuthorities());
//            userRepository.save(user);
            String jwtToken = jwtUtil.generateToken(user);
            return AuthResponse.builder().jwtToken(jwtToken).build();
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
