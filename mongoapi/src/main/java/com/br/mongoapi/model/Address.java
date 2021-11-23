package com.br.mongoapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@Builder
public class Address {

    @Schema(description = "Country of a person", example = "USA")
    @NotEmpty(message = "field COUNTRY is mandatory")
    @Length(max = 40, message = "field COUNTRY is too long. Maximum range is 40 characters.")
    private String country;
    @Schema(description = "City of a person", example = "Oklahoma")
    @Length(max = 40, message = "field CITY is too long. Maximum range is 40 characters.")
    @NotEmpty(message = "field CITY is mandatory")
    private String city;
    @Schema(description = "Post Code of person's city", example = "0000000")
    @Length(max = 40, message = "field POSTCODE is too long. Maximum range is 40 characters.")
    @NotEmpty(message = "field POSTCODE is mandatory")
    private String postCode;
}
