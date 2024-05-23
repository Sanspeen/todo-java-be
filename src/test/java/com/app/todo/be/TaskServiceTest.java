package com.app.todo.be;

import com.app.todo.be.model.Task;
import com.app.todo.be.repository.TaskRepository;
import com.app.todo.be.service.TaskService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

//First
//F: Se pueden ejecutar muchas pruebas rapido
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

    //First
    //I: Independientes una prueba de otra
    //R: Repetible, se puede ejecutar las veces que quieras
    //S: SELF-VALIDATING: Se valida con solo un click
    //T: Timely: Implementado con TDD ;)
    @Test
    public void testInsertTask() {
        // Arrange
        Task taskToInsert = new Task("1", "Task 1", "Description 1", false);
        when(taskRepositoryMock.save(taskToInsert)).thenReturn(taskToInsert);

        // Act
        Task insertedTask = taskService.insertTask(taskToInsert);

        // Assert
        Assertions.assertNotNull(insertedTask);
        Assertions.assertEquals(taskToInsert, insertedTask);
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
        Assertions.assertEquals(mockTasks.size(), tasks.size());
        Assertions.assertEquals(mockTasks, tasks);
    }

    @Test
    public void testGetTaskById() {
        // Arrange
        Task mockTask = new Task("1", "Task 1", "Description 1", false);
        when(taskRepositoryMock.findById("1")).thenReturn(Optional.of(mockTask));

        // Act
        Optional<Task> task = taskService.getTaskById("1");

        // Assert
        Assertions.assertTrue(task.isPresent());
        Assertions.assertEquals(mockTask, task.get());
    }

    @Test
    public void testUpdateTaskById_ReturnsTrue() {
        // Arrange
        String taskId = "1";
        Task existingTask = new Task(taskId, "Task 1", "Description 1", false);
        Task updatedTask = new Task(taskId, "Updated Task 1", "Updated Description 1", true);
        when(taskRepositoryMock.findById(taskId)).thenReturn(Optional.of(existingTask));

        // Act
        boolean result = taskService.updateTaskById(taskId, updatedTask);

        // Assert
        Assertions.assertTrue(result);

        // Verificamos que el método findById fue llamado una vez con el taskId
        verify(taskRepositoryMock, times(1)).findById(taskId);

        // Capturamos el objeto pasado al método save
        ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepositoryMock, times(1)).save(captor.capture());

        // Obtenemos el objeto capturado
        Task savedTask = captor.getValue();

        // Verificamos los atributos del objeto guardado
        Assertions.assertEquals(updatedTask.getId(), savedTask.getId());
        Assertions.assertEquals(updatedTask.getTitle(), savedTask.getTitle());
        Assertions.assertEquals(updatedTask.getDescription(), savedTask.getDescription());
        Assertions.assertEquals(updatedTask.getDone(), savedTask.getDone());
    }

    @Test
    void testUpdateTaskById_ReturnsFalse() {
        // Arrange
        String taskId = "1";
        Task updatedTask = new Task(taskId, "Updated Task 1", "Updated Description 1", true);
        when(taskRepositoryMock.findById(taskId)).thenReturn(Optional.empty());

        // Act
        boolean result = taskService.updateTaskById(taskId, updatedTask);

        // Assert
        Assertions.assertFalse(result);
        verify(taskRepositoryMock, times(1)).findById(taskId);
        verify(taskRepositoryMock, never()).save(updatedTask);
    }

    @Test
    public void testDeleteById() {
        // Arrange
        Task taskToDelete = new Task("1", "Task 1", "Description 1", false);
        when(taskRepositoryMock.findById("1")).thenReturn(Optional.of(taskToDelete));

        // Act
        taskService.deleteById("1");

        // Assert
        verify(taskRepositoryMock, times(1)).deleteById("1");
    }

}
