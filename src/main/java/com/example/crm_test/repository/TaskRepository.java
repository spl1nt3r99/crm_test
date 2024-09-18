package com.example.crm_test.repository;

import com.example.crm_test.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findAllByOrderByIdDesc();
}
