package com.t3h.group3_petshop.service;

import com.t3h.group3_petshop.model.response.BaseResponse;

public interface IPaymentService {
    BaseResponse<String> createVnPay();
}
