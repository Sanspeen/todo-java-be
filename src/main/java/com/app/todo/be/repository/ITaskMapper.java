package com.app.todo.be.repository;

import com.app.todo.be.DTO.TaskDTO;
import com.app.todo.be.model.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ITaskMapper {
    Task toTask(TaskDTO taskDTO);
    TaskDTO fromTask(Task taskd);
}
