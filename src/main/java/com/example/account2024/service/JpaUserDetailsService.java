package com.example.account2024.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.account2024.Entity.CustomUserDetails;
import com.example.account2024.Entity.UserTable;
import com.example.account2024.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {
// Jpa를 이용하여 DB에서 유지 정보를 조회할 것이므로 이에 맞춰서 구현
	
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserTable userTable = userRepository.findByUserId(username).orElseThrow(
					() -> new UsernameNotFoundException("Invalid authentication!"));
		return new CustomUserDetails(userTable);
	}
	
	
}
