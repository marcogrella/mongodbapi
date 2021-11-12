package com.br.mongoapi.controller;


import com.br.mongoapi.requests.ApiUserRequestBody;
import com.br.mongoapi.service.ApiUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/user")
@AllArgsConstructor
public class UserApiController {

    @Autowired
    ApiUserDetailsService userService;

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
    public ResponseEntity<String> registerApiUser(@RequestBody @Valid ApiUserRequestBody apiUserRequestBody) {
        userService.save(apiUserRequestBody);
        return new ResponseEntity<>("User was successful registered.", HttpStatus.OK);
    }



}
