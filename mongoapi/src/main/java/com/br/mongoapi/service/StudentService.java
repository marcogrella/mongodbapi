package com.br.mongoapi.service;

import com.br.mongoapi.exception.BadRequestException;
import com.br.mongoapi.exception.EmailExistsException;
import com.br.mongoapi.model.Student;
import com.br.mongoapi.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentService {

    StudentRepository repository;

    public List<Student> findAll() {
        return repository.findAll();
    }

    public Student findByEmailThrowBadRequestException(String email){
        return repository.findStudentByEmail(email).
                orElseThrow(() -> new BadRequestException("Student not found"));

    }

    @Transactional(rollbackFor = Exception.class)
    public Student save(Student student) {
        verifiesEmailExists(student.getEmail());
        student.setCreated(LocalDateTime.now());
        return repository.save(student);
    }

    public void verifiesEmailExists(String email){
        Optional<Student> student = repository.findStudentByEmail(email);
        if(student.isPresent()){
            throw new EmailExistsException("Email already in use");
        }
    }

}
