package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	Role findByRole(String role);
	Role findById(Long id);
}
