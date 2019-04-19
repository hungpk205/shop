package com.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.entities.Permission;
import com.shop.repository.PermissionRepository;
import com.shop.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService {
	
	@Autowired
	private PermissionRepository permissionRepo;
	
	@Override
	public Permission getPermissionByName(String name) {
		return permissionRepo.getPermissionByName(name);
	}

	@Override
	public Permission getPermissionById(int id) {
		return permissionRepo.getOne(id);
	}

}
