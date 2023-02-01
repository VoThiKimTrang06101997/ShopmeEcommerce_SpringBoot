package com.trang.ShopmeBackEnd_Admin.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.trang.ShopmeCommon.entity.Category;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer>, CrudRepository<Category, Integer> {

//	Category save(List<Category> list);
//
//	Category save(Category category);
//
//	Category findById(int i);
//
//	Iterable<Category> findAll();

	// void save(List<Category> of);
	
	@Query("SELECT c FROM Category c WHERE c.parent.id is NULL")
	public List<Category> findRootCategories(Sort sort);
	
	public Category findByName(String name);
	
	public Category findByAlias(String alias);
	
	@Query("UPDATE Category c SET c.enabled =?2 WHERE c.id = ?1")
	@Modifying
	public void updateEnabledStatus(Integer id, boolean enabled);
	
	public Long countById(Integer id);
	
	// Pagination
	@Query("SELECT c FROM Category c WHERE c.parent.id is NULL")
	public Page<Category> findRootCategories(Pageable pageable);
	
	@Query("SELECT c FROM Category c WHERE c.name LIKE %?1%")
	public Page<Category> search (String keyword, Pageable pageable);
	
}
