package com.invenza.forgetpass.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.invenza.forgetpass.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByMobile(String mobile);
}
