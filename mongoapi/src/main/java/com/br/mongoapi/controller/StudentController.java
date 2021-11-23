package com.br.mongoapi.controller;

import com.br.mongoapi.model.Student;
import com.br.mongoapi.repository.StudentRepository;
import com.br.mongoapi.requests.StudentPostRequestBody;
import com.br.mongoapi.requests.StudentPutRequestBody;
import com.br.mongoapi.service.StudentService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/student")
@AllArgsConstructor
@Log4j2
public class StudentController {

    StudentService service;
    StudentRepository repository;


    @GetMapping("/allpagination")
    public ResponseEntity<Page<Student>> findAllPagination(@ParameterObject Pageable peageable){
        return ResponseEntity.ok(service.findAllPagination(peageable));
    }

    @GetMapping("/byemail/{email}")
    public ResponseEntity<Student> findStudentByEmail(@PathVariable String email) {
        return ResponseEntity.ok(service.findByEmailOrThrowBadRequestException(email));
    }

    @PostMapping("/new")
    public ResponseEntity<Student> saveStudent(@RequestBody @Valid StudentPostRequestBody studentPostRequestBody) {
        return new ResponseEntity<>(service.save(studentPostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping("/deletebyemail/{email}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String email) {
        service.delete(email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateStudent(@RequestBody @Valid StudentPutRequestBody studentPutRequestBody){
        service.replace(studentPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
