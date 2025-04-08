package com.example.employee.empdto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class EmployeeLoginDTO {
	@Email
	private String email;
	private String password;
}
