package com.trang.ShopmeFrontEnd.category;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.trang.ShopmeCommon.entity.Category;
import com.trang.ShopmeFrontEnd.repository.CategoryRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CategoryRepositoryTests {

	@Autowired
	private CategoryRepository categoryRepository;

	@Test
	public void testListEnabledCategories() {
		List<Category> categories = categoryRepository.findAllEnabled();
		categories.forEach(category -> {
			System.out.println(category.getName() + " (" + category.isEnabled() + ")");
		});
	}

	@Test
	public void testFindCategoryByAlias() {
		String alias = "something";
		Category category = categoryRepository.findByAliasEnabled(alias);

		assertThat(category).isNotNull();
	}

}
