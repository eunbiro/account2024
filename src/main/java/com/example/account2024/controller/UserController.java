package com.example.account2024.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.account2024.dto.UserDto;
import com.example.account2024.repository.UserRepository;
import com.example.account2024.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
	
	private final UserRepository userRepository;
	private final UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<UserDto> login(@RequestBody UserDto userDto) throws Exception {
		return new ResponseEntity<>(userService.login(userDto), HttpStatus.OK);
	}
	
	@PostMapping("/regist")
	public ResponseEntity<Boolean> register(@RequestBody UserDto userDto) throws Exception {
		return new ResponseEntity<>(userService.regist(userDto), HttpStatus.OK);
	}
	
	@GetMapping("/user")
	public ResponseEntity<UserDto> getUser(@RequestParam String userId) throws Exception {
		return new ResponseEntity<>(userService.getUser(userId), HttpStatus.OK);
	}
	
	public ResponseEntity<UserDto> getUserForAdmin(@RequestParam String userId) throws Exception {
		return new ResponseEntity<>(userService.getUser(userId), HttpStatus.OK);
	}
}
