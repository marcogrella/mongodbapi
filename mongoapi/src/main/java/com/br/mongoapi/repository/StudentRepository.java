package com.br.mongoapi.repository;

import com.br.mongoapi.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface StudentRepository extends MongoRepository<Student, String> {


}
