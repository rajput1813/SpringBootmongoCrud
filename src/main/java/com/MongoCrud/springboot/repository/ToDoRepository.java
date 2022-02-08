package com.MongoCrud.springboot.repository;

import com.MongoCrud.springboot.model.ToDoDto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ToDoRepository extends MongoRepository<ToDoDto,String> {
@Query("{'todo':?0}")
    Optional<ToDoDto>findByTodo(String todo);

}
