package com.app.todo.be.DTO;


import com.app.todo.be.config.JacocoExcludeAnnotationGenerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@JacocoExcludeAnnotationGenerated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class TaskDTO {
    private String title;
    private String description;
    private Boolean completed;
}
