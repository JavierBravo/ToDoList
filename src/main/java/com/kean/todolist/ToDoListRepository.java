package com.kean.todolist;
import org.springframework.data.repository.CrudRepository;
interface ToDoListRepository extends CrudRepository<ToDoList, Long> {

}