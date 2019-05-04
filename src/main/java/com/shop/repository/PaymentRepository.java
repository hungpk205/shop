package com.shop.repository;

import org.springframework.stereotype.Repository;

import com.shop.entities.Payment;

import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
	
}
