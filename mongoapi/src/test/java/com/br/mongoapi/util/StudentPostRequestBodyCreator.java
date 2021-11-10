package com.br.mongoapi.util;

import com.br.mongoapi.requests.StudentPostRequestBody;

public class StudentPostRequestBodyCreator {

    public static StudentPostRequestBody createStudentPostRequestBody(){
        return StudentPostRequestBody.builder()
                .firstName(StudentCreator.createStudentToBeSaved().getFirstName())
                .lastName(StudentCreator.createStudentToBeSaved().getLastName())
                .email(StudentCreator.createStudentToBeSaved().getEmail())
                .gender(StudentCreator.createStudentToBeSaved().getGender())
                .address(StudentCreator.createStudentToBeSaved().getAddress())
                .favoriteBookThemes(StudentCreator.createStudentToBeSaved().getFavoriteBookThemes())
                .totalSpentInBooks(StudentCreator.createStudentToBeSaved().getTotalSpentInBooks())
                .created(StudentCreator.createStudentToBeSaved().getCreated())
                .build();
    }

}
