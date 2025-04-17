package com.example.employee.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.employee.empdto.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {
	List<Task> findByEmployee_Id(int id);
	Optional<Task> findByEmployee_IdAndId(int id,int id1 );
}
