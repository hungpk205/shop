package com.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shop.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
	//Get order of customer
	@Query(value = "SELECT * FROM orders AS o INNER JOIN products AS p ON o.id_product = p.id WHERE p.created_by =:id_shop", nativeQuery =  true)
	List<Order> getOrderOfShop(@Param("id_shop") int id_shop);
	
}
