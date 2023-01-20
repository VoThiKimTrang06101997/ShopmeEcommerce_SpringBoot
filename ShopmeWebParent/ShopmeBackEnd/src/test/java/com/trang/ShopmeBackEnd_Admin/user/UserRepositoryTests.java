package com.trang.ShopmeBackEnd_Admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.trang.ShopmeBackEnd_Admin.repository.UserRepository;
import com.trang.ShopmeCommon.entity.Role;
import com.trang.ShopmeCommon.entity.User;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TestEntityManager testEntityManager;

//	@Test
//	public void testCreateNewUserWithOneRole() {
//		Role roleAdmin = testEntityManager.find(Role.class, 1);
//		User user = new User("trang@gmail.com", "123456", "Võ Thị", "Kim Trang");
//		user.addRole(roleAdmin);
//
//		User savedUser = userRepository.save(user);
//		assertThat(savedUser.getId()).isGreaterThan(0);
//	}
//
//	@Test
//	public void testCreateNewUserWithTwoRoles() {
//		User userRavi = new User("ravi@gmail.com", "ravi123", "Ravi", "Kumar");
//		Role roleEditor = new Role(3);
//		Role roleAssistant = new Role(5);
//
//		userRavi.addRole(roleEditor);
//		userRavi.addRole(roleAssistant);
//
//		User savedUserRavi = userRepository.save(userRavi);
//
//		assertThat(savedUserRavi.getId()).isGreaterThan(0);
//	}
//
//	@Test
//	public void testListAllUsers() {
//		Iterable<User> listUsers = userRepository.findAll();
//		listUsers.forEach(user -> System.out.println(user));
//	}
//
//	@Test
//	public void testGetUserById() {
//		User userTrang = (User) userRepository.findById(1).get();
//		System.out.println(userTrang);
//		assertThat(userTrang).isNotNull();
//	}
//
//	@Test
//	public void testUpdateUserDetails() {
//		User userTrang = (User) userRepository.findById(1).get();
//		userTrang.setEnabled(true);
//		userTrang.setEmail("trang123@gmail.com");
//
//		userRepository.save(userTrang);
//	}
//
//	@Test
//	public void testUpdateUserRoles() {
//		User userRavi = (User) userRepository.findById(2).get();
//		Role roleEditor = new Role(3);
//		Role roleSalePerson = new Role(2);
//
//		userRavi.getRoles().remove(roleEditor);
//		userRavi.addRole(roleSalePerson);
//
//		userRepository.save(userRavi);
//	}
//
//	@Test
//	public void testDeleteUser() {
//		Integer userId = 2;
//		userRepository.deleteById(userId);
//		
//		userRepository.findById(userId);
//	}
//	
//	@Test
//	public void testGetUserByEmail() {
//		String email = "ravi@gmail.com";
//		User user = userRepository.getUserByEmail(email);
//		
//		assertThat(user).isNotNull();
//	}
//	
//	@Test
//	public void testCountById() {
//		Integer id = 100;
//		Long countById = userRepository.countById(id);
//		
//		assertThat(countById).isNotNull().isGreaterThan(0);
//	}
//	
//	@Test
//	public void testDisableUser() {
//		Integer id = 1;
//		userRepository.updateEnabledStatus(id, false);
//	}
//	
//	@Test
//	public void testEnableUser() {
//		Integer id = 2;
//		userRepository.updateEnabledStatus(id, true);
//	}

	@Test
	public void testListFirstPage() {
		int pageNumber = 1;
		int pageSize = 4;
		Pageable pageable = (Pageable) PageRequest.of(pageNumber, pageSize);
		Page<User> page = userRepository.findAll( pageable);

		
		List<User> listUsers = page.getContent();
		listUsers.forEach(user -> System.out.println(user));
		assertThat(listUsers.size()).isEqualTo(pageSize);
		
	}
}
