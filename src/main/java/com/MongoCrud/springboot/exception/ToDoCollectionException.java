package com.MongoCrud.springboot.exception;

public class ToDoCollectionException extends Exception{
    private static final Long serialVersionUID= 1L;
    public  ToDoCollectionException(String message){
        super(message);
    }
    public static String NotFoundException(String id){
        return "todo with id = "+id+"Not Found!";
    }
    public static String todoAlreadyExist(){
        return "todo with given Name already Exist";
    }

}
