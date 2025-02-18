package com.smarttaskboard.app.controller;

import com.smarttaskboard.app.dto.TaskDto;
import com.smarttaskboard.app.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/v1/tasks")
@RequiredArgsConstructor
@Slf4j
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        log.info("Fetching tasks list");
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @PostMapping
    public ResponseEntity<TaskDto> addNewTask(@RequestBody TaskDto taskDto) {
        return ResponseEntity.ok(taskService.addNewTask(taskDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTask(@PathVariable Long id, @RequestBody TaskDto taskDto) {
        taskService.updateTask(id, taskDto);
        return ResponseEntity.ok("Task Was Updated Successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok("Task Was Deleted Successfully");
    }

    @PostMapping("/{id}/users")
    public ResponseEntity<TaskDto> assignUsersToTask(@PathVariable Long id, @RequestBody Set<Long> userIds) {
        return ResponseEntity.ok(taskService.assignUsersToTask(id, userIds));
    }
}
