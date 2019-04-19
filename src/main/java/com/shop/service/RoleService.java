package com.shop.service;

import com.shop.entities.Role;

public interface RoleService {
	
	Role getRoleById(int id_role);
	
	Role getRoleByName(String name_role);
	
}
