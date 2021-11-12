package com.br.mongoapi.service;

import com.br.mongoapi.exception.EmailExistsException;
import com.br.mongoapi.exception.UsernameExistsException;
import com.br.mongoapi.mapper.ApiUserMapper;
import com.br.mongoapi.repository.UserApiRepository;
import com.br.mongoapi.requests.ApiUserRequestBody;
import com.br.mongoapi.model.ApiUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Log4j2
public class ApiUserDetailsService implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private final UserApiRepository userApiRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApiUser apiUser = userApiRepository.findByUsername(username);
        log.info("User authorities: " + apiUser.getAuthorities());
        return Optional.ofNullable(userApiRepository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }


    @Transactional(rollbackFor = Exception.class)
    public void save(ApiUserRequestBody apiUserRequestBody) {
        usernameAndEmailVerification(apiUserRequestBody.getUsername(), apiUserRequestBody.getEmail());
        ApiUser apiUser = ApiUserMapper.INSTANCE.toUserApi(apiUserRequestBody);
        String password = generatePassword();
        apiUser.setPassword(encodePassword(password));
        log.info(apiUser.getUsername() + " password: " + password);
        userApiRepository.save(apiUser);
    }

    private void usernameAndEmailVerification(String username, String email){

        ApiUser apiUserByUsername = userApiRepository.findByUsername(username);
        ApiUser apiUserByEmail = userApiRepository.findApiUserByEmail(email);

        if(apiUserByUsername != null){
            throw new UsernameExistsException("Username is already in use.");
        }
        if(apiUserByEmail != null){
            throw new EmailExistsException("Email is already in use.");
        }

    }

    private String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
