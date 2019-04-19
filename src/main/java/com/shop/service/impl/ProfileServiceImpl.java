package com.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.entities.Profile;
import com.shop.repository.ProfileRepository;
import com.shop.service.ProfileService;

@Service
public class ProfileServiceImpl implements ProfileService {

	@Autowired
	private ProfileRepository profileRepo;
	
	@Override
	public Profile addNewProfile(Profile objProfile) {
		return profileRepo.save(objProfile);
	}

}
