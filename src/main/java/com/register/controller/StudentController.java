package com.register.controller;

import com.register.model.Student;
import com.register.repository.StudentRepository;
import com.register.storage.FileSystemStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController()
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FileSystemStorageService storageService;

    @GetMapping("/list")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @PostMapping("/create")
    public Student register(@RequestPart("student") Student student, @RequestPart("file") MultipartFile file) {
        String imagePath = storageService.store(file);
        student.setImagePath(imagePath);
        return studentRepository.save(student);
    }
}