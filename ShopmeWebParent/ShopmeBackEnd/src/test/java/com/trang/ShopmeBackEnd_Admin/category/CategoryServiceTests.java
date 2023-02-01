package com.trang.ShopmeBackEnd_Admin.category;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.trang.ShopmeBackEnd_Admin.repository.CategoryRepository;
import com.trang.ShopmeBackEnd_Admin.service.category.CategoryService;
import com.trang.ShopmeCommon.entity.Category;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class CategoryServiceTests {
	@MockBean
	private CategoryRepository categoryRepository;

	@InjectMocks
	private CategoryService categoryService;

	@Test
	public void testCheckUniqueInNewModeReturnDuplicateName() {
		Integer id = null;
		String name = "Computers";
		String alias = "abc";

		Category category = new Category(id, name, alias);

		Mockito.when(categoryRepository.findByName(name)).thenReturn(category);
		Mockito.when(categoryRepository.findByAlias(alias)).thenReturn(null);

		String result = categoryService.checkIsNameUnique(id, name, alias);

		assertThat(result).isEqualTo("DuplicatedName");

	}

	@Test
	public void testCheckUniqueInEditModeReturnDuplicateName() {
		Integer id = 1;
		String name = "Computers";
		String alias = "abc";

		Category category = new Category(2, name, alias);

		Mockito.when(categoryRepository.findByName(name)).thenReturn(category);
		Mockito.when(categoryRepository.findByAlias(alias)).thenReturn(null);

		String result = categoryService.checkIsNameUnique(id, name, alias);

		assertThat(result).isEqualTo("DuplicatedName");

	}

	@Test
	public void testCheckUniqueInNewModeReturnDuplicateAlias() {
		Integer id = null;
		String name = "NameABC";
		String alias = "computers";

		Category category = new Category(id, name, alias);

		Mockito.when(categoryRepository.findByName(name)).thenReturn(null);
		Mockito.when(categoryRepository.findByAlias(alias)).thenReturn(category);

		String result = categoryService.checkIsNameUnique(id, name, alias);

		assertThat(result).isEqualTo("DuplicatedAlias");

	}

	@Test
	public void testCheckUniqueInEditModeReturnDuplicateAlias() {
		Integer id = 1;
		String name = "NameABC";
		String alias = "computers";

		Category category = new Category(2, name, alias);

		Mockito.when(categoryRepository.findByName(name)).thenReturn(null);
		Mockito.when(categoryRepository.findByAlias(alias)).thenReturn(category);

		String result = categoryService.checkIsNameUnique(id, name, alias);

		assertThat(result).isEqualTo("DuplicatedAlias");

	}

	@Test
	public void testCheckUniqueInNewModeReturnAliasOK() {
		Integer id = null;
		String name = "NameABC";
		String alias = "computers";

		Mockito.when(categoryRepository.findByName(name)).thenReturn(null);
		Mockito.when(categoryRepository.findByAlias(alias)).thenReturn(null);

		String result = categoryService.checkIsNameUnique(id, name, alias);

		assertThat(result).isEqualTo("OK");

	}

	@Test
	public void testCheckUniqueInEditModeReturnAliasOK() {
		Integer id = 2;
		String name = "NameABC";
		String alias = "computers";

		Category category = new Category(id, name, alias);

		Mockito.when(categoryRepository.findByName(name)).thenReturn(null);
		Mockito.when(categoryRepository.findByAlias(alias)).thenReturn(category);

		String result = categoryService.checkIsNameUnique(id, name, alias);

		assertThat(result).isEqualTo("OK");

	}
}
