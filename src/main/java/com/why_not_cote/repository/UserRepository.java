package com.why_not_cote.repository;

import com.why_not_cote.entity.user.User;
import com.why_not_cote.util.code.YnCode;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByLoginIdAndDeleteYn(String loginId, YnCode deleteYn);

    Optional<User> findFirstByLoginIdAndEmailAndDeleteYnOrderByCreatedAtDesc(String loginId, String email, YnCode deleteYn);

    Optional<User> findFirstByLoginIdAndDeleteYnOrderByCreatedAtDesc(String loginId, YnCode deleteYn);

    Optional<User> findFirstByAccessTokenAndDeleteYnOrderByCreatedAtDesc(String accessToken, YnCode deleteYn);

    ArrayList<User> findUsersByDeleteYn(YnCode deleteYn, Pageable pageable);


}
