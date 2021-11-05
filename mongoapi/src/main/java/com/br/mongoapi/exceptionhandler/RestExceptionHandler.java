package com.br.mongoapi.exceptionhandler;

import com.br.mongoapi.exception.BadRequestException;
import com.br.mongoapi.exception.EmailExistsException;
import com.br.mongoapi.exception.util.HttpResponse;
import com.br.mongoapi.exception.util.HttpResponseBinding;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler {


    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<HttpResponse> handleEmailExistsException(EmailExistsException e) {

        return createHttpResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HttpResponseBinding> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

        /* capturing binding fields from binding results */
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

        System.out.println(fields);
        System.out.println(fieldsMessage);

        return createHttpResponseWithBindingResults(HttpStatus.BAD_REQUEST, e.getMessage(), fields, fieldsMessage);
    }


    /* auxiliary method */
    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message){

        HttpResponse httpResponseBody = new HttpResponse(new Date(), httpStatus.value(), httpStatus,
                httpStatus.getReasonPhrase(), message);
        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }


    private ResponseEntity<HttpResponseBinding> createHttpResponseWithBindingResults(HttpStatus httpStatus, String message,
                                                                              String fields, String fieldsMessage){
        HttpResponseBinding httpResponseBody = new HttpResponseBinding(new Date(), httpStatus.value(), httpStatus,
                httpStatus.getReasonPhrase(), message, fields, fieldsMessage);

        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }



}
