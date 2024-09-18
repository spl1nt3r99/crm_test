package com.example.crm_test.service.impl;

import com.example.crm_test.dto.TaskDto;
import com.example.crm_test.dto.TaskRequestDto;
import com.example.crm_test.mapper.TaskMapper;
import com.example.crm_test.model.Task;
import com.example.crm_test.model.enums.TaskStatus;
import com.example.crm_test.repository.ContactRepository;
import com.example.crm_test.repository.TaskRepository;
import com.example.crm_test.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ContactRepository contactRepository;
    private final TaskMapper taskMapper;

    @Override
    @Cacheable(value = "tasks", key = "#id", sync = true)
    public TaskDto getTaskById(Integer id) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No task found with id: " + id));
        return taskMapper.toDto(task);
    }

    @Override
    @Cacheable(value = "tasks")
    public List<TaskDto> getAllTasks() {
        var tasks = taskRepository.findAllByOrderByIdDesc();
        return taskMapper.toList(tasks);
    }

    @Override
    @CachePut(value = "tasks", key = "#result.id")
    @CacheEvict(value = "tasks", allEntries = true)
    public TaskDto saveTask(TaskRequestDto taskRequestDto) {
        var task = Task.builder()
                .description(taskRequestDto.getDescription())
                .executionPeriod(taskRequestDto.getExecutionPeriod())
                .status(TaskStatus.OPEN)
                .build();
        var saved = taskRepository.save(task);

        return taskMapper.toDto(saved);
    }

    @Override
    @CachePut(value = "tasks", key = "#id")
    @CacheEvict(value = "tasks", allEntries = true)
    public TaskDto updateStatus(Integer id, TaskStatus status) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No task found with id: " + id));
        task.setStatus(status);
        var saved = taskRepository.save(task);

        return taskMapper.toDto(saved);
    }

    @Override
    public TaskDto assignContact(Integer taskId, Integer contactId) {
        var task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NoSuchElementException("No task found with id: " + taskId));
        var contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new NoSuchElementException("No contact found with id: " + contactId));
        task.setContact(contact);
        var saved = taskRepository.save(task);

        return taskMapper.toDto(saved);
    }

    @Override
    @CacheEvict(value = "employees", allEntries = true)
    public void deleteTask(Integer id) {
        taskRepository.deleteById(id);
    }
}
