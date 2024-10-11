package com.example.account2024.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Authority {
// 사용자의 권한 목록을 나타내는 엔티티
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;
	
	private String userName;
	
	@JoinColumn(name = "user_table")
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private UserTable userTable;
	
	public void setUser(UserTable userTable) {
		this.userTable = userTable;
	}
}
