package com.br.mongoapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class Address {

    @NotEmpty(message = "field COUNTRY is mandatory")
    private String country;
    @NotEmpty(message = "field CITY is mandatory")
    private String city;
    @NotEmpty(message = "field POSTCODE is mandatory")
    private String postCode;
}
