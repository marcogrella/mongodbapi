package com.br.mongoapi.controller;

import com.br.mongoapi.model.Student;
import com.br.mongoapi.service.StudentService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(value = "/student")
public class StudentController {

    @Autowired
    StudentService service;

    @GetMapping("/all")
    public List<Student> findAllUsers(){
        return service.findAll();
    }

}
