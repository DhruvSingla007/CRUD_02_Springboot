package com.example.CRUD_02.service;

import com.example.CRUD_02.model.AuthRequest;
import com.example.CRUD_02.model.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

public interface AuthenticateService {

    public AuthResponse authenticateUser(AuthRequest authRequest) throws Exception;

    public AuthResponse registerUser(AuthRequest authRequestDTO);
}
