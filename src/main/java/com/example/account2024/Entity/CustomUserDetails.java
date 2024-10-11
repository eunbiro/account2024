package com.example.account2024.Entity;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {
// Spring Security는 유저 인증 과정에서 UserDetails를 참조하여 인증을 진행한다.
// UserTable에 바로 UserDetails를 상속해도 동작은 하지만 엔티티가 오염되어 사용이 어려워지기 때문에 CustomUserDetails를 따로 만들어주었다.
// JWT를 사용하기 위해 아래 4개의 오버라이드는 true로 설정한다.
	
	
	private final UserTable userTable;
	
	public CustomUserDetails(UserTable userTable) {
		this.userTable = userTable;
	}
	
	public final UserTable getUser() {
		return userTable;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return userTable.getRoles().stream().map(o -> new SimpleGrantedAuthority(
				o.getUserName()
				)).collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return userTable.getUserPassword();
	}

	@Override
	public String getUsername() {
		return userTable.getUserId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	
}
