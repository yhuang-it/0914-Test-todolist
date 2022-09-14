package com.example.todolist.service;

import java.util.List;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.todolist.entity.ToDoList;
import com.example.todolist.repository.ToDoListRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ToDoListService {
    private final ToDoListRepository toDoListRepository;

    public List<ToDoList> getAll() {
        return toDoListRepository.findAll();
    }

    public ToDoList getToDoList(Long id) {
        Optional<ToDoList> toDoList = toDoListRepository.findById(id);

        if (!toDoList.isPresent()) {
            throw new IllegalStateException("Todo list with id " + id + " could not be found");
        }

        return toDoList.get();
    }

    public void deleteToDoList(Long id) {
        boolean exists = toDoListRepository.existsById(id);

        if (!exists) {
            throw new IllegalStateException("Todo list with id " + id + " does not exist");
        }
        toDoListRepository.deleteById(id);
    }

    public void addToDoList(ToDoList toDoList) {
        toDoListRepository.save(toDoList);
    }

    @Transactional
    public void updateToDoList(Long id) {
        ToDoList toDoList = toDoListRepository.findById(id)
        .orElseThrow(()-> new IllegalStateException("Todo list with id " + id + " does not exist"));

        if (id != null && !id.equals(toDoList.getId())) {
            toDoList.setId(id);
        }
    }

}
