package com.shop.repository;

import java.util.List;

import javax.transaction.Transactional;

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
	@Query(value = "SELECT * FROM products WHERE active = 1 ORDER BY created_at", nativeQuery = true)
	List<Product> getTop12(Pageable pageable);
	
	//Get count all products
	@Query(value = "SELECT COUNT(*) FROM products WHERE active = 1", nativeQuery = true)
	int getCountProducts();
	
	//Delete product by id category
	@Modifying
	@Query(value = "DELETE FROM products WHERE id_category =:idCat", nativeQuery = true)
	void deleteProductByIdCategory(@Param("idCat") int idCat);
	
	//Get product int category
	@Query(value = "SELECT * FROM products WHERE active = 1 AND id_category =:idCat", nativeQuery = true)
	List<Product> getListProductByIdCategory(@Param("idCat") int idCat);
 
	@Query(value = "SELECT * FROM products WHERE active = 1 ORDER BY created_at DESC LIMIT 0,12", nativeQuery = true)
	List<Product> getTop12Product();
	
	@Query(value = "SELECT * FROM products WHERE active = 1 ORDER BY count_buy DESC LIMIT 0,12", nativeQuery = true)
	List<Product> getTop12ProductBuy();
	
	//Get list product by id account
	@Query(value = "SELECT * FROM products AS p INNER JOIN accounts AS ac ON p.created_by = ac.id WHERE p.active = 1 AND ac.id =:id", nativeQuery = true)
	List<Product> getProductByIdAccount(@Param("id") int id);
	
	//Get list product by id category
	@Query(value = "SELECT * FROM products AS p INNER JOIN categories AS c ON p.id_category = c.id WHERE p.active = 1 AND c.id =:id", nativeQuery = true)
	List<Product> getProductByIdCategory(@Param("id") int id);
	
	//Get list product in category of shop
	@Query(value = "SELECT * FROM products AS p INNER JOIN accounts AS ac ON p.created_by = ac.id"
			+ " INNER JOIN categories AS c ON p.id_category = c.id WHERE p.active = 1 AND ac.id =:idShop AND c.id = :idCat", nativeQuery = true)
	List<Product> getProductInCategoryOfShop(@Param("idCat") int idCat, @Param("idShop") int idShop);
	
	//Get product by id
	@Query(value = "SELECT * FROM products WHERE id =:idProduct", nativeQuery = true)
	Product GetProductById(@Param("idProduct") int idProduct);
	
	//Increase count buy
	@Modifying
	@Transactional
	@Query(value = "UPDATE products SET count_buy = count_buy + 1 WHERE id =:idProduct", nativeQuery = true)
	void IncreaseCountBuy(@Param("idProduct") int idProduct);
	
	//Reduce quantity product
	@Modifying
	@Transactional
	@Query(value = "UPDATE products SET quantity = quantity - :number WHERE id =:id_product", nativeQuery = true)
	void ReduceQuantiyProduct(@Param("id_product") int id_product,@Param("number") int number);
	
}
