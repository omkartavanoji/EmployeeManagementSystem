package com.example.employee.empdto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class EmployeeDTO {
	int id;
	@Email
	private String email;
	private String password;
	private String firstname;
	private String lastname;
	private String department;
	private String salary;
}
