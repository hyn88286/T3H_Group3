package com.t3h.group3_petshop.service;

import com.t3h.group3_petshop.entity.OrderDetailEntity;
import com.t3h.group3_petshop.model.dto.OrderDTO;
import com.t3h.group3_petshop.model.dto.OrderDetailDTO;
import com.t3h.group3_petshop.model.dto.ProductDTO;
import com.t3h.group3_petshop.model.request.OrderFilterRequest;
import com.t3h.group3_petshop.model.response.BaseResponse;
import org.springframework.data.domain.Page;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;


public interface IOrderService {
    BaseResponse<Page<OrderDTO>> getAll(OrderFilterRequest orderFilterRequest, int page, int size);

    BaseResponse<?> createOrder(OrderDTO orderDTO);

    BaseResponse<OrderDTO> getDetailByCode(OrderFilterRequest request);

    BaseResponse<?> createOrderDetail(OrderDetailDTO orderDetailDTO);

    BaseResponse<?> updateOrder(OrderDTO orderDTO);

    BaseResponse<List<OrderDTO>> getOrderByUser();

    BaseResponse<?> getInvoicePdf() throws FileNotFoundException;
}
