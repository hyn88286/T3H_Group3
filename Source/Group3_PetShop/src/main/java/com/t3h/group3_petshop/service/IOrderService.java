package com.t3h.group3_petshop.service;

import com.t3h.group3_petshop.model.dto.OrderDTO;
import com.t3h.group3_petshop.model.request.OrderFilterRequest;
import com.t3h.group3_petshop.model.response.BaseResponse;
import org.springframework.data.domain.Page;


public interface IOrderService {
    BaseResponse<Page<OrderDTO>> getAll(OrderFilterRequest orderFilterRequest, int page, int size);
}
