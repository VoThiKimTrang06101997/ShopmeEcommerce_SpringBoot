package com.trang.ShopmeBackEnd_Admin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.trang.ShopmeCommon.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
	
}
