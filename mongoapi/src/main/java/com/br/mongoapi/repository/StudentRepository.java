package com.br.mongoapi.repository;

import com.br.mongoapi.model.Student;
import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface StudentRepository extends MongoRepository<Student, String> {

   //@Query("{'firstName': ?0}")
   //Optional<Student> findStudentsByFirstName(String email);

   Optional<Student> findStudentByEmail(String email);
}
