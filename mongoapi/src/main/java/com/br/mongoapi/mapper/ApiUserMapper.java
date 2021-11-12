package com.br.mongoapi.mapper;

import com.br.mongoapi.requests.ApiUserRequestBody;
import com.br.mongoapi.model.ApiUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class ApiUserMapper {
    public static final ApiUserMapper INSTANCE = Mappers.getMapper(ApiUserMapper.class);
    public abstract ApiUser toUserApi(ApiUserRequestBody apiUserRequestBody);

}
