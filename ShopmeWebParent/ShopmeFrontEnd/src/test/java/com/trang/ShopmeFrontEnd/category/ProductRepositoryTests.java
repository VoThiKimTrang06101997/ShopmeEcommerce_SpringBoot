package com.trang.ShopmeFrontEnd.category;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.trang.ShopmeCommon.entity.Product;
import com.trang.ShopmeFrontEnd.repository.ProductRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace= Replace.NONE)
public class ProductRepositoryTests {
	@Autowired
	private ProductRepository productRepository;
	
	@Test
	public void testFindByAlias() {
		String alias = "something";
		Product product = productRepository.findByAlias(alias);
		
		assertThat(product).isNotNull();
	}
	
}
