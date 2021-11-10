package com.br.mongoapi.controller;

import com.br.mongoapi.exception.util.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class ErrorMappingController implements ErrorControllerImpl{
    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public ResponseEntity<HttpResponse> notFound404() {

        HttpResponse httpResponseBody = new HttpResponse(new Date(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
                "Error 404 - Not Found",
                "There is no mapping for this URL.");

        return new ResponseEntity<>(httpResponseBody, HttpStatus.NOT_FOUND);
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
