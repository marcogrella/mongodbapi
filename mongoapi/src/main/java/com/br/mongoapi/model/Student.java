package com.br.mongoapi.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

/* At first moment you did not see any collection in database. This is not an error, it is because
* any data is stored in collection */

@Data
@Document()
public class Student implements Serializable {

    private static final long serialVersionUID = 2763083176353200618L;

    @Id
    private String id;
    private String firstName;
    private String lastName;
    @Indexed(unique = true)
    private String email;
    private Gender gender;
    private Address address;
    private List<String> favoriteBookThemes;
    private BigDecimal totalSpentInBooks;
    private LocalDateTime created;


    public Student(String firstName, String lastName, String email, Gender gender, Address address,
                   List<String> favoriteBookThemes, BigDecimal totalSpentInBooks, LocalDateTime created) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.address = address;
        this.favoriteBookThemes = favoriteBookThemes;
        this.totalSpentInBooks = totalSpentInBooks;
        this.created = created;
    }
}
