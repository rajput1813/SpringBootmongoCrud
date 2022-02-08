package com.MongoCrud.springboot.controllers;

import com.MongoCrud.springboot.exception.ToDoCollectionException;
import com.MongoCrud.springboot.model.ToDoDto;
import com.MongoCrud.springboot.repository.ToDoRepository;
import com.MongoCrud.springboot.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class ToDoController {
    @Autowired
    private ToDoRepository toDoRepository;
    @Autowired
    private ToDoService toDoService;
    @GetMapping("/todos")
    public ResponseEntity<?>getAllToDos(){
        List<ToDoDto> todos = toDoService.getAllTodos();

            return new ResponseEntity<List<ToDoDto>>(todos,todos.size()>0 ? HttpStatus.OK: HttpStatus.NOT_FOUND);
    }
    @PostMapping("/todos")
    public ResponseEntity<?>createTodo(@RequestBody ToDoDto todo){
        try{

   toDoService.createToDo(todo);
             return new ResponseEntity<ToDoDto>(todo,HttpStatus.OK);
        }
        catch(ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
        }
        catch (ToDoCollectionException e){
            return  new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
        }

    }
    @GetMapping("/todos/{id}")
    public ResponseEntity<?>getSingleToDo(@PathVariable("id") String id){

        try{
            return new ResponseEntity<ToDoDto>(toDoService.getTodosById(id),HttpStatus.OK);
        }
       catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<?>updateById(@PathVariable("id") String id,@RequestBody ToDoDto toDo) {

        try{
            toDoService.updateTodo(id,toDo);
            return new ResponseEntity<>("updated todo with Id "+ id,HttpStatus.OK);
        } catch (ConstraintViolationException e) {
           return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
        }
        catch (ToDoCollectionException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/todos/{id}")
    public ResponseEntity<?>deleteById(@PathVariable("id") String id){
        try{
           toDoService.deleteById(id);
            return new ResponseEntity<>("successfully deleted with Id = "+ id,HttpStatus.OK);
        }
        catch (ToDoCollectionException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}
