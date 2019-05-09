package com.shop.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shop.entities.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
	//Get transaction of customer
	@Query(value = "SELECT * FROM transactions AS t INNER JOIN accounts AS a ON t.id_account = a.id WHERE a.id =:id_account", nativeQuery = true)
	List<Transaction> getTransactionOfCustomer(@Param("id_account") int id_account);
	
	//Get transaction by id transaction of account
	@Query(value = "SELECT * FROM transactions AS t INNER JOIN accounts AS a ON t.id_account = a.id WHERE a.id =:id_account AND t.id =:id_transaction", nativeQuery = true)
	Transaction getTransactionByIdTransactionOfAccount(@Param("id_account") int id_account, @Param("id_transaction") int id_transaction);

}
