package com.br.mongoapi.util;

import com.br.mongoapi.constant.UserApiRoles;
import com.br.mongoapi.model.ApiUser;

public class ApiUserCreator {

    public static ApiUser createUserToBeSaved(){
        return ApiUser.builder()
                .username("Jhon")
                .email("john@email.com")
                .authorities(UserApiRoles.ROLE_SUPER_ADMIN)
                .password("$2a$10$e8M07KA0K03XwqP.smitgOET7hpw4I.XZJgt8Yb9/GeiigngRGAl6")
                .build();
    }

}
