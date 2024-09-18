package com.example.crm_test.controller;

import com.example.crm_test.dto.TaskDto;
import com.example.crm_test.dto.TaskRequestDto;
import com.example.crm_test.model.enums.TaskStatus;
import com.example.crm_test.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/{id}")
    public ModelAndView viewTask(@PathVariable Integer id) {
        TaskDto task = taskService.getTaskById(id);
        return new ModelAndView("task-details", "task", task);
    }

    @GetMapping
    public ModelAndView getAllTasks() {
        ModelAndView mav = new ModelAndView("tasks");
        List<TaskDto> tasks = taskService.getAllTasks();

        mav.addObject("tasks", tasks);
        return mav;
    }

    @GetMapping("/new")
    public ModelAndView showCreateTaskForm() {
        ModelAndView mav = new ModelAndView("create_task");
        mav.addObject("taskRequestDto", new TaskRequestDto());

        return mav;
    }

    @PostMapping
    public ModelAndView createTask(@ModelAttribute TaskRequestDto taskRequestDto) {
        ModelAndView mav = new ModelAndView("redirect:/tasks");
        TaskDto task = taskService.saveTask(taskRequestDto);
        mav.addObject("task", task);

        return mav;
    }

    @PostMapping("/update-status")
    public ModelAndView updateStatusRedirect(
            @RequestParam("taskId") Integer taskId,
            @RequestParam("status") TaskStatus status) {
        ModelAndView mav = new ModelAndView("redirect:/tasks");
        taskService.updateStatus(taskId, status);

        return mav;
    }

    @PutMapping("/{id}/status/{status}")
    public ModelAndView updateStatus(@PathVariable Integer id, @PathVariable TaskStatus status) {
        ModelAndView mav = new ModelAndView("redirect:/tasks");
        taskService.updateStatus(id, status);

        return mav;
    }

    @PutMapping("/{taskId}/contact/{contactId}")
    public ResponseEntity<TaskDto> assignContact(@PathVariable Integer taskId, @PathVariable Integer contactId) {
        return ResponseEntity.ok(taskService.assignContact(taskId, contactId));
    }
}
