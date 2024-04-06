package com.t3h.group3_petshop.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> {
    private int code;

    private String message;

    private T data;


}
