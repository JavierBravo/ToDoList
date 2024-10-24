package com.kean.todolist;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/todolist")
public class ToDoListController {

    private final ToDoListRepository toDoListRepository;
    private ToDoListController(ToDoListRepository toDoListRepository){
        this.toDoListRepository = toDoListRepository;
    }

    @PostMapping
    private ResponseEntity<ToDoList> createNewToDoList(@RequestBody ToDoList toDoList) {
        ToDoList saveToDoList = toDoListRepository.save(toDoList);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveToDoList);
    }


    @GetMapping("/{requestedId}")
    private ResponseEntity<ToDoList> findById(@PathVariable Long requestedId) {
        Optional<ToDoList> todoListOptional = toDoListRepository.findById(requestedId);
        if (todoListOptional.isPresent()){
            return ResponseEntity.ok(todoListOptional.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
