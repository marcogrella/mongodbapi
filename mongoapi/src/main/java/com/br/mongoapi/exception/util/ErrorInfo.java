package com.br.mongoapi.exception.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorsInfo {

    private String field;
    private String fieldMessage;

}
