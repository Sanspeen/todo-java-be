package com.app.todo.be;

import com.app.todo.be.model.Task;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TaskTest {

    @Test
    void testSuperBuilder() {
        // Using the @SuperBuilder to create a Task instance
        Task task = Task.builder()
                .id("1")
                .title("Test Title")
                .description("Test Description")
                .done(true)
                .build();

        // Using AssertJ for fluent assertions
        assertThat(task).isNotNull();
        assertThat(task.getId()).isEqualTo("1");
        assertThat(task.getTitle()).isEqualTo("Test Title");
        assertThat(task.getDescription()).isEqualTo("Test Description");
        assertThat(task.getDone()).isTrue();
    }

    @Test
    public void testToString() {
        Task task = Task.builder()
                .id("1")
                .title("Test Task")
                .description("This is a test task description")
                .done(true)
                .build();

        String expectedString = "Task(id=1, title=Test Task, description=This is a test task description, done=true)";
        assertThat(task.toString()).isEqualTo(expectedString);
    }
}
