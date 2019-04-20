package com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shop.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{
	
	//Get category by name
	@Query(value = "SELECT * FROM categories WHERE name =:name", nativeQuery = true)
	Category getCategoryByName(@Param("name") String name);
	
}
