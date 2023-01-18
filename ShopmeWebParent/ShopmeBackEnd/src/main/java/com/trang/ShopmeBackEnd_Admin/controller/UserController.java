package com.trang.ShopmeBackEnd_Admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trang.ShopmeBackEnd_Admin.service.UserNotFoundException;
import com.trang.ShopmeBackEnd_Admin.service.UserService;
import com.trang.ShopmeCommon.entity.Role;
import com.trang.ShopmeCommon.entity.User;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public String listAllUsers(Model model) {
		List<User> listUsers = userService.listAllUsers();
		model.addAttribute("listUsers", listUsers);
		return "users";
	}

	@GetMapping("/users/new")
	public String newUser(Model model) {
		List<Role> listRoles = userService.listRoles();

		User user = new User();
		user.setEnabled(true);

		model.addAttribute("user", user);
		model.addAttribute("listRoles", listRoles);
		model.addAttribute("pageTitle", "Create New User");
		return "user_form";
	}

	@PostMapping("/users/save")
	@ModelAttribute(value = "user")
	// @RequestMapping(method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
		System.out.println(user);
		// model.addAttribute("user", user);
		userService.save(user);

		redirectAttributes.addFlashAttribute("message", "The User had been saved successfully !");
		return "redirect:/users";
	}

	@GetMapping("/users/edit/{id}")
	public String editUser(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes, Model model) {
		try {
			User user = userService.get(id);
			List<Role> listRoles = userService.listRoles();

			model.addAttribute("user", user);
			model.addAttribute("pageTitle", "Edit User (ID: " + id + ") ");
			model.addAttribute("listRoles", listRoles);

			return "user_form";
		} catch (UserNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
			return "redirect:/users";
		}
	}

	@GetMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes,
			Model model) {
		try {
			userService.delete(id);
			redirectAttributes.addFlashAttribute("message", "The user ID: " + id + " has been deleted successfully !");
			// return "user_form";
		} catch (UserNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());

		}
		return "redirect:/users";
	}

	@GetMapping("/users/{id}/enabled/{status}")

	public String updateUserEnabledStatus(@PathVariable("id") Integer id, @PathVariable("status") boolean enabled,
			RedirectAttributes redirectAttributes) throws UserNotFoundException {

		userService.updateUserEnabledStatus(id, enabled);

		String status = enabled ? "enabled" : "disabled";

		String message = "The user ID: " + id + " has been " + status;

		redirectAttributes.addFlashAttribute("message", message);

		User user = userService.get(id);

		return "redirect:/users";
	}
}