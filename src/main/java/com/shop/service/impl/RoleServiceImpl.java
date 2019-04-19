package com.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.entities.Role;
import com.shop.repository.RoleRepository;
import com.shop.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{
	@Autowired
	private RoleRepository roleRepo;
	
	@Override
	public Role getRoleById(int id_role) {
		return roleRepo.getOne(id_role);
	}

	@Override
	public Role getRoleByName(String name_role) {
		return roleRepo.getRoleByName(name_role);
	}

}
