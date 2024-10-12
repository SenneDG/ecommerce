package com.senne.service;

import com.senne.request.LoginRequest;
import com.senne.response.AuthResponse;
import com.senne.response.SignupRequest;

public interface AuthService {
    
    void sentLoginOtp(String email) throws Exception;
    String createUser(SignupRequest request) throws Exception;
    AuthResponse signin(LoginRequest request) throws Exception;
}
