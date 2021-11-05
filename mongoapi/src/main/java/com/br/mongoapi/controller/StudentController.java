package com.br.mongoapi.controller;

import com.br.mongoapi.model.Student;
import com.br.mongoapi.repository.StudentRepository;
import com.br.mongoapi.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/student")
@AllArgsConstructor
public class StudentController {

    StudentService service;
    StudentRepository repository;

    @GetMapping("/all")
    public List<Student> findAllUsers(){
        return service.findAll();
    }

    @GetMapping("/byEmail/{email}")
    public ResponseEntity<Student> findStudentByEmail(@PathVariable String email) {
        return ResponseEntity.ok(service.findByEmailThrowBadRequestException(email));
    }


    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Student> saveStudent(@RequestBody @Valid Student student) {
        return new ResponseEntity<>(service.save(student), HttpStatus.CREATED);
    }



}
