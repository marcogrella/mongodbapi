package com.br.mongoapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@Builder
public class Address {

    @NotEmpty(message = "field COUNTRY is mandatory")
    @Length(max = 40, message = "field COUNTRY is too long. Maximum range is 40 characters.")
    private String country;
    @Length(max = 40, message = "field CITY is too long. Maximum range is 40 characters.")
    @NotEmpty(message = "field CITY is mandatory")
    private String city;
    @Length(max = 40, message = "field POSTCODE is too long. Maximum range is 40 characters.")
    @NotEmpty(message = "field POSTCODE is mandatory")
    private String postCode;
}
