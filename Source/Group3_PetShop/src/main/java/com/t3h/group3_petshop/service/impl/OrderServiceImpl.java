package com.t3h.group3_petshop.service.impl;

import com.t3h.group3_petshop.entity.*;
import com.t3h.group3_petshop.model.dto.OrderDTO;
import com.t3h.group3_petshop.model.dto.OrderDetailDTO;
import com.t3h.group3_petshop.model.dto.UserDTO;
import com.t3h.group3_petshop.model.request.OrderFilterRequest;
import com.t3h.group3_petshop.model.response.BaseResponse;
import com.t3h.group3_petshop.repository.*;
import com.t3h.group3_petshop.service.IOrderService;
import com.t3h.group3_petshop.service.IUserService;
import com.t3h.group3_petshop.utils.Constant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IUserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SizeRepository sizeRepository;

    @Override
    public BaseResponse<Page<OrderDTO>> getAll(OrderFilterRequest orderFilterRequest, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderEntity> orderEntities = orderRepository.findAllByFilter(orderFilterRequest, pageable);
        List<OrderDTO> orderDTOS = orderEntities.getContent().stream().map(orderEntity -> {
            OrderDTO orderDTO = modelMapper.map(orderEntity, OrderDTO.class);
            orderDTO.setTotalAmount(orderEntity.getTotalAmount());
            return orderDTO;
        }).collect(Collectors.toList());

        Page<OrderDTO> pageData = new PageImpl<>(orderDTOS, pageable, orderEntities.getTotalElements());
        BaseResponse<Page<OrderDTO>> response = new BaseResponse<>();
        response.setCode(HttpStatus.OK.value());
        response.setMessage("success");
        response.setData(pageData);
        return response;
    }

    @Override
    public BaseResponse<OrderDTO> getDetailByCode(OrderFilterRequest request) {
        String code = request.getCode();
        UserDTO userDTO = userService.getCurrentUser(true);
        // Lấy bản ghi đơn hàng theo mã đơn hàng
        OrderEntity orderEntity = orderRepository.getByCode(userDTO.getId(), code, Constant.ORDER_STATUS_UNPAID);
        // map OrderEntity sang OrderDTO
        OrderDTO orderDTO = modelMapper.map(orderEntity, OrderDTO.class);

        // Lấy danh sách chi tiết các sản phẩm và size trong đơn hàng và trả về 1 list chi tiết
        List<OrderDetailDTO> orderDetailDTOS = orderDetailRepository.findByCode(orderEntity.getId()).stream().map(orderDetailEntity -> {
            OrderDetailDTO orderDetailDTO = new OrderDetailDTO();

            orderDetailDTO.setTotal(orderDetailEntity.getTotal());
            orderDetailDTO.setQuantity(orderDetailEntity.getQuantity());

            Optional<ProductEntity> product = productRepository.findById(orderDetailEntity.getProductEntity().getId());
            if (product.isEmpty()) {
                orderDetailDTO.setProductName(null);
            } else orderDetailDTO.setProductName(product.get().getName());

            Optional<SizeEntity> size = sizeRepository.findById(orderDetailEntity.getSizeEntity().getId());
            if (size.isEmpty()) {
                orderDetailDTO.setSizeName(null);
            } else orderDetailDTO.setSizeName(size.get().getName());

            return orderDetailDTO;
        }).collect(Collectors.toList());

        // Set list chi tiết các sản phẩm và size trong đơn hàng
        orderDTO.setOrderDetails(orderDetailDTOS);
        BaseResponse<OrderDTO> response = new BaseResponse<>();
        response.setCode(HttpStatus.OK.value());
        response.setMessage("Get order info successfully");
        response.setData(orderDTO);
        return response;
    }

    @Override
    public BaseResponse<?> createOrder(OrderDTO orderDTO) {
        BaseResponse<OrderDTO> response = new BaseResponse<>();
        UserDTO userDTO = userService.getCurrentUser(true);
        Optional<UserEntity> user = userRepository.findById(userDTO.getId());
        if (user.isEmpty()) {
            response.setCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage("User not exits in system");
            return response;
        }

        // Timestamp ngày giờ hiện tại
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setCode(formatter.format(cld.getTime()));
        orderEntity.setStatus(Constant.ORDER_STATUS_UNPAID);
        orderEntity.setUserEntity(user.get());
        orderEntity.setTotalAmount(orderDTO.getTotalAmount());
        LocalDateTime now = LocalDateTime.now();
        orderEntity.setCreatedDate(now);
        orderEntity.setCreatedBy(userDTO.getUsername());
        OrderEntity orderSave = orderRepository.save(orderEntity);

        OrderDTO orderResponse = new OrderDTO();
        orderResponse.setId(orderSave.getId());
        orderResponse.setCode(orderSave.getCode());
        response.setMessage("Save order successfully");
        response.setCode(HttpStatus.OK.value());
        response.setData(orderResponse);
        return response;
    }

    public BaseResponse<?> updateOrder(OrderDTO orderDTO) {
        BaseResponse<?> response = new BaseResponse<>();
        UserDTO userDTO = userService.getCurrentUser(true);
        Optional<OrderEntity> order = orderRepository.findById(orderDTO.getId());

        if (order.isEmpty()) {
            response.setCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage("Order not exits in system");
            return response;
        }

        order.get().setAddressShipping(orderDTO.getAddressShipping());
        order.get().setPhoneShipping(orderDTO.getPhoneShipping());
        order.get().setLastModifiedBy(userDTO.getUsername());

        orderRepository.save(order.get());
        response.setMessage("Update order successfully");
        response.setCode(HttpStatus.OK.value());
        return response;
    }

    @Override
    public BaseResponse<?> createOrderDetail(OrderDetailDTO orderDetailDTO) {
        BaseResponse<?> response = new BaseResponse<>();

        Optional<OrderEntity> order = orderRepository.findById(orderDetailDTO.getOrderId());
        if (order.isEmpty()) {
            response.setCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage("Order not exits in system");
            return response;
        }

        Optional<ProductEntity> product = productRepository.findById(orderDetailDTO.getProductId());
        if (product.isEmpty()) {
            response.setCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage("Product not exits in system");
            return response;
        }

        Optional<SizeEntity> size = sizeRepository.findById(orderDetailDTO.getSizeId());
        if (size.isEmpty()) {
            response.setCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage("Size not exits in system");
            return response;
        }

        OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
        orderDetailEntity.setOrderEntity(order.get());
        orderDetailEntity.setProductEntity(product.get());
        orderDetailEntity.setSizeEntity(size.get());
        orderDetailEntity.setQuantity(orderDetailDTO.getQuantity());
        orderDetailEntity.setTotal(orderDetailDTO.getTotal());
        orderDetailRepository.save(orderDetailEntity);
        response.setMessage("Save order detail successfully");
        response.setCode(HttpStatus.OK.value());
        return response;
    }
}
