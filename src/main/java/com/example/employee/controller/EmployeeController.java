package com.example.employee.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.employee.empdto.EmployeeDTO;
import com.example.employee.empdto.EmployeeLoginDTO;
import com.example.employee.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
	EmployeeService employeeService;

	@PostMapping("/login")
	public ResponseEntity<Object> employeeLogin(@RequestBody EmployeeLoginDTO user) {
		System.out.println(user);
		return employeeService.employeeLogin(user);
	}

	@GetMapping("/getinfo/{id}")
	public ResponseEntity<Object> getDetails(@PathVariable int id) {
		return employeeService.getDetails(id);
	}

	@PatchMapping("/updateemployee")
	public ResponseEntity<Object> updateEmployee(@RequestBody EmployeeDTO employeeDTO, Principal principal) {
		return employeeService.updateEmployee(employeeDTO,principal);
	}
	
	@GetMapping("/viewtask")
	public ResponseEntity<Object>viewTask(Principal principal){
		return employeeService.viewTask(principal);
	}
}
