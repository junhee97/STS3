package com.example.app.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.app.domain.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PrincipalDetails implements UserDetails {
	private UserDto userDto;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() { // 권한 체크
		Collection<GrantedAuthority> authorities = new ArrayList();
		authorities.add(new SimpleGrantedAuthority(userDto.getRole()));

		return authorities;
	}

	@Override
	public String getPassword() {

		return userDto.getPassword();
	}

	@Override
	public String getUsername() {

		return userDto.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() { // 계정 만료 여부

		return true;
	}

	@Override
	public boolean isAccountNonLocked() { // 계정 잠금 여부

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
