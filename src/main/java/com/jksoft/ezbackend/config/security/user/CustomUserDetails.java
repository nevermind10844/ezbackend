package com.jksoft.ezbackend.config.security.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails{

	private static final long serialVersionUID = 8580989043403554170L;
	
	private Long userId;
	private String username;
	private String password;
	private List<GrantedAuthority> authorities;
	private boolean enabled;
	
	public CustomUserDetails(User user) {
		this.userId = user.getId();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.authorities = new ArrayList<>();
		this.authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		this.enabled = user.isActive();
	}
	
	public Long getId() {
		return this.userId;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
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
		return this.enabled;
	}

}
