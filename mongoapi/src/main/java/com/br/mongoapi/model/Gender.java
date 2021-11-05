package com.br.mongoapi.model;

import javax.validation.constraints.NotBlank;


public enum Gender {

    MALE("Male"),
    FEMALE("Female");

    public String gender;

    Gender(String gender){

        this.gender = gender;
    }



}
