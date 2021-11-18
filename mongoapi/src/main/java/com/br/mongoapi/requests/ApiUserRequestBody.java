package com.br.mongoapi.requests;

import com.br.mongoapi.constant.UserApiRoles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiUserRequestBody implements Serializable {

    private static final long serialVersionUID = -6634988844087344148L;

    @NotEmpty(message = "field USERNAME is mandatory")
    @Length(max = 40, message = "field USERNAME is too long. Maximum range is 40 characters.")
    private String username;
    @Email(message = "field EMAIL is not a valid email.")
    @Length(max = 40, message = "field EMAIL is too long. Maximum range is 40 characters.")
    private String email;
    @NotNull(message = "field AUTHORITIES cannot be null.")
    private UserApiRoles authorities;

}
