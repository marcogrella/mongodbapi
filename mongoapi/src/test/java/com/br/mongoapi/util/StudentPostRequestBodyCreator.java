package com.br.mongoapi.util;

import com.br.mongoapi.requests.StudentPostRequestBody;

import java.io.Serializable;

public class StudentPostRequestBodyCreator implements Serializable {

    private static final long serialVersionUID = -2229229167137409804L;

    public static StudentPostRequestBody createStudentPostRequestBody(){
        return StudentPostRequestBody.builder()
                .firstName(StudentCreator.createStudentToBeSaved().getFirstName())
                .lastName(StudentCreator.createStudentToBeSaved().getLastName())
                .email(StudentCreator.createStudentToBeSaved().getEmail())
                .gender(StudentCreator.createStudentToBeSaved().getGender())
                .address(StudentCreator.createStudentToBeSaved().getAddress())
                .favoriteBookThemes(StudentCreator.createStudentToBeSaved().getFavoriteBookThemes())
                .totalSpentInBooks(StudentCreator.createStudentToBeSaved().getTotalSpentInBooks())
                .build();
    }

}
