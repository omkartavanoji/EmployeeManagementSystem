package com.example.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.employee.empdto.Task;

public interface TaskRepository extends JpaRepository<Task, Integer>{

	List<Task> findByEmployeeId(int id);
   
}
