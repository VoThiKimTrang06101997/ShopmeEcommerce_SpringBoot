package com.trang.ShopmeBackEnd_Admin.brand;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.trang.ShopmeBackEnd_Admin.repository.BrandRepository;
import com.trang.ShopmeCommon.entity.Brand;
import com.trang.ShopmeCommon.entity.Category;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class BrandRepositoryTest {
	@Autowired
	private BrandRepository brandRepository;

	@Test
	public void testCreateBrand1() {
		Category laptops = new Category(6);
		Brand acer = new Brand("Acer");
		acer.getCategories().add(laptops);

		var savedBrand = brandRepository.save(acer);

		assertThat(savedBrand).isNotNull();
		assertThat(savedBrand.getId()).isGreaterThan(0);
	}

	@Test
	public void testCreateBrand2() {
		Category cellphonesAndAcessories = new Category(4);
		Category tablets = new Category(7);
		Brand apple = new Brand("Apple");
		apple.getCategories().add(cellphonesAndAcessories);
		apple.getCategories().add(tablets);

		var savedBrand = brandRepository.save(apple);

		assertThat(savedBrand).isNotNull();
		assertThat(savedBrand.getId()).isGreaterThan(0);
	}

	@Test
	public void testCreateBrand3() {
		Category memory = new Category(29);     // Category memory
		Category hd = new Category(24);			// Category Internal Hard Drive

		Brand samsung = new Brand("Samsung");

		samsung.getCategories().add(memory);
		samsung.getCategories().add(hd);

		var savedBrand = brandRepository.save(samsung);

		assertThat(savedBrand).isNotNull();
		assertThat(savedBrand.getId()).isGreaterThan(0);
	}

	@Test
	public void printAllBrands() {
		Iterable<Brand> brands = brandRepository.findAll();
		brands.forEach(System.out::println);

		assertThat(brands).isNotNull();

//	        for (Brand brand : brands){
//	            System.out.println("ID: " + brand.getId());
//	            System.out.println("Name: " + brand.getName());
//	            System.out.println("Category:" + brand.getCategories());
		//
//	        }
	}

	@Test
	public void testGetById() {
		Brand brand = brandRepository.findById(2).get();

		assertThat(brand.getName()).isEqualTo("Acer");
	}

	@Test
	public void getBrandById() {
		var brandById = brandRepository.findById(2);
		System.out.println(brandById.get().toString());

		var categories = brandById.get().getCategories();

		for (Category category : categories) {
			System.out.println(category.getName());
		}

		assertThat(categories.size()).isGreaterThan(0);
	}

	@Test
	public void updateBrand() {
		var samsung = brandRepository.findByName("Samsung");
		System.out.println(samsung.toString());

		samsung.setName("Samsung Eletronics");
		brandRepository.save(samsung);

		System.out.println(samsung.toString());

		assertThat(samsung.getName()).isEqualTo("Samsung Eletronics");
	}
	
//	@Test
//	public void testUpdateName() {
//		String newName = "Samsung Electronics";
//		Brand samsung = brandRepository.findById(3).get();
//		samsung.setName(newName);
//		
//		Brand savedBrand = brandRepository.save(samsung);
//		assertThat(savedBrand.getName()).isEqualTo(newName);
//	}

	@Test
	public void deleteBrand() {
		var deletedBrand = brandRepository.findByName("Apple");

		brandRepository.delete(deletedBrand);

		Optional<Brand> result = brandRepository.findById(deletedBrand.getId());

		assertThat(result.isEmpty());
	}
	
//	@Test
//	public void testDelete() {
//		Integer id = 2;
//		brandRepository.deleteById(id);
//		
//		Optional<Brand> result = brandRepository.findById(id);
//		
//		assertThat(result.isEmpty());
//	}

//	    @Test
//	    public void getBrand(){
//	        repo.get
//	    }

}
