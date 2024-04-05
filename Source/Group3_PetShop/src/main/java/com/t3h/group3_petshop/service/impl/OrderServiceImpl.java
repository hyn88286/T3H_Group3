package com.t3h.group3_petshop.service.impl;

import com.t3h.group3_petshop.entity.OrderDetailEntity;
import com.t3h.group3_petshop.entity.OrderEntity;
import com.t3h.group3_petshop.entity.ProductEntity;
import com.t3h.group3_petshop.entity.UserEntity;
import com.t3h.group3_petshop.model.dto.OrderDTO;
import com.t3h.group3_petshop.model.dto.OrderDetailDTO;
import com.t3h.group3_petshop.model.dto.ProductDTO;
import com.t3h.group3_petshop.model.request.OrderFilterRequest;
import com.t3h.group3_petshop.model.response.BaseResponse;
import com.t3h.group3_petshop.repository.OrderDetailRepository;
import com.t3h.group3_petshop.repository.OrderRepository;
import com.t3h.group3_petshop.repository.ProductRepository;
import com.t3h.group3_petshop.repository.UserRepository;
import com.t3h.group3_petshop.service.IOrderService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements IOrderService {

    private Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    private final OrderRepository orderRepository;

    private final OrderDetailRepository orderDetailRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;


    public OrderServiceImpl(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository, UserRepository userRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public BaseResponse<Page<OrderDTO>> getAll(OrderFilterRequest orderFilterRequest, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);

        Page<OrderEntity> orderEntities = orderRepository.findAllByFilter(orderFilterRequest,pageable);

        List<OrderDTO> orderDTOS = orderEntities.getContent().stream().map(orderEntity -> {
            OrderDTO orderDTO = modelMapper.map(orderEntity,OrderDTO.class);
            orderDTO.setUsername(orderEntity.getUserEntity().getUsername());
            orderDTO.setTotalAmount(orderEntity.getTotalAmount());
            return orderDTO;
        }).collect(Collectors.toList());

        Page<OrderDTO> pageData = new PageImpl(orderDTOS,pageable,orderEntities.getTotalElements());
        BaseResponse<Page<OrderDTO>> response = new BaseResponse<>();
        response.setCode(HttpStatus.OK.value());
        response.setMessage("success");
        response.setData(pageData);
        return response;
    }

}
