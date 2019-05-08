package com.shop.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shop.entities.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
	//Get cart of account
	@Query(value = "SELECT * FROM carts AS c WHERE c.id_account =:id_account", nativeQuery = true)
	List<Cart> getCartOfAccount(@Param("id_account") int id_account);
	
	//Delete cart of account
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM carts WHERE id_account =:id_account", nativeQuery = true)
	void DeleteCartOfAccount(@Param("id_account") int id_account);
	
	//Get cart product account
	@Query(value = "SELECT * FROM carts WHERE id_account =:id_account AND id_product =:id_product", nativeQuery = true)
	Cart getCartProductAccount(@Param("id_account") int id_account, @Param("id_product") int id_product);
}
