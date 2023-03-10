package com.trang.ShopmeBackEnd_Admin.category;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.trang.ShopmeBackEnd_Admin.repository.CategoryRepository;
import com.trang.ShopmeCommon.entity.Category;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CategoryRepositoryTests {
	@Autowired
	private CategoryRepository categoryRepository;

	@Test
	public void testCreateRootCategory1() {
		Category category = new Category("Computers");
		Category savedCategory = categoryRepository.save(category);
			
		assertThat(savedCategory.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateRootCategory2() {
		Category category = new Category("Electronics");
		Category savedCategory = categoryRepository.save(category);
	
		assertThat(savedCategory.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateSubCategoryDesktops() {
		Category parent = new Category(1);
		Category subCategory = new Category("Desktops", parent);
		Category savedCategory = categoryRepository.save(subCategory);
		assertThat(savedCategory.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateSubCategoryLaptops() {
		Category parent = new Category(1);
		Category subCategory = new Category("Laptops", parent);
		Category savedCategory = categoryRepository.save(subCategory);
		assertThat(savedCategory.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateSubCategoryComputerComponents() {
		Category parent = new Category(1);
		Category subCategory = new Category("Computer Components", parent);
		Category savedCategory = categoryRepository.save(subCategory);
		assertThat(savedCategory.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateSubCategoryGamingLaptops() {
		Category parent = new Category(4);
		Category subCategory = new Category("GamingLaptops", parent);
		Category savedCategory = categoryRepository.save(subCategory);
		assertThat(savedCategory.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateSubCategorySmartPhone() {
		Category parent = new Category(7);
		Category subCategory = new Category("IPhone", parent);
		Category savedCategory = categoryRepository.save(subCategory);
		assertThat(savedCategory.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateSubCategory() {
		Category parent = new Category(2);
		Category cameras = new Category("Cameras", parent);
		Category smartphone = new Category("Smartphones", parent);

		categoryRepository.saveAll(List.of(cameras, smartphone));
	}
	
	@Test
	public void testCreateSubCategoryNext() {
		Category parent = new Category(5);
		Category subCategory = new Category("Memory", parent);
		Category savedCategory = categoryRepository.save(subCategory);
		assertThat(savedCategory.getId()).isGreaterThan(0);
	}

	@Test
	public void testGetCategory() {
		Category category = categoryRepository.findById(1).get();
		System.out.println(category.getName());

		Set<Category> children = category.getChildren();

		for (Category subCategory : children) {
			System.out.println(subCategory.getName());
		}

		assertThat(children.size()).isGreaterThan(0);
	}

	@Test
	public void testPrintHierarchicalCategories() {
		Iterable<Category> categories = categoryRepository.findAll();

		for (Category category : categories) {
			if (category.getParent() == null) {
				System.out.println(category.getName());

				Set<Category> children = category.getChildren();

				for (Category subCategory : children) {
					System.out.println("--" + subCategory.getName());
					printChildren(subCategory, 1);
				}
			}
		}
	}

	private void printChildren(Category parent, int subLevel) {
		int newSubLevel = subLevel + 1;
		Set<Category> children = parent.getChildren();

		for (Category subCategory : children) {
			for (int i = 0; i < newSubLevel; i++) {
				System.out.println("--");
			}
			System.out.println(subCategory.getName());

			printChildren(parent, newSubLevel);
		}
	}
	
//	@Test
//	public void testListRootCategories() {
//		List<Category> rootCategories =	categoryRepository.findRootCategories();
//		rootCategories.forEach(categories -> System.out.println(categories.getName()));
//	}
	
	@Test
	public void testFindByName() {
		String name = "Computers";
		Category category = categoryRepository.findByName(name);
		
		assertThat(category).isNotNull();
		assertThat(category.getName()).isEqualTo(name);		
	}
	
	@Test
	public void testFindByAlias() {
		String alias = "electronics";
		Category category = categoryRepository.findByAlias(alias);
		
		assertThat(category).isNotNull();
		assertThat(category.getAlias()).isEqualTo(alias);		
	}

}
