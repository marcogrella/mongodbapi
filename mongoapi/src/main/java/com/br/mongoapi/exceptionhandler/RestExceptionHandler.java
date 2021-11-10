package com.br.mongoapi.exceptionhandler;

import com.br.mongoapi.exception.BadRequestException;
import com.br.mongoapi.exception.EmailExistsException;
import com.br.mongoapi.exception.util.ErrorInfo;
import com.br.mongoapi.exception.util.HttpResponse;
import com.br.mongoapi.exception.util.HttpResponseBinding;
import com.br.mongoapi.model.Gender;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<HttpResponse> handleEmailExistsException(EmailExistsException e) {
        return createHttpResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<HttpResponse> handleBadRequestException(BadRequestException bre) {
        return createHttpResponse(HttpStatus.BAD_REQUEST, bre.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HttpResponseBinding> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<ErrorInfo> errorList = new ArrayList<>();

        for(int i =0; i < e.getBindingResult().getFieldErrors().size(); i++){
            ErrorInfo errorsInfo = new ErrorInfo();
            errorsInfo.setField(fieldErrors.get(i).getField());
            errorsInfo.setFieldMessage(fieldErrors.get(i).getDefaultMessage());
            errorList.add(errorsInfo);
        }

        return createHttpResponseWithBindingResults(HttpStatus.BAD_REQUEST, e.getMessage(), errorList);
    }


    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<HttpResponseBinding> handleInvalidFormatException(InvalidFormatException e) {

        List<ErrorInfo> errorInfoList = new ArrayList<>();

        if(e.getTargetType().isAssignableFrom(Gender.class)){
            ErrorInfo errorsInfo = new ErrorInfo();
            errorsInfo.setField("Gender");
            errorsInfo.setFieldMessage(e.getValue() + " is not valid for GENDER field (insert MALE or FEMALE)" );
            errorInfoList.add(errorsInfo);
        }
        return createHttpResponseWithBindingResults(HttpStatus.BAD_REQUEST, e.getMessage(), errorInfoList);
    }


    /* auxiliary method */
    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message){

        HttpResponse httpResponseBody = new HttpResponse(new Date(), httpStatus.value(), httpStatus,
                httpStatus.getReasonPhrase(), message);
        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }


    private ResponseEntity<HttpResponseBinding> createHttpResponseWithBindingResults(HttpStatus httpStatus, String message,
                                                                              List<ErrorInfo> errorsInfoList){
        /*
        if(fields.equals("") && fieldsMessage.equals("")){
            HttpResponseBinding httpResponseBody = new HttpResponseBinding(new Date(), httpStatus.value(), httpStatus,
                    httpStatus.getReasonPhrase(), message, fields, fieldsMessage);
            return new ResponseEntity<>(httpResponseBody, httpStatus);
        }
        */

        HttpResponseBinding httpResponseBody = new HttpResponseBinding(new Date(), httpStatus.value(), httpStatus,
                httpStatus.getReasonPhrase(), message, errorsInfoList);

        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }



}
