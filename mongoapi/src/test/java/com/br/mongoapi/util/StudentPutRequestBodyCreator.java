package com.br.mongoapi.util;

import com.br.mongoapi.requests.StudentPutRequestBody;

public class StudentPutRequestBodyCreator {

    public static StudentPutRequestBody createStudentPutRequestBody(){
        return StudentPutRequestBody.builder()
                .firstName(StudentCreator.createValidStudent().getFirstName())
                .lastName(StudentCreator.createValidStudent().getLastName())
                .email(StudentCreator.createValidStudent().getEmail())
                .gender(StudentCreator.createValidStudent().getGender())
                .address(StudentCreator.createValidStudent().getAddress())
                .favoriteBookThemes(StudentCreator.createValidStudent().getFavoriteBookThemes())
                .totalSpentInBooks(StudentCreator.createValidStudent().getTotalSpentInBooks())
                .build();
    }
}
