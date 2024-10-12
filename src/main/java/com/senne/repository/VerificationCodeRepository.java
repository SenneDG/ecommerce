package com.senne.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.senne.modal.VerificationCode;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    
    VerificationCode findByEmail(String email);
    VerificationCode findByOtp(String otp);
}
