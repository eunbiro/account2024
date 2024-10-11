package com.example.account2024.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import com.example.account2024.Entity.UserTable;
import com.example.account2024.repository.UserRepository;

import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("local")
public class UserServiceTest {

	@Resource
	UserService userService;
	
	@Resource
	UserRepository userRepository;
	
	@Resource
	@Transactional
	@DisplayName("회원가입")
	@Test
	void saveUser() {
		UserTable user = UserTable.builder()
									.userId("user1234")
									.userName("name")
									.userPassword("12345")
									.userPNum("01012345678")
									.build();
		
		
		this.userRepository.save(user);
		
	}
}
