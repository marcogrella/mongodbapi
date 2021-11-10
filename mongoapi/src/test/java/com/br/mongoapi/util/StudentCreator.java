package com.br.mongoapi.util;

import com.br.mongoapi.model.Address;
import com.br.mongoapi.model.Gender;
import com.br.mongoapi.model.Student;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StudentCreator {


    public static Student createStudentToBeSaved(){

        Address address = Address.builder()
                .city("São Paulo")
                .country("BR")
                .postCode("0000000000")
                .build();

        List<String> bookThemes = new ArrayList<>();
        bookThemes.add("Computer Science");
        bookThemes.add("Ficction");

        return Student.builder()
                .firstName("Carlos")
                .lastName("Silva")
                .email("c.silva@email.com")
                .gender(Gender.MALE)
                .address(address)
                .favoriteBookThemes(bookThemes)
                .totalSpentInBooks(new BigDecimal(280))
                .created(LocalDateTime.now())
                .build();

    }

    public static Student createValidStudent(){
        Address address = Address.builder()
                .city("São Paulo")
                .country("BR")
                .postCode("0000000000")
                .build();

        List<String> bookThemes = new ArrayList<>();
        bookThemes.add("Computer Science");
        bookThemes.add("Ficction");

        return Student.builder()
                .id("1")
                .firstName("Carlos")
                .lastName("Silva")
                .email("c.silva@email.com")
                .gender(Gender.MALE)
                .address(address)
                .favoriteBookThemes(bookThemes)
                .totalSpentInBooks(new BigDecimal(280))
                .created(LocalDateTime.now())
                .build();

    }


}
