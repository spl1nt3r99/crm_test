package com.example.crm_test.service.impl;

import com.example.crm_test.dto.TaskDto;
import com.example.crm_test.dto.TaskRequestDto;
import com.example.crm_test.mapper.TaskMapper;
import com.example.crm_test.model.Contact;
import com.example.crm_test.model.Task;
import com.example.crm_test.model.enums.TaskStatus;
import com.example.crm_test.repository.ContactRepository;
import com.example.crm_test.repository.TaskRepository;
import com.example.crm_test.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {TaskServiceImpl.class})
class TaskServiceImplTest {

    @MockBean
    private TaskRepository taskRepository;
    @MockBean
    private ContactRepository contactRepository;
    @MockBean
    private TaskMapper taskMapper;

    @Autowired
    private TaskService taskService;

    @Test
    void test_getTaskById() {
        var id = 1;
        var task = mock(Task.class);
        var taskDto = mock(TaskDto.class);

        given(taskRepository.findById(id)).willReturn(Optional.of(task));
        given(taskMapper.toDto(task)).willReturn(taskDto);

        var returned = taskService.getTaskById(id);

        verify(taskRepository).findById(id);
        assertEquals(taskDto, returned);
    }

    @Test
    void test_getTaskById_NotFound() {
        var id = 1;

        given(taskRepository.findById(id)).willReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> taskService.getTaskById(id));
    }

    @Test
    void test_getAllTasks() {
        List<Task> tasks = mock(List.class);
        List<TaskDto> taskDtos = mock(List.class);

        given(taskRepository.findAllByOrderByIdDesc()).willReturn(tasks);
        given(taskMapper.toList(tasks)).willReturn(taskDtos);

        var returned = taskService.getAllTasks();

        verify(taskRepository).findAllByOrderByIdDesc();
        assertEquals(taskDtos, returned);
    }

    @Test
    void test_saveTask() {
        var taskRequestDto = mock(TaskRequestDto.class);
        var task = mock(Task.class);
        var taskDto = mock(TaskDto.class);

        when(taskRequestDto.getDescription()).thenReturn("Description");
        when(taskRequestDto.getExecutionPeriod()).thenReturn(LocalDate.now());
        given(taskRepository.save(argThat((Task t) ->
                taskRequestDto.getDescription().equals(t.getDescription())
                        && taskRequestDto.getExecutionPeriod().equals(t.getExecutionPeriod())
                        && TaskStatus.OPEN.equals(t.getStatus()))))
                .willReturn(task);
        given(taskMapper.toDto(task)).willReturn(taskDto);

        var returned = taskService.saveTask(taskRequestDto);

        verify(taskRepository).save(any());
        assertEquals(taskDto, returned);
    }

    @Test
    void test_updateStatus() {
        var id = 1;
        var taskStatus = TaskStatus.IN_PROGRESS;
        var task = mock(Task.class);
        var savedTask = mock(Task.class);
        var taskDto = mock(TaskDto.class);

        given(taskRepository.findById(id)).willReturn(Optional.of(task));
        given(taskRepository.save(argThat((Task t) -> taskStatus.equals(t.getStatus())))).willReturn(savedTask);
        given(taskMapper.toDto(any())).willReturn(taskDto);

        var returned = taskService.updateStatus(id, taskStatus);

        verify(taskRepository).findById(id);
        verify(taskRepository).save(task);
        verify(taskMapper).toDto(any());
        assertEquals(taskDto, returned);
    }

    @Test
    void test_assignContact() {
        var taskId = 1;
        var contactId = 2;
        var task = mock(Task.class);
        var contact = mock(Contact.class);
        var savedTask = mock(Task.class);
        var taskDto = mock(TaskDto.class);

        given(taskRepository.findById(taskId)).willReturn(Optional.of(task));
        given(contactRepository.findById(contactId)).willReturn(Optional.of(contact));
        given(taskRepository.save(argThat((Task t) -> contact.equals(t.getContact())))).willReturn(savedTask);
        given(taskMapper.toDto(savedTask)).willReturn(taskDto);

        taskService.assignContact(taskId, contactId);

        verify(taskRepository).findById(taskId);
        verify(contactRepository).findById(contactId);
        verify(taskRepository).save(task);
        verify(taskMapper).toDto(any());
    }

    @Test
    void test_deleteContact() {
        var id = 1;

        taskService.deleteTask(id);

        verify(taskRepository).deleteById(id);
    }

}