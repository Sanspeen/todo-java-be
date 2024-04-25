package com.app.todo.be.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@Document("Task")
public class Task {
    @Id
    private String id;
    private String title;
    private String description;
    private Boolean completed;
}
