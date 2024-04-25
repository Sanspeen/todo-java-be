package com.app.todo.be.controller;

import com.app.todo.be.model.Task;
import com.app.todo.be.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/task")
    public ResponseEntity<Task> insertTask(@RequestBody Task task){
        return ResponseEntity.ok(taskService.insertTask(task));
    }

    @GetMapping("/task")
    public List<Task> getTasks(){
        return taskService.getTasks().stream().toList();
    }

    @GetMapping("/task/{id}")
    public Optional<Task> getTaskById(@PathVariable String id){
        return taskService.getTaskById(id);
    }

    @PutMapping("/task/{id}")
    public Object updateTaskById(@PathVariable String id, @RequestBody Task task){
        boolean responseTrigger = taskService.updateTaskById(id, task);
        if(responseTrigger){
            return ResponseEntity.ok("Successfully updated task: ".concat(id));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/task/{id}")
    public void deleteById(@PathVariable String id){
        taskService.deleteById(id);
    }
}
