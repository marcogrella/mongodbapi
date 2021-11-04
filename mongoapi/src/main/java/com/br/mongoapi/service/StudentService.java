package com.br.mongoapi.service;

import com.br.mongoapi.model.Student;
import com.br.mongoapi.repository.StudentRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    StudentRepository repository;

    public List<Student> findAll() {
        return repository.findAll();
    }
}
