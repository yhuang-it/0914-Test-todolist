package com.example.todolist.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class ToDoList {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDate targetDate;

    public ToDoList(String description, LocalDate targetDate) {
        this.description = description;
        this.targetDate = targetDate;
      }  
}
