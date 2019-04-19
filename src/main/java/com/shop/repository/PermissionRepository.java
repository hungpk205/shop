package com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shop.entities.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
	
	@Query(value = "SELECT * FROM permissions WHERE name =:name", nativeQuery = true)
	Permission getPermissionByName(@Param("name") String name);
}
