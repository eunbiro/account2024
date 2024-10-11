package com.example.account2024.service;

import java.util.Collections;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.account2024.Entity.Authority;
import com.example.account2024.Entity.UserTable;
import com.example.account2024.config.JwtProvider;
import com.example.account2024.dto.UserDto;
import com.example.account2024.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;
	
	public UserDto login(UserDto userDto) throws Exception {
		UserTable userTable = userRepository.findByUserId(userDto.getUserId()).orElseThrow(() -> new BadCredentialsException("잘못된 계정정보 입니다."));
		
		if (!passwordEncoder.matches(userDto.getUserPassword(), userTable.getUserPassword())) {
			throw new BadCredentialsException("잘못된 계정정보 입니다.");
		}
		
		return UserDto.builder()
					  .userKeyId(userTable.getUserKeyId())
					  .userId(userTable.getUserId())
					  .userName(userTable.getUserName())
					  .userPNum(userTable.getUserPNum())
					  .roles(userTable.getRoles())
					  .token(jwtProvider.createToken(userTable.getUserId(), userTable.getRoles()))
					  .build();
	}
	
	public boolean regist(UserDto userDto) throws Exception {
		try {
			UserTable userTable = UserTable.builder()
										   .userId(userDto.getUserId())
										   .userPassword(userDto.getUserPassword())
										   .userName(userDto.getUserName())
										   .userPNum(userDto.getUserPNum())
										   .build();
			
			userTable.setRoles(Collections.singletonList(Authority.builder().userName("ROLE_USER").build()));
			
			userRepository.save(userTable);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new Exception("잘못된 요청입니다.");
		}
		
		return true;
	}
	
	public UserDto getUser(String userId) throws Exception {
		UserTable userTable = userRepository.findByUserId(userId).orElseThrow(() -> new Exception("계정을 찾을 수 없습니다."));
		return new UserDto(userTable);
	}
}
