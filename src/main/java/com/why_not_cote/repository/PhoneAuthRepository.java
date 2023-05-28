package com.why_not_cote.repository;

import com.why_not_cote.entity.user.PhoneAuth;
import com.why_not_cote.util.code.AuthTypeCode;
import com.why_not_cote.util.code.TelecomCode;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhoneAuthRepository extends JpaRepository<PhoneAuth, Long> {

    Optional<PhoneAuth> findFirstByAuthTypeCodeAndEncPhoneAndTelecomCodeOrderByUpdatedAtDesc(AuthTypeCode authTypeCode, String encPhone, TelecomCode telecomCode);

    Optional<PhoneAuth> findFirstByAuthenticationAndAuthTypeCode(String authentication, AuthTypeCode authTypeCode);
}
