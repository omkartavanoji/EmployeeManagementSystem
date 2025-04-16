package com.example.employee.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.employee.empdto.Employee;
import com.example.employee.empdto.EmployeeDTO;
import com.example.employee.empdto.EmployeeLoginDTO;
import com.example.employee.empdto.Task;
import com.example.employee.empdto.TaskDTO;
import com.example.employee.empdto.User;
import com.example.employee.repository.EmployeeRepository;
import com.example.employee.repository.TaskRepository;
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

	@Autowired
	TaskRepository taskRepository;

	public ResponseEntity<String> login(EmployeeLoginDTO user) {
		System.out.println(user.getEmail() + "--" + user.getPassword());
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
		String password = employeeDTO.getPassword();
		user.setPassword(new BCryptPasswordEncoder(12).encode(password));
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

	public ResponseEntity<List<User>> getEmployees() {
		List<User> list = userRepository.findAll();
		if (list.isEmpty()) {
			return new ResponseEntity<List<User>>(list, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<User>>(list, HttpStatus.OK);
		}
	}

	public ResponseEntity<Object> getEmployeeById(int id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isEmpty()) {
			return new ResponseEntity<Object>("NO EMPLOYEE PRESENT WITH ID " + id, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Object>(user.get().getEmployee(), HttpStatus.OK);
		}
	}

	public ResponseEntity<Object> deleteEmployee(int id) {
		Optional<User> optional = userRepository.findById(id);
		if (optional.isEmpty()) {
			return new ResponseEntity<Object>("NO DATA FOUND TO DELETE", HttpStatus.NOT_FOUND);
		} else {
			userRepository.deleteById(id);
			return new ResponseEntity<Object>("EMPLOYEE DATA DELETED ", HttpStatus.OK);
		}
	}

	public ResponseEntity<Object> updateEmployee(EmployeeDTO employeeDTO) {
		if (userRepository.existsById(employeeDTO.getId())) {
			User emp = userRepository.findById(employeeDTO.getId()).get();
			emp.getEmployee().setDepartment(employeeDTO.getDepartment());
			emp.getEmployee().setFirstname(employeeDTO.getFirstname());
			emp.getEmployee().setLastname(employeeDTO.getLastname());
			emp.getEmployee().setSalary(employeeDTO.getSalary());
			employeeRepository.save(emp.getEmployee());
			return new ResponseEntity<Object>("EMPLOYEE DATA UPDATED SUCCESSFULLY", HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>("NO EMPLOYEE PRESENT", HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<Object> updateEmployeeRole(int id, String role) {
		User user = userRepository.findById(id).get();
		if (user != null) {
			user.setRole(role);
			userRepository.save(user);
			return new ResponseEntity<Object>("ROLE UPDATED", HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>("NO USER FOUND", HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<Object> assignTaskEmployee(TaskDTO taskDTO) {
		Optional<User> emp =userRepository.findById(taskDTO.getEmployee_id());
		if (emp.isPresent()) {
			Task task = new Task();
			task.setTitle(taskDTO.getTitle());
			task.setDescription(taskDTO.getDescription());
			task.setStatus(taskDTO.getStatus());
			task.setEmployee(emp.get().getEmployee());
			taskRepository.save(task);
			return new ResponseEntity<Object>("Task Assigned Succesfully", HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>("Error Occured", HttpStatus.CONFLICT);
		}
	}
}
