package com.app.todo.be.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
@Document("task")
public class Task {
    private String id;
    private String title;
    private String description;
    private Boolean done;
}
