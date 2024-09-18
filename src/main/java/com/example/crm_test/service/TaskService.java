package com.example.crm_test.service;

import com.example.crm_test.dto.TaskDto;
import com.example.crm_test.dto.TaskRequestDto;
import com.example.crm_test.model.enums.TaskStatus;

import java.util.List;

public interface TaskService {
    TaskDto getTaskById(Integer id);

    List<TaskDto> getAllTasks();

    TaskDto saveTask(TaskRequestDto taskRequestDto);

    TaskDto updateStatus(Integer id, TaskStatus status);

    TaskDto assignContact(Integer taskId, Integer contactId);

    void deleteTask(Integer id);
}
