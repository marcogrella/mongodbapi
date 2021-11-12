package com.br.mongoapi.mapper;

import com.br.mongoapi.model.Student;
import com.br.mongoapi.requests.StudentPostRequestBody;
import com.br.mongoapi.requests.StudentPutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class StudentMapper {
    public static final StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);
    public abstract Student toStudent(StudentPostRequestBody studentPostRequestBody);
    public abstract Student toStudent(StudentPutRequestBody studentPutRequestBody);

}
