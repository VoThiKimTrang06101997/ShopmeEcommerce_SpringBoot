package com.trang.ShopmeBackEnd_Admin.controller.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trang.ShopmeBackEnd_Admin.service.category.CategoryService;


@RestController
public class CategoryRestController {
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/categories/check_unique")
	public String checkUnique(@Param("id") Integer id, @Param("name") String name, @Param("alias") String alias) {
		return categoryService.checkIsNameUnique(id, name, alias);
	}
	
	
}
