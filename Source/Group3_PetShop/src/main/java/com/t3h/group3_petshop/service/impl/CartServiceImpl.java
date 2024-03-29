package com.t3h.group3_petshop.service.impl;

import com.t3h.group3_petshop.entity.*;
import com.t3h.group3_petshop.model.dto.CartDTO;
import com.t3h.group3_petshop.model.dto.ProductDTO;
import com.t3h.group3_petshop.model.dto.SizeDTO;
import com.t3h.group3_petshop.model.dto.UserDTO;
import com.t3h.group3_petshop.model.request.CartFilterRequest;
import com.t3h.group3_petshop.model.request.ProductFilterRequest;
import com.t3h.group3_petshop.model.response.BaseResponse;
import com.t3h.group3_petshop.repository.CartRepository;
import com.t3h.group3_petshop.repository.ProductRepository;
import com.t3h.group3_petshop.repository.SizeRepository;
import com.t3h.group3_petshop.repository.UserRepository;
import com.t3h.group3_petshop.service.ICartService;
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

import java.util.List;
import java.util.Optional;
import java.util.Set;
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
    public BaseResponse<Page<CartDTO>> getAll(int page, int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        BaseResponse<Page<CartDTO>> response = new BaseResponse<>();
        Long userId = null;
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                // Lấy thông tin người dùng từ Principal
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                // Lấy giá trị username đăng nhập
                String username = userDetails.getUsername();
                // Lấy dữ liệu bản ghi user đang đăng nhập
                UserEntity userEntity = userRepository.findByUsername(username);
                // Set giá trị userId cho filter
                userId = userEntity.getId();
            }
        }

        if (userId == null) {
            response.setCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage("User is not login");
            return response;
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<CartEntity> cartEntities = cartRepository.findAllByFilter(userId, pageable);

        List<CartDTO> cartDTOS = cartEntities.getContent().stream().map(cartEntity -> {
            CartDTO cartDTO = new CartDTO();
            // Set tên size
            cartDTO.setSizeName(cartEntity.getSizeEntity().getName());
            // Set cân nặng size
            Integer weightSize = cartEntity.getSizeEntity().getWeight();
            cartDTO.setWeightSize(weightSize);
            // Set username thêm sản phẩm vào giỏ hàng
            cartDTO.setCreatedBy(cartEntity.getUserEntity().getUsername());
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

            // Set Url ảnh ( Lấy ảnh đầu tiên trong list ảnh sản phẩm )
            Optional<String> imageName = productEntity.get().getProductImageEntities().stream()
                    .map(ProductImageEntity::getName)
                    .findFirst();

            String urlImage = Constant.IMAGE_PATH_DEPLOY + imageName.filter(s -> !s.isEmpty()).orElse(Constant.IMAGE_FILE_TEST);
            cartDTO.setImgProduct(urlImage);
            return cartDTO;
        }).collect(Collectors.toList());

        Page<CartDTO> pageData = new PageImpl<>(cartDTOS, pageable, cartEntities.getTotalElements());

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
        cartEntity.setSizeEntity(size.get());
        cartEntity.setUserEntity(user.get());

        cartRepository.save(cartEntity);
        logger.info("Save product successfully");
        baseResponse.setMessage("Save product successfully");
        baseResponse.setCode(HttpStatus.OK.value());
        return baseResponse;
    }
}
