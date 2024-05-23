package com.app.todo.be;

import com.app.todo.be.config.JacocoExcludeAnnotationGenerated;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@JacocoExcludeAnnotationGenerated
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
