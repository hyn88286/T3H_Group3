package com.t3h.group3_petshop.service;

import com.t3h.group3_petshop.model.request.OrderFilterRequest;
import com.t3h.group3_petshop.model.response.BaseResponse;

public interface IPaymentService {
    BaseResponse<String> createVnPay(OrderFilterRequest request);
}
