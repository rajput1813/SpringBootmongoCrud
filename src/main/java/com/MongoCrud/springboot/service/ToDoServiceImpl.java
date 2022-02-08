package com.MongoCrud.springboot.service;

import com.MongoCrud.springboot.exception.ToDoCollectionException;
import com.MongoCrud.springboot.model.ToDoDto;
import com.MongoCrud.springboot.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ToDoServiceImpl implements ToDoService {
    @Autowired
    ToDoRepository toDoRepository;
    @Override
    public void createToDo(ToDoDto toDoDto) throws ConstraintViolationException, ToDoCollectionException {
        Optional<ToDoDto>toDoDtoOptional = toDoRepository.findByTodo(toDoDto.getTodo());
        if(toDoDtoOptional.isPresent()){
            throw new ToDoCollectionException(ToDoCollectionException.todoAlreadyExist());
        }
        else{
            toDoDto.setCreatedAt(new Date(System.currentTimeMillis()));
            toDoRepository.save(toDoDto);
        }
    }

    @Override
    public List<ToDoDto> getAllTodos() {
     List<ToDoDto>todos = toDoRepository.findAll();
     if(todos.size()>0){
         return todos;
     }
     else{
         return new ArrayList<ToDoDto>();
     }
    }

    @Override
    public ToDoDto getTodosById(String id) throws ToDoCollectionException {
        Optional<ToDoDto>toDoDtoOptional = toDoRepository.findById(id);
        if(!toDoDtoOptional.isPresent()){
            throw new ToDoCollectionException(ToDoCollectionException.NotFoundException(id));
        }
        else {
            return toDoDtoOptional.get();
        }
    }

    @Override
    public void deleteById(String id) throws ToDoCollectionException {
        Optional<ToDoDto>toDoDtoOptional = toDoRepository.findById(id);
        if(!toDoDtoOptional.isPresent()){
            throw new ToDoCollectionException(ToDoCollectionException.NotFoundException(id));
        }
        else {
            toDoRepository.deleteById(id);
        }
    }

    @Override
    public void updateTodo(String id, ToDoDto todo) throws ToDoCollectionException {
        Optional<ToDoDto>toDoDtoWithId = toDoRepository.findById(id);
        Optional<ToDoDto>toDoDtoWithSameName = toDoRepository.findByTodo(todo.getTodo());
        if(toDoDtoWithId.isPresent() ){
            if(toDoDtoWithSameName.isPresent()&& !toDoDtoWithSameName.get().getId().equals(id)){
                throw new ToDoCollectionException(ToDoCollectionException.todoAlreadyExist());
            }
            ToDoDto todoUpdate = toDoDtoWithId.get();
            todoUpdate.setTodo(todo.getTodo());
            todoUpdate.setDescription(todo.getDescription());
            todoUpdate.setCompleted(todo.getCompleted());
            todoUpdate.setUpdatedAt(new Date(System.currentTimeMillis()));
            toDoRepository.save(todoUpdate);
        }else{
            throw new ToDoCollectionException(ToDoCollectionException.NotFoundException(id));
        }
    }
}
