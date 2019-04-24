package com.shop.service;

import com.shop.entities.Profile;

public interface ProfileService {
	//Get profile by id
	Profile getProfile(int id);
	
	//Add new Profile
	Profile addNewProfile(Profile objProfile);
	
	//Edit Profile
	Profile editProfile(Profile objProfile);
	
	
	//Delete profile
	void deleteProfile(Profile objProfile);
	
}
