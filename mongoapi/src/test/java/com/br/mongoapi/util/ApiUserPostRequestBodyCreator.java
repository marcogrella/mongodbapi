package com.br.mongoapi.util;

import com.br.mongoapi.constant.UserApiRoles;
import com.br.mongoapi.requests.ApiUserRequestBody;

public class ApiUserPostRequestBodyCreator {

    public static ApiUserRequestBody createApiUserPRB(){
        return ApiUserRequestBody.builder()
                .username("Jhon")
                .email("john@email.com")
                .authorities(UserApiRoles.ROLE_SUPER_ADMIN)
                .build();
    }

}
