package com.trang.ShopmeBackEnd_Admin.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.trang.ShopmeCommon.entity.Brand;

@Repository
public interface BrandRepository extends PagingAndSortingRepository<Brand, Integer>, CrudRepository<Brand, Integer> {
	public Brand findByName(String name);
	
	public Long countById(Integer id);

//	@Query("SELECT b FROM Brand b where b.name = :name")
//	public Brand getBrandByName(String name);

	@Query("SELECT b FROM Brand b WHERE b.name LIKE %?1%")
	public Page<Brand> findAll(String keyword, Pageable pageable);

	@Query("SELECT NEW Brand(b.id, b.name) FROM Brand b ORDER BY b.name ASC")
	public List<Brand> findAll();

}