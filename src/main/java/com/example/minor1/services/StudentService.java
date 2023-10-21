package com.example.minor1.services;

import com.example.minor1.domain.Student;
import com.example.minor1.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public void createStudent(Student student){
        studentRepository.save(student);
    }

    public Student getStudent(int id){
        return studentRepository.findById(id).orElse(null);
    }

    public Student find(int studentId) {
        return studentRepository.findById(studentId).orElse(null);
    }
}
