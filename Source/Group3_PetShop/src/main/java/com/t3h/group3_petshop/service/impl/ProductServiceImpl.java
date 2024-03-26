package com.t3h.group3_petshop.service.impl;

import com.t3h.group3_petshop.entity.CategoryEntity;
import com.t3h.group3_petshop.entity.ProductEntity;
import com.t3h.group3_petshop.entity.ProductImageEntity;
import com.t3h.group3_petshop.entity.SizeEntity;
import com.t3h.group3_petshop.model.dto.ProductDTO;
import com.t3h.group3_petshop.model.request.ProductFilterRequest;
import com.t3h.group3_petshop.model.response.BaseResponse;
import com.t3h.group3_petshop.repository.CategoryRepository;
import com.t3h.group3_petshop.repository.ProductRepository;
import com.t3h.group3_petshop.repository.SizeRepository;
import com.t3h.group3_petshop.service.IProductService;
import com.t3h.group3_petshop.utils.Constant;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

@Service
public class ProductServiceImpl implements IProductService {
    private Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SizeRepository sizeRepository;

    @Override
    public BaseResponse<Page<ProductDTO>> getAll(ProductFilterRequest filterRequest, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<ProductEntity> productEntities = productRepository.findAllByFilter(filterRequest,pageable);

        List<ProductDTO> productDTOS = productEntities.getContent().stream().map(productEntity -> {
            ProductDTO productDTO = new ProductDTO();
            // Set tên sản phẩm
            productDTO.setName(productEntity.getName());
            // Set giá sản phẩm
            productDTO.setPrice(productEntity.getPrice());
            // Set giá sản phẩm trong khoảng min - max
            OptionalDouble minPrice = productEntity.getSizeEntities().stream()
                    .mapToDouble(sizeEntity -> productEntity.getPrice() * sizeEntity.getWeight())
                    .min();
            productDTO.setMinPrice(minPrice.orElse(0));
            OptionalDouble maxPrice = productEntity.getSizeEntities().stream()
                    .mapToDouble(sizeEntity -> productEntity.getPrice() * sizeEntity.getWeight())
                    .max();
            productDTO.setMaxPrice(maxPrice.orElse(0));
            // Set Url ảnh ( Lấy ảnh đầu tiên trong list ảnh sản phẩm )
            Optional<String> imageName = productEntity.getProductImageEntities().stream()
                    .map(ProductImageEntity::getName)
                    .findFirst();
            String urlImage = Constant.IMAGE_PATH_DEPLOY + imageName.filter(s -> !s.isEmpty()).orElse(Constant.IMAGE_FILE_TEST);
            productDTO.setUrlImage(urlImage);
            return productDTO;
        }).collect(Collectors.toList());

        Page<ProductDTO> pageData = new PageImpl<>(productDTOS, pageable, productEntities.getTotalElements());
        BaseResponse<Page<ProductDTO>> response = new BaseResponse<>();
        response.setCode(200);
        response.setMessage("success");
        response.setData(pageData);
        return response;
    }

    @Override
    public BaseResponse<?> createProduct(ProductDTO productDTO) {
        logger.info("Start create product: {}", productDTO.toString());
        BaseResponse<ProductDTO> baseResponse = new BaseResponse<>();
        Optional<CategoryEntity> category = categoryRepository.findById(productDTO.getCategoryId());

        if (category.isEmpty()) {
            baseResponse.setCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage("Category not exits in system");
            return baseResponse;
        }
        logger.info("create product");
        Set<SizeEntity> sizeEntities = sizeRepository.findByIds(productDTO.getSizeIds());
        if (CollectionUtils.isEmpty(sizeEntities)) {
            baseResponse.setCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage("Size not exits in system");
            return baseResponse;
        }
        ProductEntity productEntity = modelMapper.map(productDTO, ProductEntity.class);
        productEntity.setCategoryEntity(category.get());
        productEntity.setSizeEntities(sizeEntities);
        LocalDateTime now = LocalDateTime.now();
        productEntity.setCreatedDate(now);
        productEntity.setDeleted(false);

        ProductEntity productSave = productRepository.save(productEntity);

        Long id = productSave.getId();
        logger.info("Save product successfully");
        baseResponse.setMessage("Save product successfully");
        baseResponse.setCode(HttpStatus.OK.value());
        baseResponse.setData(productDTO);
        return baseResponse;
    }

    @Override
    public BaseResponse<ProductDTO> getByCode(String code) {
        Optional<ProductEntity> productEntity = productRepository.findFirstByCodeIsIgnoreCase(code);
        BaseResponse<ProductDTO> response = new BaseResponse<>();
        if(productEntity.isEmpty()){
            response.setCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage("Product not exits in system");
            return response;
        }
        ProductDTO productDTO = modelMapper.map(productEntity, ProductDTO.class);
        response.setCode(HttpStatus.OK.value());
        response.setMessage("Get product with code successfully");
        response.setData(productDTO);
        return response;
    }
}
