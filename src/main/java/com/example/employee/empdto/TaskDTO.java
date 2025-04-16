package com.example.employee.empdto;

import lombok.Data;

@Data
public class TaskDTO {
	String title;
	String description;
	String status;
	int employee_id;
}

