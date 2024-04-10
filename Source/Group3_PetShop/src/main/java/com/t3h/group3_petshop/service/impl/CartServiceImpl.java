package com.t3h.group3_petshop.service.impl;

import com.t3h.group3_petshop.entity.*;
import com.t3h.group3_petshop.model.dto.CartDTO;
import com.t3h.group3_petshop.model.dto.UserDTO;
import com.t3h.group3_petshop.model.response.BaseResponse;
import com.t3h.group3_petshop.repository.CartRepository;
import com.t3h.group3_petshop.repository.ProductRepository;
import com.t3h.group3_petshop.repository.SizeRepository;
import com.t3h.group3_petshop.repository.UserRepository;
import com.t3h.group3_petshop.service.ICartService;
import com.t3h.group3_petshop.service.IUserService;
import com.t3h.group3_petshop.utils.Constant;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CartServiceImpl implements ICartService {
    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

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

    @Autowired
    private IUserService userService;

    @Override
    public BaseResponse<Page<CartDTO>> getAll(int page, int size) {
        BaseResponse<Page<CartDTO>> response = new BaseResponse<>();
        UserDTO userDTO = userService.getCurrentUser(true);
        // Lấy ra userId đang đăng nhập
        Long userId = userDTO.getId();
        if (userId == null) {
            response.setCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage("User is not login or not exit in system");
            return response;
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<CartEntity> cartEntities = cartRepository.findAllByFilter(userId, pageable);

        List<CartDTO> cartDTOS = cartEntities.getContent().stream().map(cartEntity -> {
            CartDTO cartDTO = new CartDTO();
            // Set id
            cartDTO.setId(cartEntity.getId());

            // Set id size
            cartDTO.setSizeId(cartEntity.getSizeEntity().getId());

            // Set tên size
            cartDTO.setSizeName(cartEntity.getSizeEntity().getName());

            // Set cân nặng size
            Integer weightSize = cartEntity.getSizeEntity().getWeight();
            cartDTO.setWeightSize(weightSize);

            // Set username thêm sản phẩm vào giỏ hàng
            cartDTO.setCreatedBy(cartEntity.getUserEntity().getUsername());

            // Set id sản phẩm
            cartDTO.setProductId(cartEntity.getProductEntity().getId());

            // Set tên sản phẩm
            String productName = cartEntity.getProductEntity().getName();
            cartDTO.setProductName(productName);

            // Set giá sản phẩm
            Double productPrice = cartEntity.getProductEntity().getPrice();
            cartDTO.setProductPrice(productPrice);

            // Set số lượng sản phẩm
            Integer quantity = cartEntity.getQuantity();
            cartDTO.setQuantity(quantity);

            // Set tổng tiền trên 1 sản phẩm
            Double totalOneP = productPrice * weightSize * quantity;
            cartDTO.setTotalOneP(totalOneP);

            Optional<ProductEntity> productEntity = productRepository.findById(cartEntity.getProductEntity().getId());

            if (productEntity.isEmpty()) {
                cartDTO.setImgProduct(Constant.IMAGE_PATH_DEPLOY + Constant.IMAGE_FILE_TEST);
            } else {
                Optional<String> imageName = productEntity.get().getProductImageEntities().stream()
                        .map(ProductImageEntity::getName)
                        .findFirst();
                String urlImage = Constant.IMAGE_PATH_DEPLOY + imageName.filter(s -> !s.isEmpty()).orElse(Constant.IMAGE_FILE_TEST);
                cartDTO.setImgProduct(urlImage);
            }
            // Set Url ảnh ( Lấy ảnh đầu tiên trong list ảnh sản phẩm )
            return cartDTO;
        }).collect(Collectors.toList());

        Page<CartDTO> pageData = new PageImpl<>(cartDTOS, pageable, cartEntities.getTotalElements());

        response.setCode(200);
        response.setMessage("Get all product cart successfully");
        response.setData(pageData);
        return response;
    }

    @Override
    public BaseResponse<?> addToCart(CartDTO cartDTO) {
        BaseResponse<?> response = new BaseResponse<>();
        UserDTO userDTO = userService.getCurrentUser(true);
        // Lấy ra userId đang đăng nhập
        Long userId = userDTO.getId();
        if (userId == null) {
            response.setCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage("User is not login or not exit in system");
            return response;
        }

        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
        Optional<ProductEntity> product = productRepository.findById(cartDTO.getProductId());

        if (product.isEmpty()) {
            response.setCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage("Product not exits in system");
            return response;
        }

        Optional<SizeEntity> size = sizeRepository.findById(cartDTO.getSizeId());

        if (size.isEmpty()) {
            response.setCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage("Size not exits in system");
            return response;
        }

        Optional<CartEntity> cartExist = cartRepository.cartExist(userId, product.get().getId(), size.get().getId());
        cartExist.ifPresent(dto -> cartDTO.setId(dto.getId()));

        CartEntity cartEntity = new CartEntity();
        cartEntity.setId(cartDTO.getId());
        cartEntity.setSizeEntity(size.get());
        cartEntity.setUserEntity(userEntity);
        cartEntity.setProductEntity(product.get());
        cartEntity.setTotal(cartDTO.getTotalOneP());
        cartEntity.setQuantity(cartDTO.getQuantity());
        LocalDateTime now = LocalDateTime.now();
        cartEntity.setCreatedDate(now);
        cartEntity.setCreatedBy(userDTO.getUsername());
        cartRepository.save(cartEntity);
        response.setMessage("Add to cart successfully");
        response.setCode(HttpStatus.OK.value());
        return response;
    }

    public BaseResponse<Long> countCart() {
        BaseResponse<Long> response = new BaseResponse<>();
        UserDTO userDTO = userService.getCurrentUser(true);
        Long userId = userDTO.getId();
        if (userId == null) {
            response.setCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage("User is not login or not exit in system");
            return response;
        }
        Long countProductCart = cartRepository.countCartByUser(userId);
        response.setCode(HttpStatus.OK.value());
        response.setMessage("Get count product cart successfully");
        response.setData(countProductCart);
        return response;
    }

    public BaseResponse<?> deleteCart(CartDTO cartDTO) {
        BaseResponse<?> response = new BaseResponse<>();
        CartEntity cartEntity = modelMapper.map(cartDTO, CartEntity.class);
        cartRepository.delete(cartEntity);
        response.setCode(HttpStatus.OK.value());
        response.setMessage("Delete cart successfully");
        return response;
    }
}
