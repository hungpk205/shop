package com.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shop.entities.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
	//Get List All Profile by id
	@Query(value = "SELECT * FROM profiles", nativeQuery = true)
	List<Profile> getAll();
}
