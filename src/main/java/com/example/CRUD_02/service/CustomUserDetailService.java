package com.example.CRUD_02.service;

import com.example.CRUD_02.mappers.UserMapper;
import com.example.CRUD_02.model.User;
import com.example.CRUD_02.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByUsername(username);
        return user.map(UserMapper::new).orElseThrow(
                ()->new UsernameNotFoundException("User not found"));
    }
}
