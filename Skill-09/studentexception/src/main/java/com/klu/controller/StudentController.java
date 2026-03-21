package com.klu.controller;

import org.springframework.web.bind.annotation.*;

import com.klu.dto.Student;
import com.klu.exception.InvalidInputException;
import com.klu.exception.StudentNotFoundException;

@RestController
@RequestMapping("/student")
public class StudentController {

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable String id){

        int x;

        try{
            x=Integer.parseInt(id);
        }
        catch(NumberFormatException e){
            throw new InvalidInputException("Student ID must be a number");
        }

        if(x<=0){
            throw new InvalidInputException("Student ID must be positive");
        }

        if(x!=101){
            throw new StudentNotFoundException("Student with ID "+x+" not found");
        }

        return new Student(101,"Rishi","CSE");
    }
    @PostMapping("/add")
public Student addStudent(@RequestBody Student s){
    return s;
}
}