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
        logger.info("Bắt đầu thêm sản phẩm vào giỏ hàng: {}", cartDTO);

        BaseResponse<?> baseResponse = new BaseResponse<>();

        // Kiểm tra xem sản phẩm tồn tại không
        Optional<ProductEntity> productOptional = productRepository.findById(cartDTO.getProductId());
        if (productOptional.isEmpty()) {
            baseResponse.setCode(HttpStatus.NOT_FOUND.value());
            baseResponse.setMessage("Sản phẩm không tồn tại trong hệ thống");
            return baseResponse;
        }

        // Kiểm tra xem kích thước sản phẩm tồn tại không
        Optional<SizeEntity> sizeOptional = sizeRepository.findById(cartDTO.getSizeId());
        if (sizeOptional.isEmpty()) {
            baseResponse.setCode(HttpStatus.NOT_FOUND.value());
            baseResponse.setMessage("Kích thước không tồn tại trong hệ thống");
            return baseResponse;
        }

        // Kiểm tra xem người dùng tồn tại không
        Optional<UserEntity> userOptional = userRepository.findById(cartDTO.getUserId());
        if (userOptional.isEmpty()) {
            baseResponse.setCode(HttpStatus.NOT_FOUND.value());
            baseResponse.setMessage("Người dùng không tồn tại trong hệ thống");
            return baseResponse;
        }

        ProductEntity product = productOptional.get();
        SizeEntity size = sizeOptional.get();
        UserEntity user = userOptional.get();

        // Tạo một đối tượng giỏ hàng và đặt các thuộc tính
        CartEntity cartEntity = new CartEntity();
        cartEntity.setProductEntity(product);
        cartEntity.setSizeEntity(size);
        cartEntity.setUserEntity(user);
        cartEntity.setTotal(cartDTO.getTotal());

        // Lưu giỏ hàng vào cơ sở dữ liệu
        cartRepository.save(cartEntity);

        logger.info("Thêm sản phẩm vào giỏ hàng thành công");
        baseResponse.setMessage("Thêm sản phẩm vào giỏ hàng thành công");
        baseResponse.setCode(HttpStatus.OK.value());

        return baseResponse;
    }

}
