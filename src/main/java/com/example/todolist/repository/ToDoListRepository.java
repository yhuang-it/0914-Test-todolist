package com.example.todolist.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.todolist.entity.ToDoList;

@Repository
public interface ToDoListRepository extends JpaRepository<ToDoList, Long>{
    Optional<ToDoList> findByIdAndTargetDate(Long id, LocalDate targetDate);
}
