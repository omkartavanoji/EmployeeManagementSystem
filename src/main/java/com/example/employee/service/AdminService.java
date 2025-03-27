package com.example.employee.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.employee.empdto.Employee;
import com.example.employee.empdto.EmployeeDTO;
import com.example.employee.empdto.User;
import com.example.employee.repository.EmployeeRepository;
import com.example.employee.repository.UserRepository;
import com.example.employee.securityconf.JwtService;
import com.example.employee.securityconf.UserPrincipal;

import jakarta.transaction.Transactional;

@Service
public class AdminService {

	@Autowired
	AuthenticationManager manager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	JwtService jwtService;

	public ResponseEntity<String> login(User user) {
		Authentication authentication = manager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
		if (authentication.isAuthenticated()) {
			UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
			User authUser = userPrincipal.getUser();
			return new ResponseEntity<String>(jwtService.generateToken(authUser), HttpStatus.OK);
		} else {
			System.out.println("omkar");
			return new ResponseEntity<String>("failure", HttpStatus.CONFLICT);
		}
	}

	@Transactional
	public ResponseEntity<String> addEmployee(EmployeeDTO employeeDTO) {
		User user = new User();
		user.setEmail(employeeDTO.getEmail());
		user.setPassword(employeeDTO.getPassword());
		user.setRole("EMPLOYEE");
		Employee employee = new Employee();
		employee.setFirstname(employeeDTO.getFirstname());
		employee.setLastname(employeeDTO.getLastname());
		employee.setDepartment(employeeDTO.getDepartment());
		employee.setSalary(employeeDTO.getSalary());
		user.setEmployee(employee);
		userRepository.save(user);
		return new ResponseEntity<String>("EMPLOYEE REGISTERED SUCCESSFULLY", HttpStatus.CREATED);
	}

	public ResponseEntity<List<Employee>> getEmployees() {
		List<Employee> list = employeeRepository.findAll();
		if (list.isEmpty()) {
			return new ResponseEntity<List<Employee>>(list, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<Employee>>(list, HttpStatus.OK);
		}
	}

	public ResponseEntity<Object> getEmployeeById(int id) {
		Optional<Employee> emp = employeeRepository.findById(id);
		if (emp.isEmpty()) {
			return new ResponseEntity<Object>("NO EMPLOYEE PRESENT WITH ID "+id,HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Object>(emp, HttpStatus.OK);
		}
	}

	public ResponseEntity<Object> deleteEmployee(int id) {
		Optional<Employee> optional = employeeRepository.findById(id);
		if (optional.isEmpty()) {
			return new ResponseEntity<Object>("NO DATA FOUND TO DELETE", HttpStatus.NOT_FOUND);
		} else {
			employeeRepository.deleteById(id);
			return new ResponseEntity<Object>("EMPLOYEE DATA DELETED ", HttpStatus.OK);
		}
	}

	public ResponseEntity<Object> updateEmployee(EmployeeDTO employeeDTO) {
		if (employeeRepository.existsById(employeeDTO.getId())) {
			Employee emp=employeeRepository.findById(employeeDTO.getId()).get();
			emp.setDepartment(employeeDTO.getDepartment());
			emp.setFirstname(employeeDTO.getFirstname());
			emp.setLastname(employeeDTO.getLastname());
			emp.setSalary(employeeDTO.getSalary());
			employeeRepository.save(emp);
			return new ResponseEntity<Object> ("EMPLOYEE DATA UPDATED SUCCESSFULLY",HttpStatus.OK);
		} else {
            return new ResponseEntity<Object>("NO EMPLOYEE PRESENT",HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<Object> updateEmployeeRole(int id, String role) {
		User user=userRepository.findById(id).get();
		if (user!=null) {
			user.setRole(role);
			userRepository.save(user);
			return new ResponseEntity<Object>("ROLE UPDATED",HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>("NO USER FOUND",HttpStatus.NOT_FOUND);
		}
	}
}
