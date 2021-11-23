package com.br.mongoapi.requests;

import com.br.mongoapi.model.Address;
import com.br.mongoapi.model.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentPostRequestBody implements Serializable{

    private static final long serialVersionUID = -4040516302734722943L;

    @Schema(description = "First Name of a person", example = "Marco")
    @NotEmpty(message = "field FIRST NAME is mandatory")
    @Length(max = 40, message = "field FIRST NAME is too long. Maximum range is 40 characters.")
    private String firstName;
    @Schema(description = "Last Name of a person", example = "Smith")
    @Length(max = 40, message = "field LAST NAME is too long. Maximum range is 40 characters.")
    @NotEmpty(message = "field LAST NAME is mandatory")
    private String lastName;
    @Indexed(unique = true)
    @Schema(description = "Email", example = "person@email.com")
    @Email(message = "field EMAIL is not a valid email.")
    @Length(max = 40, message = "field EMAIL is too long. Maximum range is 40 characters.")
    private String email;
    @Schema(description = "Gender of a person", example = "MALE")
    @NotNull(message = "field GENDER cannot be null.")
    private Gender gender;
    @Valid
    private Address address;
    @Schema(description = "List of favorites themes of book", example = "Java Coding")
    @NotEmpty(message = "The favorite book themes can not be empty")
    private List<
            @Length(max = 40, message = "field FAVORITE BOOK THEMES is too long. Maximum range is 40 characters.")
            @NotBlank(message = "field theme cannot be null or empty.") String> favoriteBookThemes;

    @Schema(description = "Total amount of spent money spent in books", example = "190.00")
    @PositiveOrZero(message = "Please insert a valid value for field TOTAL SPENT IN BOOKS.")
    @Max(value = 1000000000, message = "field TOTAL SPENT IN BOOKS cannot be too long.")
    @NotNull(message = "field TOTAL SPENT IN BOOKS can not be null.")
    private BigDecimal totalSpentInBooks;

}
