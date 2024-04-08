package com.t3h.group3_petshop.model.request;

import lombok.Data;

@Data
public class OrderFilterRequest {
    private Integer userId;

    private String code;
}
