package com.example.employee.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.employee.empdto.EmployeeLoginDTO;
import com.example.employee.empdto.User;
import com.example.employee.repository.UserRepository;
import com.example.employee.securityconf.JwtService;
import com.example.employee.securityconf.UserPrincipal;

@Service
public class EmployeeService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	JwtService jwtService;

	@Autowired
	AuthenticationManager manager;

	public ResponseEntity<Object> employeeLogin(EmployeeLoginDTO user) {
		Authentication authentication = manager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
		if (authentication.isAuthenticated()) {
			UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
			User authUser = userPrincipal.getUser();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Token", jwtService.generateToken(authUser));
			map.put("EID", authUser.getId());
			return new ResponseEntity<Object>(map, HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>("Invalid Email/Password", HttpStatus.CONFLICT);
		}
	}

	public ResponseEntity<Object> getDetails(int id) {
		return new ResponseEntity<Object>(userRepository.findById(id), HttpStatus.FOUND);
	}

}
