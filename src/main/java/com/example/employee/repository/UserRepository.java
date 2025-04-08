package com.example.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.employee.empdto.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	User findByEmail(String email);

	boolean existsByEmail(String email);
}
