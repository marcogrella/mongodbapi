package com.br.mongoapi.controller;

import com.br.mongoapi.model.Student;
import com.br.mongoapi.repository.StudentRepository;
import com.br.mongoapi.requests.StudentPostRequestBody;
import com.br.mongoapi.requests.StudentPutRequestBody;
import com.br.mongoapi.service.StudentService;
import com.br.mongoapi.util.StudentCreator;
import com.br.mongoapi.util.StudentPostRequestBodyCreator;
import com.br.mongoapi.util.StudentPutRequestBodyCreator;
import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class StudentControllerTest {

    @InjectMocks
    StudentController studentController;
    @Mock
    StudentService studentServiceMock;
    @Mock
    StudentRepository studentRepositoryMock;

    @BeforeEach
    void setUp(){
       PageImpl<Student> studentsPage = new PageImpl<>(List.of(StudentCreator.createValidStudent()));

       BDDMockito.when(studentServiceMock.findAllPagination(ArgumentMatchers.any())).thenReturn(studentsPage);

       BDDMockito.when(studentServiceMock.findByEmailOrThrowBadRequestException(ArgumentMatchers.anyString()))
               .thenReturn(StudentCreator.createValidStudent());

       BDDMockito.when(studentServiceMock.save(ArgumentMatchers.any(StudentPostRequestBody.class)))
               .thenReturn(StudentCreator.createValidStudent());

       BDDMockito.doNothing().when(studentServiceMock).delete(ArgumentMatchers.anyString());

       BDDMockito.doNothing().when(studentServiceMock).replace(ArgumentMatchers.any(StudentPutRequestBody.class));


    }


    @DisplayName("Returns a Pagination with the list of students")
    @Test
    void list_ReturnsListOfStudentstInsidePageObject_WhenSuccessful(){

        String expectedFirstName = StudentCreator.createValidStudent().getFirstName();

        Page<Student> studentPage = studentController.findAllPagination(null).getBody();

        Assertions.assertThat(studentPage.toList())
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(studentPage).isNotNull();
        Assertions.assertThat(studentPage.toList().get(0).getFirstName()).isEqualTo(expectedFirstName);

    }

    @DisplayName("Returns a Student when find by email ")
    @Test
    void findByEmail_ReturnsAStudent_WhenSuccessful(){

        Student expectedStudent = StudentCreator.createValidStudent();
        Student student = studentController.findStudentByEmail("anyString").getBody();


        Assertions.assertThat(student).isNotNull();
        Assertions.assertThat(student.getEmail()).isEqualTo(expectedStudent.getEmail());

    }


    @DisplayName("saveStudent returns a student when successful ")
    @Test
    void save_ReturnsAStudent_WhenSuccessful(){

        Student student = studentController.saveStudent(StudentPostRequestBodyCreator.createStudentPostRequestBody()).getBody();

        Assertions.assertThat(student).isNotNull();
        Assertions.assertThat(student.getFirstName()).isEqualTo(StudentCreator.createValidStudent().getFirstName());

        ResponseEntity<Student> entity = studentController.saveStudent(StudentPostRequestBodyCreator.createStudentPostRequestBody());
        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    }


    @DisplayName("delete removes a Student when successful ")
    @Test
    void delete_RemovesStudent_WhenSucessful(){

        Assertions.assertThatCode(() -> studentController.deleteStudent("anyString"))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = studentController.deleteStudent("anyString");
        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }

    @DisplayName("replace updates a student when successful ")
    @Test
    void replace_Updates_WhenSuccessful(){

        Assertions.assertThatCode(() -> studentController.updateStudent(StudentPutRequestBodyCreator.createStudentPutRequestBody()))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = studentController.updateStudent(StudentPutRequestBodyCreator.createStudentPutRequestBody());

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }





}