package com.br.mongoapi.repository;

import com.br.mongoapi.model.Student;
import com.br.mongoapi.util.StudentCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureDataMongo
@TestPropertySource("/application-test.properties")
class StudentRepositoryTest {

    /* for tests a database mongoapitest was created */

    @Autowired
    private StudentRepository repository;

    @BeforeEach
    void cleanCollection(){

        this.repository.deleteAll();
    }

    @Test
    @DisplayName("Save a student when successful")
    void save_PersistsStudent_WhenSuccessful() {
        Student studentToBeSaved = StudentCreator.createStudentToBeSaved();
        Student savedStudent = this.repository.save(studentToBeSaved);

        Assertions.assertThat(savedStudent).isNotNull();
        Assertions.assertThat(savedStudent.getId()).isNotNull();
        Assertions.assertThat(savedStudent.getFirstName()).isEqualTo(studentToBeSaved.getFirstName());

    }

    @Test
    @DisplayName("Update a student when successful")
    void update_PersistsStudent_WhenSuccessful() {
        Student studentToBeSaved = StudentCreator.createStudentToBeSaved();
        Student savedStudent = repository.save(studentToBeSaved);

        savedStudent.setFirstName("João Atualizado");
        savedStudent.setLastName("da Silva");


        Student studentUpdated = repository.save(savedStudent);

        Assertions.assertThat(studentUpdated).isNotNull();
        Assertions.assertThat(studentUpdated.getId()).isNotNull();
        Assertions.assertThat(savedStudent.getFirstName()).isEqualTo(studentUpdated.getFirstName());
        Assertions.assertThat(savedStudent.getLastName()).isEqualTo(studentUpdated.getLastName());
        Assertions.assertThat(savedStudent.getId()).isEqualTo(studentUpdated.getId());


    }


    @Test
    @DisplayName("Delete a student when successful")
    void deleteByEmail_RemovesStudent_WhenSuccessful() {
        Student studentToBeSaved = StudentCreator.createStudentToBeSaved();
        Student savedStudent = repository.save(studentToBeSaved);

        repository.deleteByEmail(savedStudent.getEmail());

        Optional<Student> optionalStudent = repository.findStudentByEmail(studentToBeSaved.getEmail());

        Assertions.assertThat(optionalStudent.isEmpty()).isTrue();

    }


    @Test
    @DisplayName("Find a student by email")
    void findByEmail_ThenReturn_WhenSuccessful() {
        Student studentToBeSaved = StudentCreator.createStudentToBeSaved();
        Student savedStudent = repository.save(studentToBeSaved);

        String email = savedStudent.getEmail();
        Optional<Student> optionalStudent = repository.findStudentByEmail(email);
        Assertions.assertThat(optionalStudent.isPresent()).isTrue();
        Assertions.assertThat(optionalStudent.get().getEmail()).isEqualTo("c.silva@email.com");


    }


    @Test
    @DisplayName("Shoud throw a DuplicateKeyException when try to save a Student with same email that belongs to another one")
    void save_ThrowDuplicateKeyException_WhenEmailAlreadyTaken() {
        repository.save(StudentCreator.createStudentToBeSaved());
        Assertions.assertThatExceptionOfType(DuplicateKeyException.class)
                .isThrownBy(() -> this.repository.save( StudentCreator.createStudentToBeSaved()));


    }





}