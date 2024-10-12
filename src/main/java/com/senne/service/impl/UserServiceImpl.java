package com.senne.service.impl;

import org.springframework.stereotype.Service;

import com.senne.config.JwtProvider;
import com.senne.modal.User;
import com.senne.repository.UserRepository;
import com.senne.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        return this.findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);

        if(user == null) {
            throw new Exception("User not found with provided email - " + email);
        }

        return user;
    }

}
