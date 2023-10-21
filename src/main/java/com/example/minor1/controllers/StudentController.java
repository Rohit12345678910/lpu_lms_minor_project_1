package com.example.minor1.controllers;

import com.example.minor1.domain.Student;
import com.example.minor1.dtos.CreateStudentRequest;
import com.example.minor1.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;
    @PostMapping("/student")
    public void createStudent(@RequestBody @Valid CreateStudentRequest createStudentRequest){
         studentService.createStudent(createStudentRequest.to());
    }

    @GetMapping("/student")
    public Student getStudentById(@RequestParam("id") int id){
       return studentService.getStudent(id);
    }
}
