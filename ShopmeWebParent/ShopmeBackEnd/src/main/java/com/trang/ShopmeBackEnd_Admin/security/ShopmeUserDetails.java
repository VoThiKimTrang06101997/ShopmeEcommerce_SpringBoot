package com.trang.ShopmeBackEnd_Admin.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.trang.ShopmeCommon.entity.Role;
import com.trang.ShopmeCommon.entity.User;

public class ShopmeUserDetails implements UserDetails {

	private User user;

	public ShopmeUserDetails(User user) {
		super();
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<Role> roles = user.getRoles();

		List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();

		for (Role role : roles) {
			simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
		}

		return simpleGrantedAuthorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// return user.getEmail();
		return user.getFirstName() + " " + user.getLastName();
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
		return user.isEnabled();
	}

	
}
