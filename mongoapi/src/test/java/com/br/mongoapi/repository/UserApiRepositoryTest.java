package com.br.mongoapi.repository;

import com.br.mongoapi.constant.UserApiRoles;
import com.br.mongoapi.model.ApiUser;
import com.br.mongoapi.util.ApiUserCreator;
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

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureDataMongo
@TestPropertySource("/application-test.properties")
class UserApiRepositoryTest {

    /* it uses  mongoapitest databases  */

    @Autowired
    private UserApiRepository repository;

    @BeforeEach
    void cleanCollection(){
       this.repository.deleteAll();
    }

    @Test
    @DisplayName("Save a ApiUser when successful")
    void save_PersistsStudent_WhenSuccessful() {
        ApiUser userToBeSaved = ApiUserCreator.createUserToBeSaved();
        ApiUser savedUser = repository.save(userToBeSaved);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isNotNull();
        Assertions.assertThat(savedUser.getEmail()).isEqualTo(userToBeSaved.getEmail());
        Assertions.assertThat(savedUser.isAccountNonExpired()).isTrue();
        Assertions.assertThat(savedUser.isEnabled()).isTrue();
        Assertions.assertThat(savedUser.isCredentialsNonExpired()).isTrue();
        Assertions.assertThat(savedUser.isAccountNonLocked()).isTrue();

    }


    @Test
    @DisplayName("Update a student when successful")
    void update_PersistsStudent_WhenSuccessful() {
        ApiUser userToBeSaved = ApiUserCreator.createUserToBeSaved();
        ApiUser savedUser = repository.save(userToBeSaved);

        savedUser.setAuthorities(UserApiRoles.ROLE_SUPER_ADMIN);

        ApiUser updatedApiUser = repository.save(savedUser);

        Assertions.assertThat(updatedApiUser).isNotNull();
        Assertions.assertThat(updatedApiUser.getId()).isNotNull();
        Assertions.assertThat(savedUser.getId()).isEqualTo(updatedApiUser.getId());
        // Example: Assertions.assertThat("this(string)here").matches("this[(]string[)]here");
        assertEquals(updatedApiUser.getAuthorities().toString(),"["+ UserApiRoles.ROLE_SUPER_ADMIN+"]");

    }


    @Test
    @DisplayName("findByEmail finds an UserApi by email")
    void findByEmail_ThenReturnsUser_WhenSuccessful() {
        ApiUser userToBeSaved = ApiUserCreator.createUserToBeSaved();
        ApiUser savedUser = repository.save(userToBeSaved);

        String email = savedUser.getEmail();
        ApiUser userFoundByEmail = repository.findApiUserByEmail(email);
        Assertions.assertThat(userFoundByEmail).isNotNull();


    }


    @Test
    @DisplayName("findByUsername an UserApi by username")
    void findByUsername_ThenReturnsUser_WhenSuccessful() {
        ApiUser userToBeSaved = ApiUserCreator.createUserToBeSaved();
        ApiUser savedUser = repository.save(userToBeSaved);

        String userName = savedUser.getUsername();
        ApiUser userFoundByUsername = repository.findByUsername(userName);
        Assertions.assertThat(userFoundByUsername).isNotNull();


    }

    @Test
    @DisplayName("Shoud throw a DuplicateKeyException when try to save an User with same email that belongs to another one")
    void save_ThrowDuplicateKeyException_WhenEmailAlreadyTaken() {
        repository.save(ApiUserCreator.createUserToBeSaved());

        Assertions.assertThatExceptionOfType(DuplicateKeyException.class)
                .isThrownBy(() -> this.repository.save(ApiUserCreator.createUserToBeSaved()));


    }






}