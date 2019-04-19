package com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shop.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	//Get Role by name
	@Query(value = "SELECT * FROM roles WHERE name =:name", nativeQuery = true)
	Role getRoleByName(@Param("name") String name);
}
