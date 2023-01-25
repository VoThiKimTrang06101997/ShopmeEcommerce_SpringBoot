package com.trang.ShopmeBackEnd_Admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.trang.ShopmeBackEnd_Admin.repository.UserRepository;
import com.trang.ShopmeCommon.entity.User;

public class ShopmeUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.getUserByEmail(email);

		if (user != null) {
			return new ShopmeUserDetails(user);
		}

		throw new UsernameNotFoundException("Could not find user with email: " + email);
	}

}
