package com.br.mongoapi.repository;

import com.br.mongoapi.model.ApiUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserApiRepository extends MongoRepository<ApiUser, String> {

    ApiUser findByUsername(String username);
    ApiUser findApiUserByEmail(String email);


}
