package com.trang.ShopmeBackEnd_Admin.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trang.ShopmeBackEnd_Admin.FileUploadUtil;
import com.trang.ShopmeBackEnd_Admin.service.UserNotFoundException;
import com.trang.ShopmeBackEnd_Admin.service.UserService;
import com.trang.ShopmeCommon.entity.Role;
import com.trang.ShopmeCommon.entity.User;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public String listFirstPage(Model model) {
//		List<User> listUsers = userService.listAllUsers();
//		model.addAttribute("listUsers", listUsers);
//		return "users";

		return listByPage(1, model);
	}

	@GetMapping("/users/page/{pageNum}")
	public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model) {
		Page<User> page = userService.listByPage(pageNum);
		List<User> listUsers = page.getContent();

//		System.out.println("Pagenum = " + pageNum);
//		System.out.println("Total elements = " + page.getTotalElements());
//		System.out.println("Total pages = " + page.getTotalPages());
		long startCount = (pageNum - 1) * userService.USER_PER_PAGE + 1;
		long endCount = startCount + userService.USER_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);

//		 Code cách này sẽ ra kết quả là User Not Found
//		 model.addAttribute("totalItems", page.getTotalElements());
//		 model.addAttribute("listUsers", new ArrayList<User>());
		
		
		model.addAttribute("totalItems", page.getTotalElements());
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
	public String saveUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes,
			@RequestParam("image") MultipartFile multipartFile) throws IOException {
		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			user.setPhotos(fileName);
			User savedUser = userService.save(user);

			String uploadDir = "user-photos/" + savedUser.getId();

			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		} else {
			if (user.getPhotos().isEmpty()) {
				user.setPhotos(null);
				userService.save(user);
			}
		}
		// System.out.println(user);
		// System.out.println(multipartFile.getOriginalFilename());

		// model.addAttribute("user", user);

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
