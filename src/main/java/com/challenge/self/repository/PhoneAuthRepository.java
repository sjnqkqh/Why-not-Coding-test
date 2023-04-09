package com.challenge.self.repository;

import com.challenge.self.entity.PhoneAuth;
import com.challenge.self.util.AuthTypeCode;
import com.challenge.self.util.TelecomCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhoneAuthRepository extends JpaRepository<PhoneAuth, Long> {

    Optional<PhoneAuth> findFirstByAuthTypeCodeAndEncPhoneAndTelecomCodeOrderByUpdatedAtDesc(AuthTypeCode authTypeCode, String encPhone, TelecomCode telecomCode);

    Optional<PhoneAuth> findFirstByAuthenticationAndAuthTypeCode(String authentication, AuthTypeCode authTypeCode);
}
