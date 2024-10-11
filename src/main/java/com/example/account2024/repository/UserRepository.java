package com.example.account2024.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.account2024.Entity.UserTable;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<UserTable, Long> {
	Optional<UserTable> findByUserId(String userId);
}
