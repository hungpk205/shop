package com.shop.service;

import com.shop.entities.Profile;

public interface ProfileService {
	
	//Add new Profile
	Profile addNewProfile(Profile objProfile);
	
	//Delete profile
	void deleteProfile(Profile objProfile);
	
}
