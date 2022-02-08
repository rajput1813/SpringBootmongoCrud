package com.MongoCrud.springboot.service;

import com.MongoCrud.springboot.exception.ToDoCollectionException;
import com.MongoCrud.springboot.model.ToDoDto;

import javax.validation.ConstraintViolationException;
import java.util.List;

public interface ToDoService {
    public void createToDo(ToDoDto toDoDto) throws ConstraintViolationException, ToDoCollectionException;
    public List<ToDoDto>getAllTodos();
    public ToDoDto getTodosById(String id) throws ToDoCollectionException;
    public void deleteById(String id) throws ToDoCollectionException;
    public void updateTodo(String id,ToDoDto todo) throws ToDoCollectionException;
}
