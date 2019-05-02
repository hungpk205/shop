package com.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shop.entities.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
	//Get transaction of customer
	@Query(value = "SELECT * FROM transactions AS t INNER JOIN accounts AS a ON t.id_account = a.id WHERE a.id =:id_customer", nativeQuery = true)
	List<Transaction> getTransactionOfCustomer(@Param("id_customer") int id_customer);
	
}
