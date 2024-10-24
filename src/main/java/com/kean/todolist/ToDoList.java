package com.kean.todolist;
import org.springframework.data.annotation.Id;

record ToDoList(@Id Long id, String title, String taskDescription) {
}