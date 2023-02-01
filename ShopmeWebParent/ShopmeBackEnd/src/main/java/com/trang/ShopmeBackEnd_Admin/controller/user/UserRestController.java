package com.trang.ShopmeBackEnd_Admin.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trang.ShopmeBackEnd_Admin.service.user.UserService;

@RestController
// @CrossOrigin
public class UserRestController {
	@Autowired
	private UserService userService;

	@PostMapping("/users/check_email")
	public String checkDuplicateEmail(@Param("id") Integer id, @Param("email") String email) {
		return userService.isEmailUnique(id, email) ? "OK" : "Duplicated";
	}
}
