package com.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shop.entities.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{
	
	//Get all account have role is SHOP OWNER
	@Query(value = "SELECT * FROM accounts AS ac INNER JOIN account_role AS ar ON ac.id = ar.id_account" + 
				"	INNER JOIN roles AS r ON ar.id_role = r.id WHERE r.name LIKE 'SHOP OWNER'")
	List<Account> getAccountShop();
	
	
	//Get account by username and password
	@Query(value = "SELECT * FROM accounts AS ac WHERE ac.username =:username AND ac.password =:password", nativeQuery = true)
	Account GetAccountByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
