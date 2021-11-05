package com.br.mongoapi.model;


import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.mongodb.lang.NonNull;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Document
public class Student implements Serializable {

    private static final long serialVersionUID = 2763083176353200618L;

    @Id
    private String id;
    @NotEmpty(message = "field FIRST NAME is mandatory")
    private String firstName;
    @NotEmpty(message = "field LAST NAME is mandatory")
    private String lastName;
    @Indexed(unique = true)
    @Email(message = "field EMAIL is not a valid email.")
    private String email;
    private Gender gender;
    @Valid
    private Address address;
    @NotEmpty(message = "The favorite book themes can not be empty")
    private List<@NotBlank(message = "field theme cannot be null or empty.") String> favoriteBookThemes;
    @Min(value = 0, message = "Please insert a valid value for field TOTAL SPENT IN BOOKS.")
    @NotNull(message = "field TOTAL SPENT IN BOOKS can not be null.")
    private BigDecimal totalSpentInBooks;
    private LocalDateTime created;

}
