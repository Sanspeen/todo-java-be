package com.app.todo.be;

import com.app.todo.be.model.Task;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class TaskControllerE2ETest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    public void testInsertTask() {
        Task task = Task.builder()
                .title("Test Task")
                .description("This is a test task")
                .done(false)
                .build();

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(task)
                .when()
                .post("/api/v1/task")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", notNullValue())
                .body("title", equalTo("Test Task"))
                .body("description", equalTo("This is a test task"))
                .body("done", equalTo(false));
    }

    @Test
    public void testGetTasks() {
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/v1/task")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE);
    }

    @Test
    public void testGetTaskById() {
        // Primero, crea una tarea para obtener
        Task task = Task.builder()
                .title("Test Task")
                .description("This is a test task")
                .done(false)
                .build();

        Response response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(task)
                .when()
                .post("/api/v1/task")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response();

        String taskId = response.path("id");

        // Ahora obtén la tarea por ID
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/v1/task/" + taskId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(taskId))
                .body("title", equalTo("Test Task"))
                .body("description", equalTo("This is a test task"))
                .body("done", equalTo(false));
    }

    @Test
    public void testUpdateTaskById() {
        // Primero, crea una tarea para actualizar
        Task task = Task.builder()
                .title("Original Task")
                .description("Original description")
                .done(false)
                .build();

        Response response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(task)
                .when()
                .post("/api/v1/task")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response();

        String taskId = response.path("id");

        // Ahora actualiza la tarea
        Task updatedTask = Task.builder()
                .title("Updated Task")
                .description("Updated description")
                .done(true)
                .build();

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(updatedTask)
                .when()
                .put("/api/v1/task/" + taskId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(equalTo("Successfully updated task: " + taskId));

        // Verifica que la tarea fue actualizada
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/v1/task/" + taskId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(taskId))
                .body("title", equalTo("Updated Task"))
                .body("description", equalTo("Updated description"))
                .body("done", equalTo(true));
    }

    @Test
    public void testDeleteTaskById() {
        // Primero, crea una tarea para eliminar
        Task task = Task.builder()
                .title("Task to delete")
                .description("This task will be deleted")
                .done(false)
                .build();

        Response response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(task)
                .when()
                .post("/api/v1/task")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response();

        String taskId = response.path("id");

        // Ahora elimina la tarea
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/api/v1/task/" + taskId)
                .then()
                .statusCode(HttpStatus.OK.value());

        // Verifica que la tarea fue eliminada
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/v1/task/" + taskId)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}