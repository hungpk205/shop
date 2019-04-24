package com.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.entities.Profile;
import com.shop.service.ProfileService;

@RestController
@RequestMapping("api/profile")
public class ProfileController {
	
	@Autowired
	private ProfileService profileService;
	
	//Get profile account
	@GetMapping("{id}")
	public ResponseEntity<Profile> getProfile(@PathVariable("id") int id){
		Profile objProfile = profileService.getProfile(id);
		if (objProfile != null) {
			return new ResponseEntity<Profile>(objProfile, HttpStatus.OK);
		} else {
			return new ResponseEntity<Profile>(HttpStatus.NO_CONTENT);
		}
	}
	
	//Edit profile
	@PutMapping("{id}")
	public ResponseEntity<Profile> editProfile(@RequestBody Profile objProfile){
		Profile editProfile = profileService.editProfile(objProfile);
		if (editProfile != null) {
			return new ResponseEntity<Profile>(editProfile, HttpStatus.OK);
		} else {
			return new ResponseEntity<Profile>(HttpStatus.NOT_ACCEPTABLE);
		}
			
		
	}
}
