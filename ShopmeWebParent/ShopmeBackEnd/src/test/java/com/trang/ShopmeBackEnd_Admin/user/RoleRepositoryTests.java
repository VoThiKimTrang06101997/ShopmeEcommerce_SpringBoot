package com.trang.ShopmeBackEnd_Admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.trang.ShopmeBackEnd_Admin.repository.RoleRepository;
import com.trang.ShopmeCommon.entity.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests {
	@Autowired
	private RoleRepository roleRepository;
	
	@Test
	public void testCreateFirstRole() {
		Role roleAdmin = new Role("Admin", "Manage Everything");
		Role savedRole = roleRepository.save(roleAdmin);
		
		assertThat(savedRole.getId()).isGreaterThan(0);	
	}
	
	@Test
	public void testCreateRestRoles() {
		Role roleSalesPerson = new Role("SalesPerson", "Manage Product Price, Customers, Shipping, Orders"
				+ "and Sales Report");
		
		Role roleEditor = new Role("Editor", "Manage Categories, Brands, Products, Articles"
				+ "and Menus");
		
		Role roleShipper = new Role("Shipper", "View Products, View Orders, and Update Order Status");
		
		Role roleAssistant = new Role("Assistant", "Manage Questions and Reviews");
		
		roleRepository.saveAll(List.of(roleSalesPerson, roleEditor, roleShipper, roleAssistant));
	}
}
