package com.br.mongoapi.service;

import com.br.mongoapi.exception.BadRequestException;
import com.br.mongoapi.exception.EmailExistsException;
import com.br.mongoapi.mapper.StudentMapper;
import com.br.mongoapi.model.Student;
import com.br.mongoapi.repository.StudentRepository;
import com.br.mongoapi.requests.StudentPostRequestBody;
import com.br.mongoapi.requests.StudentPutRequestBody;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentService {

    StudentRepository repository;

    public Page<Student> findAllPagination(Pageable peageable) {
        return repository.findAll(peageable);     }

    public Student findByEmailOrThrowBadRequestException(String email) {
        return repository.findStudentByEmail(email).
                orElseThrow(() -> new BadRequestException("Student not found"));

    }

    @Transactional(rollbackFor = Exception.class)
    public Student save(StudentPostRequestBody studentPostRequestBody) {
        verifiesEmailExists(studentPostRequestBody.getEmail());
        Student student = StudentMapper.INSTANCE.toStudent(studentPostRequestBody);
        student.setCreated(LocalDateTime.now());
        return repository.save(student);    }

    public void delete(String email) {
        Student student = findByEmailOrThrowBadRequestException(email);
        repository.deleteByEmail(email);
    }

    public void replace(StudentPutRequestBody studentRequestBody) {

        Student currentUser = findByEmailOrThrowBadRequestException(studentRequestBody.getEmail());
        Student studentToBeUpdated = StudentMapper.INSTANCE.toStudent(studentRequestBody);
        studentToBeUpdated.setCreated(currentUser.getCreated());
        studentToBeUpdated.setId(currentUser.getId());
        repository.save(studentToBeUpdated);
    }

    public void verifiesEmailExists(String email){
        Optional<Student> student = repository.findStudentByEmail(email);
        if(student.isPresent()){
            throw new EmailExistsException("Email already in use");        }
    }
}
