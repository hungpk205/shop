package com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shop.entities.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{
	
	@Query(value = "SELECT * FROM accounts AS ac WHERE ac.username =:username AND ac.password =:password", nativeQuery = true)
	Account GetAccountByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
