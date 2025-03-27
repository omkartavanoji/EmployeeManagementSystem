	package com.example.employee.empdto;
	import com.fasterxml.jackson.annotation.JsonIgnore;
	
	import jakarta.persistence.CascadeType;
	import jakarta.persistence.Entity;
	import jakarta.persistence.GeneratedValue;
	import jakarta.persistence.GenerationType;
	import jakarta.persistence.Id;
	import jakarta.persistence.OneToOne;
	import lombok.AllArgsConstructor;
	import lombok.Data;
	import lombok.NoArgsConstructor;
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Entity
	public class Employee {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int id;
		private String firstname;
		private String lastname;
		private String department;
		private String salary;
		private boolean verify;
		@JsonIgnore
		@OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
	    private User user;
	}
