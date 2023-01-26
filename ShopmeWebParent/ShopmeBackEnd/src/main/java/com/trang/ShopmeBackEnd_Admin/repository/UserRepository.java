package com.trang.ShopmeBackEnd_Admin.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trang.ShopmeCommon.entity.User;

import jakarta.transaction.Transactional;

@Repository
@EnableJpaRepositories
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
	@Query("SELECT u FROM User u WHERE u.email = :email")
	public User getUserByEmail(@Param("email") String email);
	
	public Long countById(Integer id);
	
	@Query("SELECT u FROM User u WHERE CONCAT(u.id, ' ', u.email, ' ', u.firstName, ' ',"
			+ " u.lastName) LIKE %?1%")
	public Page<User> findAll(String keyword, Pageable pageable);
	
	@Query(value = "UPDATE User u SET u.enabled = ?2 WHERE u.id = ?1")
	@Modifying
	@Transactional
	public void updateEnabledStatus(Integer id, boolean enabled);

	public Page<User> findAll(Pageable pageable);

	public List<User> findAll();
	
	public User findById(Integer id);
	
	public User findById(User user);

	public User save(User user);

	public void deleteById(Integer id);


	
}
