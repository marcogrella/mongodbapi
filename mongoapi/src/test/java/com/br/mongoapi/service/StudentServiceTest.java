package com.br.mongoapi.service;

import com.br.mongoapi.exception.BadRequestException;
import com.br.mongoapi.exception.EmailExistsException;
import com.br.mongoapi.model.Student;
import com.br.mongoapi.repository.StudentRepository;
import com.br.mongoapi.util.StudentCreator;
import com.br.mongoapi.util.StudentPostRequestBodyCreator;
import com.br.mongoapi.util.StudentPutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
class StudentServiceTest {

    @InjectMocks
    StudentService studentService;
    @Mock
    StudentRepository studentRepositoryMock;


    @BeforeEach
    void setUp() {
        PageImpl<Student> studentsPage = new PageImpl<>(List.of(StudentCreator.createValidStudent()));

        BDDMockito.when(studentRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(studentsPage);

        BDDMockito.when(studentRepositoryMock.findStudentByEmail(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(StudentCreator.createValidStudent()));

        BDDMockito.when(studentRepositoryMock.save(ArgumentMatchers.any(Student.class)))
                .thenReturn(StudentCreator.createValidStudent());

        BDDMockito.doNothing().when(studentRepositoryMock).delete(ArgumentMatchers.any(Student.class));

    }


    @DisplayName("findAllPagination returns Pagination with the list of students")
    @Test
    void list_ReturnsListOfStudentstInsidePageObject_WhenSuccessful() {

        String expectedFirstName = StudentCreator.createValidStudent().getFirstName();

        Page<Student> studentPage = studentService.findAllPagination(PageRequest.of(1, 1));

        Assertions.assertThat(studentPage)
                .isNotEmpty()
                .isNotNull()
                .hasSize(1);

        Assertions.assertThat(studentPage.toList().get(0).getFirstName()).isEqualTo(expectedFirstName);

    }

    @DisplayName("find findByEmailThrowBadRequestException returns a student when successful")
    @Test
    void findByEmailThrowBadRequestException_ReturnsStudent_WhenSuccesful() {

        String expectedId = StudentCreator.createValidStudent().getId();

        Student studentFound = studentService.findByEmailOrThrowBadRequestException("anyString");

        Assertions.assertThat(studentFound).isNotNull();
        Assertions.assertThat(studentFound.getId()).isEqualTo(expectedId);

    }

    @DisplayName("find findByEmailThrowBadRequestException throws BadRequestException when a student is not found")
    @Test
    void findByEmailThrowBadRequestException_ThrowsBadRequest_WhenStudentNotFound() {

        BDDMockito.when(studentRepositoryMock.findStudentByEmail(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> this.studentService.findByEmailOrThrowBadRequestException("anyString"))
                .withMessage("Student not found");

    }


    @DisplayName("save returns a student when successful")
    @Test
    void save_ReturnsStudent_WhenSuccesful() {

        String expectedFirstName = StudentCreator.createValidStudent().getFirstName();

        BDDMockito.when(studentRepositoryMock.findStudentByEmail(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());

        Student student = studentService.save(StudentPostRequestBodyCreator.createStudentPostRequestBody());

        Assertions.assertThat(student).isNotNull();
        Assertions.assertThat(student.getFirstName()).isEqualTo(expectedFirstName);

    }


    @DisplayName("save throws a EmaiExistsException when try to save a student with email already in use ")
    @Test
    void save_ThrowsEmaiExistsException_WhenEmailAlreadyInUse() {


        BDDMockito.when(studentRepositoryMock.findStudentByEmail(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(StudentCreator.createValidStudent()));


        Assertions.assertThatExceptionOfType(EmailExistsException.class)
                .isThrownBy(() -> studentService.save(StudentPostRequestBodyCreator.createStudentPostRequestBody()))
                .withMessage("Email already in use");

    }

    @DisplayName("delete removes a student when successful")
    @Test
    void delete_RemovesAStudentByEmail_WhenSuccessful() {

        Assertions.assertThatCode(() -> studentService.delete("anyString"))
                .doesNotThrowAnyException();

    }

    @Test
    @DisplayName("replace updates student when successful")
    void replace_Updates_WhenSuccessful(){

        Assertions.assertThatCode(() -> studentService.replace(StudentPutRequestBodyCreator.createStudentPutRequestBody()))
                .doesNotThrowAnyException();

    }

}


