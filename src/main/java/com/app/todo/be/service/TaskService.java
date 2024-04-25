package com.app.todo.be.service;

import com.app.todo.be.model.Task;
import com.app.todo.be.repository.TaskRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Setter
public class TaskService {

    @Autowired
    TaskRepository taskRepository;


    public Task insertTask(Task task){
        return taskRepository.save(task);
    }

    public List<Task> getTasks(){
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(String id){
        return taskRepository.findById(id);
    }

    public Task updateTaskById(String id, Task task){
        Optional<Task> taskToUpdate = this.taskRepository.findById(id);
        if(taskToUpdate.isEmpty()){
            return null;
        }

        Task updatedTask = new Task();
        updatedTask.setId(id);
        updatedTask.setTitle(task.getTitle());
        updatedTask.setDescription(task.getDescription());
        updatedTask.setCompleted(task.getCompleted());

        return taskRepository.save(updatedTask);
    }

    public void deleteById(String id){
        Optional<Task> taskToDelete = this.taskRepository.findById(id);
        if(taskToDelete.isEmpty()){
            return;
        }
        taskRepository.deleteById(id);
    }
}
