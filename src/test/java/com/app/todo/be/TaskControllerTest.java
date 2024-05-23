package com.app.todo.be;

import com.app.todo.be.controller.TaskController;
import com.app.todo.be.model.Task;
import com.app.todo.be.service.TaskService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private Task task;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        task = Task.builder()
                .id("1")
                .title("Test Task")
                .description("This is a test task")
                .done(false)
                .build();
    }

    @Test
    void testInsertTask() {
        when(taskService.insertTask(any(Task.class))).thenReturn(task);

        ResponseEntity<Task> response = taskController.insertTask(task);

        Assertions.assertEquals(200, response.getStatusCodeValue());
        Assertions.assertEquals(task, response.getBody());
        verify(taskService, times(1)).insertTask(any(Task.class));
    }

    @Test
    void testGetTasks() {
        List<Task> tasks = Collections.singletonList(task);
        when(taskService.getTasks()).thenReturn(tasks);

        List<Task> response = taskController.getTasks();

        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals(task, response.get(0));
        verify(taskService, times(1)).getTasks();
    }

    @Test
    void testGetTaskById() {
        when(taskService.getTaskById(anyString())).thenReturn(Optional.of(task));

        Optional<Task> response = taskController.getTaskById("1");

        Assertions.assertEquals(true, response.isPresent());
        Assertions.assertEquals(task, response.get());
        verify(taskService, times(1)).getTaskById(anyString());
    }

    @Test
    void testUpdateTaskById_Success() {
        when(taskService.updateTaskById(anyString(), any(Task.class))).thenReturn(true);

        ResponseEntity<String> response = (ResponseEntity<String>) taskController.updateTaskById("1", task);

        Assertions.assertEquals(200, response.getStatusCodeValue());
        Assertions.assertEquals("Successfully updated task: 1", response.getBody());
        verify(taskService, times(1)).updateTaskById(anyString(), any(Task.class));
    }

    @Test
    void testUpdateTaskById_NotFound() {
        when(taskService.updateTaskById(anyString(), any(Task.class))).thenReturn(false);

        ResponseEntity<String> response = (ResponseEntity<String>) taskController.updateTaskById("1", task);

        Assertions.assertEquals(404, response.getStatusCodeValue());
        verify(taskService, times(1)).updateTaskById(anyString(), any(Task.class));
    }

    @Test
    void testDeleteById() {
        doNothing().when(taskService).deleteById(anyString());

        taskController.deleteById("1");

        verify(taskService, times(1)).deleteById(anyString());
    }
}
