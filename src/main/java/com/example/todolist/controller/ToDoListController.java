package com.example.todolist.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todolist.entity.ToDoList;
import com.example.todolist.service.ToDoListService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/todolist")
@AllArgsConstructor
public class ToDoListController {
    private final ToDoListService toDoListService;

    //Get all todo items
    @GetMapping("/all")
    public List<ToDoList> getAll() {
        return toDoListService.getAll();
    }

    //Get item by id
    @GetMapping("/{id}")
    ToDoList getToDoList(@PathVariable Long id) {
        return toDoListService.getToDoList(id);
    }

    //Add todo
    @PostMapping("/add")
    public void addToDoList(@RequestBody ToDoList toDoList) {
        toDoListService.addToDoList(toDoList);
    }
    
    //Delete todo
    @DeleteMapping("/{id}")
    void deleteToDoList(@PathVariable Long id) {
        toDoListService.deleteToDoList(id);
    }

    //Update todo
    @PutMapping("/{id}")
    void updateToDoList(@PathVariable Long id) {
        toDoListService.updateToDoList(id);
    }
}
