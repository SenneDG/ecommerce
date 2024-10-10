package com.senne.service;

import com.senne.response.SignupRequest;

public interface AuthService {
    
    String createUser(SignupRequest request) throws Exception;
}
