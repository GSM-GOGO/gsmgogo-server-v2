package com.example.gsmgogoapi.domain.user.repository;

import com.example.gsmgogoentity.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {
}
