package com.br.mongoapi.integration;

import com.br.mongoapi.model.Student;
import com.br.mongoapi.repository.StudentRepository;
import com.br.mongoapi.util.ResourceUtils;
import com.br.mongoapi.util.StudentCreator;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
@AutoConfigureDataMongo
public class StudentControllerRestAssuredIT {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentRepository studentRepository;

    private String jsonCorrectStudent;
    private String jsonIncorrectStudentFirstNameNull;
    private String jsonIncorrectStudentLastNameTooLong;
    private String jsonIncorrectStudentEmail;
    private String jsonIncorrectGender;
    private String jsonIncorrectSpentInBooks;

    @BeforeEach
    public void setUp(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/student/";
        studentRepository.deleteAll();

        jsonCorrectStudent = ResourceUtils.getContentFromResource(
                "/json/correctPostRequestBody.json");
        jsonIncorrectStudentFirstNameNull = ResourceUtils.getContentFromResource(
                "/json/incorrectFirstNamePostRequestBody.json");
        jsonIncorrectStudentLastNameTooLong = ResourceUtils.getContentFromResource(
                "/json/incorrectLastNameTooLong.json");
        jsonIncorrectStudentEmail = ResourceUtils.getContentFromResource(
                "/json/incorrectInvalidEmail.json");
        jsonIncorrectGender = ResourceUtils.getContentFromResource(
                "/json/incorrectStudentGender.json");
        jsonIncorrectSpentInBooks = ResourceUtils.getContentFromResource(
                "/json/incorrectValueSpentInBooks.json");
    }




    @Test
    @DisplayName("allpagination returns Page object with list of students")
    public void allpagination_ReturnsPageObjectContentStudentes(){

        Student savedStudent = studentRepository.save(StudentCreator.createValidStudent());

        RestAssured.given()
                .auth()
                .basic("admin","admin")
                .when()
                .get("/allpagination")
                .then()
                .statusCode(200);

    }

    @Test
    @DisplayName("getByEmail returns Student when successful")
    public void getByEmail_ReturnsStudentWhenSucessful(){

        Student student = RestAssured.given()
                .auth()
                .basic("admin","admin")
                .body(jsonCorrectStudent)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/new")
                .then()
                .extract().as(Student.class);

        Student savedStudent = RestAssured.given()
                .given()
                .auth()
                .basic("admin","admin")
                .pathParam("email", student.getEmail())
                .accept(ContentType.JSON)
                .when()
                .get("byemail/{email}")
                .then()
                .extract().as(Student.class);

        Assertions.assertThat(savedStudent).isNotNull();
        Assertions.assertThat(savedStudent.getEmail()).isEqualTo(student.getEmail());

    }




    @Test
    @DisplayName("saveStudent returns Student when successful")
    public void saveStudent_ReturnsStudentWhenSuccessful(){

        Student student = RestAssured.given()
                .auth()
                .basic("admin","admin")
                .body(jsonCorrectStudent)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/new")
                .then()
                .extract().as(Student.class);

        Assertions.assertThat(student).isNotNull();
        Assertions.assertThat(student.getEmail()).isEqualTo("carlos.nogueira@gmail.com.br");
        Assertions.assertThat(student.getFirstName()).isNotNull();
    }

    @Test
    @DisplayName("saveStudent returns status Created when successful")
    public void saveStudent_ReturnsStatusCreated_WhenSuccessful(){

        RestAssured.given()
                .auth()
                .basic("admin","admin")
                .body(jsonCorrectStudent)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/new")
                .then()
                .statusCode(HttpStatus.CREATED.value());

    }

    @Test
    @DisplayName("saveStudent returns Bad Status when postBody has first name null")
    public void saveStudent_ReturnsBadStatus_WhenFirstNameIsNull(){

        RestAssured.given()
                .auth()
                .basic("admin","admin")
                .body(jsonIncorrectStudentFirstNameNull)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/new")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message.fieldMessage", Matchers.contains("field FIRST NAME is mandatory"));

    }

    @Test
    @DisplayName("saveStudent returns Bad Status when postRequestBody has last name too long")
    public void saveStudent_ReturnsBadStatus_WhenFirstNameIsTooLong(){

        RestAssured.given()
                .auth()
                .basic("admin","admin")
                .body(jsonIncorrectStudentLastNameTooLong)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/new")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message.fieldMessage", Matchers.contains("field LAST NAME is too long. Maximum range is 40 characters."));

    }

    @Test
    @DisplayName("saveStudent returns Bad Request when postRequestBody has invalid Email")
    public void saveStudent_ReturnsBadStatus_WhenEmailInvalid(){

        RestAssured.given()
                .auth()
                .basic("admin","admin")
                .body(jsonIncorrectStudentEmail)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/new")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message.fieldMessage", Matchers.contains("field EMAIL is not a valid email."));

    }

    @Test
    @DisplayName("saveStudent returns Bad Request  when Post Request Body has invalid Gender")
    public void saveStudent_ReturnsBadStatus_WhenGenderHaveIncorrectFormat(){

        //Assert.assertThrows(InvalidFormatException.class,
             //   () -> {RestAssured.given()
           //             .body(jsonIncorrectGender)
            //            .contentType(ContentType.JSON)
           //             .accept(ContentType.JSON)
           //             .when()
            //            .post("/new");
           //     });


        RestAssured.given()
                .auth()
                .basic("admin","admin")
                .body(jsonIncorrectGender)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/new")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message.fieldMessage", Matchers.contains("MAAAAALE is not valid for GENDER field (insert MALE or FEMALE)"));

    }

    @Test
    @DisplayName("saveStudent returns Bad Request when Post Request Body has invalid value for total spent books")
    public void saveStudent_ReturnsBadRequest_WhenTotalSpentBooksIsInvalid(){

        RestAssured.given()
                .auth()
                .basic("admin","admin")
                .body(jsonIncorrectSpentInBooks)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/new")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message.fieldMessage", Matchers.contains("Please insert a valid value for field TOTAL SPENT IN BOOKS."));

    }


    @Test
    @DisplayName("update replace a student with updated data")
    public void updateStudent_ReturnNothing_WhenSuccesfull(){

        Student student = RestAssured.given()
                .auth()
                .basic("admin","admin")
                .body(jsonCorrectStudent)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/new")
                .then()
                .extract().as(Student.class);

        student.setFirstName("First Name - updated");
        student.setLastName("Last Name - updated");
        student.setTotalSpentInBooks(BigDecimal.valueOf(190));

        Gson gson = new Gson();
        String updateStudentJson = gson.toJson(student);

        RestAssured.given()
                .auth()
                .basic("admin","admin")
                .body(updateStudentJson)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .put("/update")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        Student updatedStudent = RestAssured.given()
                .auth()
                .basic("admin","admin")
            .given()
            .pathParam("email", student.getEmail())
            .accept(ContentType.JSON)
            .when()
            .get("byemail/{email}")
            .then()
            .extract().as(Student.class);

        Assertions.assertThat(student.getFirstName()).isEqualTo(updatedStudent.getFirstName());
        Assertions.assertThat(student.getLastName()).isEqualTo(updatedStudent.getLastName());
        Assertions.assertThat(student.getTotalSpentInBooks()).isEqualTo(updatedStudent.getTotalSpentInBooks());
        Assertions.assertThat(student.getEmail()).isEqualTo(updatedStudent.getEmail());
    }


    @Test
    @DisplayName("Throws Forbiden Status when user request does n value for total spent books")
    public void shouldThrowsForbiddenStatus_WhenCredencialsIsInvalid(){

        RestAssured.given()
                .auth()
                .basic("xxxxxx","xxxxxxx")
                .body(jsonCorrectStudent)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/new")
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());

    }


    @Test
    @DisplayName("Throws EmailExistsException when try to save a student with an email already in use")
    public void rasesEmailExistsException_WhenTryingToSaveAnStudentWithEmailAlreadyInUse() {

        Student student = RestAssured.given()
                .auth()
                .basic("admin","admin")
                .body(jsonCorrectStudent)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/new")
                .then()
                .extract().as(Student.class);

        RestAssured.given()
                .auth()
                .basic("admin","admin")
                .body(jsonCorrectStudent)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/new")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", Matchers.containsString("Email already in use"));


    }

}


