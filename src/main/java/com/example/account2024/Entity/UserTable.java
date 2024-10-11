package com.example.account2024.Entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class UserTable {
// 사용자를 정의
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userKeyId;

	@Column(unique = true)
	private String userId;

	private String userName;

	private String userPassword;

	@Column(unique = true)
	private String userPNum;

	private int startMonth;

	private long expensesTarget;
	
	@OneToMany(mappedBy = "user_table", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Builder.Default
	private List<Authority> roles = new ArrayList<>();
	
	public void setRoles(List<Authority> role) {
		
	}
}
