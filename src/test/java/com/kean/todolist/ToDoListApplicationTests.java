package com.kean.todolist;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ToDoListApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void shouldReturnASuccessMessageWhenDataIsSaved() {
		
		// Crear el cuerpo del POST en formato JSON
        String requestBody = """
            {
                "id": 1875,
                "title": "Queam",
                "taskDescription": "Las Torres de Wilde"
            }
        """;

		// Configurar los headers para el contenido JSON
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("/todolist", request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void shouldReturnATaskWhenDataIsSaved() {
        ResponseEntity<String> response = restTemplate.getForEntity("/todolist/1875", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        DocumentContext documentContext = JsonPath.parse(response.getBody());
        
        Number id = documentContext.read("$.id");
        assertThat(id).isEqualTo(1875);
        String title = documentContext.read("$.title");
        assertThat(title).isEqualTo("Queam");
        String taskDescription = documentContext.read("$.taskDescription");
        assertThat(taskDescription).isEqualTo("Las Torres de Wilde");

    }

    @Test
    void shouldNotReturnATaskWithAnUnknownId() {
    ResponseEntity<String> response = restTemplate.getForEntity("/todolist/1000", String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(response.getBody()).isBlank();
}
}