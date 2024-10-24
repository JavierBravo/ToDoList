package com.kean.todolist;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ToDoListController.class)
public class ToDoListControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    
    @MockBean
    private ToDoListRepository toDoListRepository; // Simula el repositorio

    @Test
    void shouldCreateNewToDoList() throws Exception {
        String newToDoListJson = """
            {
                "id": 1,
                "title": "Estudiar Spring",
                "taskDescription": "Revisar el patrón TDD en Spring"
            }
        """;

        ToDoList mockToDoList = new ToDoList(1L, "Estudiar Spring", "Revisar el patrón TDD en Spring");
        when(toDoListRepository.save(any(ToDoList.class))).thenReturn(mockToDoList);

        mockMvc.perform(post("/todolist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newToDoListJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Estudiar Spring"))
                .andExpect(jsonPath("$.taskDescription").value("Revisar el patrón TDD en Spring"));
    }
}
