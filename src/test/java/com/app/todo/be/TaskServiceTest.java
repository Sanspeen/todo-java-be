package com.app.todo.be;

import com.app.todo.be.model.Task;
import com.app.todo.be.repository.TaskRepository;
import com.app.todo.be.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepositoryMock;
    private TaskService taskService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        taskService = new TaskService();
        taskService.setTaskRepository(taskRepositoryMock);
    }

    @Test
    public void testInsertTask() {
        // Arrange
        Task taskToInsert = new Task("1", "Task 1", "Description 1", false);
        when(taskRepositoryMock.save(taskToInsert)).thenReturn(taskToInsert);

        // Act
        Task insertedTask = taskService.insertTask(taskToInsert);

        // Assert
        assertNotNull(insertedTask);
        assertEquals(taskToInsert, insertedTask);
    }

    @Test
    public void testGetTasks() {
        // Arrange
        List<Task> mockTasks = new ArrayList<>();
        mockTasks.add(new Task("1", "Task 1", "Description 1", false));
        mockTasks.add(new Task("2", "Task 2", "Description 2", true));
        when(taskRepositoryMock.findAll()).thenReturn(mockTasks);

        // Act
        List<Task> tasks = taskService.getTasks();

        // Assert
        assertEquals(mockTasks.size(), tasks.size());
        assertEquals(mockTasks, tasks);
    }


}
