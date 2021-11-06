package com.br.mongoapi.requests;

import com.br.mongoapi.model.Address;
import com.br.mongoapi.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
public class StudentRequestBody implements Serializable{

    private static final long serialVersionUID = -4040516302734722943L;

    @NotEmpty(message = "field FIRST NAME is mandatory")
    private String firstName;
    @NotEmpty(message = "field LAST NAME is mandatory")
    private String lastName;
    @Indexed(unique = true)
    @Email(message = "field EMAIL is not a valid email.")
    private String email;
    @NotNull(message = "field GENDER cannot be null.")
    private Gender gender;
    @Valid
    private Address address;
    @NotEmpty(message = "The favorite book themes can not be empty")
    private List<@NotBlank(message = "field theme cannot be null or empty.") String> favoriteBookThemes;
    @PositiveOrZero(message = "Please insert a valid value for field TOTAL SPENT IN BOOKS.")
    @NotNull(message = "field TOTAL SPENT IN BOOKS can not be null.")
    private BigDecimal totalSpentInBooks;
    private LocalDateTime created;

}
