package com.kean.todolist;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;


import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

/**
 * ToDoListJsonTest
 */
@JsonTest
class ToDoListJsonTest {

    @Autowired
    private JacksonTester<ToDoList> json;

    @Test
    void toDoListSerializationTest() throws IOException {
        ToDoList toDoList = new ToDoList(1875L, "Queam", "Las Torres de Wilde");

        // Verificar que el JSON serializado coincide con un JSON esperado
        assertThat(json.write(toDoList)).isStrictlyEqualToJson("expected.json");
        
        // Verificar que los campos del JSON tienen los valores correctos
        assertThat(json.write(toDoList)).hasJsonPathNumberValue("@.id");
        assertThat(json.write(toDoList)).extractingJsonPathNumberValue("@.id")
                .isEqualTo(1875);

        assertThat(json.write(toDoList)).hasJsonPathStringValue("@.title");
        assertThat(json.write(toDoList)).extractingJsonPathStringValue("@.title")
             .isEqualTo("Queam");

        assertThat(json.write(toDoList)).hasJsonPathStringValue("@.taskDescription");
        assertThat(json.write(toDoList)).extractingJsonPathStringValue("@.taskDescription")
            .isEqualTo("Las Torres de Wilde");
    }


    @Test
    void toDoListDeserializationTest() throws IOException{
        String expected = """
                {
                    "id": 1875,
                    "title": "Queam",
                    "taskDescription": "Las Torres de Wilde"
                }
                """;
        assertThat(json.parse(expected)).isEqualTo(new ToDoList(1875L, "Queam", "Las Torres de Wilde"));

        assertThat(json.parseObject(expected).id()).isEqualTo(1875);
        assertThat(json.parseObject(expected).title()).isEqualTo("Queam");
        assertThat(json.parseObject(expected).taskDescription()).isEqualTo("Las Torres de Wilde");
    }
}