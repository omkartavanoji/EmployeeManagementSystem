	package com.example.employee.empdto;
	
	import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
	import jakarta.persistence.GenerationType;
	import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
	import lombok.Data;
	import lombok.NoArgsConstructor;
	
	@Entity
	@NoArgsConstructor
	@AllArgsConstructor
	@Data
	public class User {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int id;
		private String email;
		private String password;
		private String role;
		@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
		@JoinColumn(name = "employee_id")
		private Employee employee;
	}
