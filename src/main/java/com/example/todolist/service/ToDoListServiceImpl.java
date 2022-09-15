package com.example.todolist.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.todolist.entity.ToDoList;
import com.example.todolist.repository.ToDoListRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ToDoListServiceImpl {
    private final ToDoListRepository toDoListRepository;

    @Override
    public List<ToDoList> getAll() {
        return toDoListRepository.findAll();
    }
}
