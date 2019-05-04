package com.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.entities.Payment;
import com.shop.repository.PaymentRepository;
import com.shop.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private PaymentRepository paymentRepo;
	
	@Override
	public Payment getPaymentById(int id) {
		return paymentRepo.getOne(id);
	}

}
