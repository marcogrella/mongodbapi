package com.br.mongoapi.service;

import com.br.mongoapi.constant.UserApiRoles;
import com.br.mongoapi.repository.UserApiRepository;
import com.br.mongoapi.util.ApiUserCreator;
import com.br.mongoapi.util.ApiUserPostRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
class ApiUserDetailsServiceTest {

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private UserApiRepository userRepositoryMock;

    @InjectMocks
    private ApiUserDetailsService serviceTest;

    @BeforeEach
    void setUp() {

        BDDMockito.when(userRepositoryMock.findByUsername(ArgumentMatchers.anyString()))
                .thenReturn(ApiUserCreator.createApiUser());

    }


    @Test
    @DisplayName("loadUserByUsername finds an UserApi by username in order to get UserDetails.")
    void loadUserByUsername_thenReturnUserDetails() {


        UserDetails userDetails = serviceTest.loadUserByUsername("userEmail");

        Assertions.assertThat(userDetails.isAccountNonExpired()).isTrue();
        Assertions.assertThat(userDetails.isAccountNonLocked()).isTrue();
        Assertions.assertThat(userDetails.isEnabled()).isTrue();
        Assertions.assertThat(userDetails.isCredentialsNonExpired()).isTrue();
        assertEquals(userDetails.getAuthorities().toString(),"["+ UserApiRoles.ROLE_SUPER_ADMIN+"]");
    }

    @Test
    @DisplayName("loadUserByUsername throws UsernameNotFoundException when username not found.")
    void loadUserByUsername_ThenThrowsUsernameNotFoundException_WhenUsernameNotFound() {

        BDDMockito.when(userRepositoryMock.findByUsername(ArgumentMatchers.anyString())).thenReturn(null);

        Throwable usernameNotFoundException = Assert.assertThrows(UsernameNotFoundException.class, () ->
                serviceTest.loadUserByUsername("userEmail"));

        Assertions.assertThat(usernameNotFoundException).isInstanceOf(UsernameNotFoundException.class);
        Assertions.assertThat(usernameNotFoundException).hasMessage("User not found!");


    }

    @Test
    @DisplayName("save persists an UserApi when both email and username are available.")
    void saveUserApi_WhenEmailAndUsernameAreAvailable() {

        Mockito.when(userRepositoryMock.findByUsername(ArgumentMatchers.anyString())).thenReturn(null);
        Mockito.when(userRepositoryMock.findApiUserByEmail(ArgumentMatchers.anyString())).thenReturn(null);
        serviceTest.save(ApiUserPostRequestBodyCreator.createApiUserPRB());

        Mockito.verify(userRepositoryMock, Mockito.times(1)).findByUsername(ArgumentMatchers.anyString());
        Mockito.verify(userRepositoryMock, Mockito.times(1)).findApiUserByEmail(ArgumentMatchers.anyString());
        Mockito.verify(userRepositoryMock, Mockito.times(1)).save(ArgumentMatchers.any());


    }




}