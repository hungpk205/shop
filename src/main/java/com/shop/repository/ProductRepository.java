package com.shop.repository;

import java.util.List;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shop.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	//Get top 12 product new
	@Query(value = "SELECT * FROM products ORDER BY created_at", nativeQuery = true)
	List<Product> getTop12(Pageable pageable);
	
	//Get count all products
	@Query(value = "SELECT COUNT(*) FROM products", nativeQuery = true)
	int getCountProducts();
	
	//Delete product by id category
	@Modifying
	@Query(value = "DELETE FROM products WHERE id_category =:idCat", nativeQuery = true)
	void deleteProductByIdCategory(@Param("idCat") int idCat);
	
	
	@Query(value = "SELECT * FROM products WHERE id_category =:idCat", nativeQuery = true)
	List<Product> getListProductByIdCategory(@Param("idCat") int idCat);

	@Query(value = "SELECT * FROM products ORDER BY created_at DESC LIMIT 0,10", nativeQuery = true)
	List<Product> getTop10Product();
	
	//Get list product by id account
	@Query(value = "SELECT * FROM products AS p INNER JOIN accounts AS ac ON p.created_by = ac.id WHERE ac.id =:id", nativeQuery = true)
	List<Product> getProductByIdAccount(@Param("id") int id);
	
}
