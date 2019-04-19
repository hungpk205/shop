package com.shop.service;

import com.shop.entities.Permission;

public interface PermissionService {
	
	Permission getPermissionById(int id);
	
	Permission getPermissionByName(String name);
}
