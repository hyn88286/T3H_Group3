package com.t3h.group3_petshop.service.impl;

import com.t3h.group3_petshop.entity.CartEntity;
import com.t3h.group3_petshop.entity.ProductEntity;
import com.t3h.group3_petshop.entity.SizeEntity;
import com.t3h.group3_petshop.entity.UserEntity;
import com.t3h.group3_petshop.model.dto.CartDTO;
import com.t3h.group3_petshop.model.dto.ProductDTO;
import com.t3h.group3_petshop.model.request.CartFilterRequest;
import com.t3h.group3_petshop.model.request.ProductFilterRequest;
import com.t3h.group3_petshop.model.response.BaseResponse;
import com.t3h.group3_petshop.repository.CartRepository;
import com.t3h.group3_petshop.repository.ProductRepository;
import com.t3h.group3_petshop.repository.SizeRepository;
import com.t3h.group3_petshop.repository.UserRepository;
import com.t3h.group3_petshop.service.ICartService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CartServiceImpl implements ICartService {
    private Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BaseResponse<Page<CartDTO>> getAll(CartFilterRequest filterRequest, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<CartEntity> cartEntities = cartRepository.findAllByFilter(filterRequest, pageable);

        List<CartDTO> cartDTOS = cartEntities.getContent().stream().map(cartEntity -> {
            CartDTO cartDTO = modelMapper.map(cartEntity, CartDTO.class);
            cartDTO.setSize(cartEntity.getSizeEntity().getName());
            cartDTO.setUserName(cartEntity.getUserEntity().getUsername());
            cartDTO.setProductName(cartEntity.getProductEntity().getName());
            return cartDTO;
        }).collect(Collectors.toList());

        Page<CartDTO> pageData = new PageImpl<>(cartDTOS, pageable, cartEntities.getTotalElements());
        BaseResponse<Page<CartDTO>> response = new BaseResponse<>();
        response.setCode(200);
        response.setMessage("success");
        response.setData(pageData);
        return response;
    }

    @Override
    public BaseResponse<?> addToCart(CartDTO cartDTO) {
        logger.info("Start create product: {}", cartDTO.toString());

        BaseResponse<?> baseResponse = new BaseResponse<>();
        Optional<ProductEntity> product = productRepository.findById(cartDTO.getProductId());

        if (product.isEmpty()) {
            baseResponse.setCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage("Product not exits in system");
            return baseResponse;
        }

        Optional<SizeEntity> size = sizeRepository.findById(cartDTO.getSizeId());

        if (size.isEmpty()) {
            baseResponse.setCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage("Size not exits in system");
            return baseResponse;
        }

        Optional<UserEntity> user = userRepository.findById(cartDTO.getUserId());

        if (user.isEmpty()) {
            baseResponse.setCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage("User not exits in system");
            return baseResponse;
        }

        CartEntity cartEntity = modelMapper.map(cartDTO, CartEntity.class);
        cartEntity.setProductEntity(product.get());
        cartEntity.setSizeEntity(size.get());
        cartEntity.setUserEntity(user.get());
        cartEntity.setTotal(cartDTO.getTotal());

        cartRepository.save(cartEntity);
        logger.info("Save product successfully");
        baseResponse.setMessage("Save product successfully");
        baseResponse.setCode(HttpStatus.OK.value());
        return baseResponse;
    }
}
