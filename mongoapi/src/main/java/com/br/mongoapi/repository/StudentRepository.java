package com.br.mongoapi.repository;

import com.br.mongoapi.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface StudentRepository extends MongoRepository<Student, String> {

   //@Query("{'firstName': ?0}")
   //Optional<Student> findStudentsByFirstName(String email);

   Optional<Student> findStudentByEmail(String email);

   void deleteByEmail(String email);


}
