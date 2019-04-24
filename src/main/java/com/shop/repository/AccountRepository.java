package com.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shop.entities.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{
	
	//Get all account
	//List<Account> getAccountShop();
	
	
	//Get account by username and password
	@Query(value = "SELECT * FROM accounts AS ac WHERE ac.username =:username AND ac.password =:password", nativeQuery = true)
	Account GetAccountByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
