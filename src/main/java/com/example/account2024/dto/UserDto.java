package com.example.account2024.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.account2024.Entity.Authority;
import com.example.account2024.Entity.UserTable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	private Long userKeyId;
	
	private String userId;
	
	private String userName;
	
	private String userPassword;
	
	private String userPNum;
	
	private int startMonth;
	
	private Long expensesTarget;
	
	private List<Authority> roles = new ArrayList<>();
	
	private String token;

	public UserDto(UserTable userTable) {
		this.userKeyId = userTable.getUserKeyId();
		this.userId = userTable.getUserId();
		this.userName = userTable.getUserName();
		this.userPassword = userTable.getUserPassword();
		this.userPNum = userTable.getUserPNum();
		this.roles = userTable.getRoles();
	}
}
