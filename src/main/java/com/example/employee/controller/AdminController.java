package com.example.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.employee.empdto.Employee;
import com.example.employee.empdto.EmployeeDTO;
import com.example.employee.empdto.EmployeeLoginDTO;
import com.example.employee.empdto.User;
import com.example.employee.service.AdminService;
@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {

	@Autowired
	AdminService adminService;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody EmployeeLoginDTO user) {
		return adminService.login(user);
	}

	@PostMapping("/addemployee")
	public ResponseEntity<String> addEmployee(@RequestBody EmployeeDTO employeeDTO) {
		return adminService.addEmployee(employeeDTO);
	}
	
	@GetMapping("/getemployees")
	public ResponseEntity<List<User>> getEmployess(){
		return adminService.getEmployees();
	}
	
	@GetMapping("/getemployee")
	public ResponseEntity<Object>getEmployeeById(@RequestParam int id){
		return adminService.getEmployeeById(id);
	}
	
	@DeleteMapping("/deleteemployee")
	public ResponseEntity<Object> deleteEmployee(@RequestParam int id){
		return adminService.deleteEmployee(id);
	}
	
	@PutMapping("/updateemployee")
	public ResponseEntity<Object> updateEmployee(@RequestBody EmployeeDTO employeeDTO){
		return adminService.updateEmployee(employeeDTO);
	}
	
	@PutMapping("/updateemployeerole/{id}-{role}")
	public ResponseEntity<Object> updateEmployeeRole(@PathVariable int id, @PathVariable String role){
		return adminService.updateEmployeeRole(id,role);
	}
	
}
