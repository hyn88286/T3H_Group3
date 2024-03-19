package com.t3h.group3_petshop.service.impl;

import com.t3h.group3_petshop.entity.CategoryEntity;
import com.t3h.group3_petshop.entity.ProductEntity;
import com.t3h.group3_petshop.entity.SizeEntity;
import com.t3h.group3_petshop.model.dto.ProductDTO;
import com.t3h.group3_petshop.model.request.ProductFilterRequest;
import com.t3h.group3_petshop.model.response.BaseResponse;
import com.t3h.group3_petshop.repository.CategoryRepository;
import com.t3h.group3_petshop.repository.ProductRepository;
import com.t3h.group3_petshop.repository.SizeRepository;
import com.t3h.group3_petshop.service.IProductService;
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
<<<<<<< HEAD
=======

>>>>>>> bbf2cbc8586955739e77511c509b1a72e5a6bc94
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

@Service
public class ProductServiceImpl implements IProductService {
    private Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    @Autowired
<<<<<<< HEAD
    private  ProductRepository productRepository;
    @Autowired
    private  ModelMapper modelMapper;
    @Autowired
    private  CategoryRepository categoryRepository;
    @Autowired
    private  SizeRepository sizeRepository;
=======
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SizeRepository sizeRepository;
>>>>>>> bbf2cbc8586955739e77511c509b1a72e5a6bc94

    @Override
    public BaseResponse<Page<ProductDTO>> getAll(ProductFilterRequest filterRequest, int page, int size) {

<<<<<<< HEAD
        Pageable pageable = PageRequest.of(page,size);
        Page<ProductEntity> productEntities = productRepository.findAllByFilter(filterRequest,pageable);
=======
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductEntity> productEntities = productRepository.findAllByFilter(filterRequest, pageable);
>>>>>>> bbf2cbc8586955739e77511c509b1a72e5a6bc94

        List<ProductDTO> productDTOS = productEntities.getContent().stream().map(productEntity -> {
            ProductDTO productDTO = modelMapper.map(productEntity, ProductDTO.class);
            productDTO.setCategory(productEntity.getCategoryEntity().getName());
            String sizeP = productEntity.getSizeEntities().stream().map(SizeEntity::getName).collect(Collectors.joining(","));
            productDTO.setSize(sizeP);
            return productDTO;
        }).collect(Collectors.toList());

<<<<<<< HEAD
        Page<ProductDTO> pageData = new PageImpl<>(productDTOS,pageable,productEntities.getTotalElements());
=======
        Page<ProductDTO> pageData = new PageImpl<>(productDTOS, pageable, productEntities.getTotalElements());
>>>>>>> bbf2cbc8586955739e77511c509b1a72e5a6bc94
        BaseResponse<Page<ProductDTO>> response = new BaseResponse<>();
        response.setCode(200);
        response.setMessage("success");
        response.setData(pageData);
        return response;
    }

    @Override
    public BaseResponse<?> createProduct(ProductDTO productDTO) {
<<<<<<< HEAD
        logger.info("Start create product: {}",productDTO.toString());
        BaseResponse<?> baseResponse = new BaseResponse<>();
        Optional<CategoryEntity> category = categoryRepository.findById(productDTO.getCategoryId());

        if (category.isEmpty()){
=======
        logger.info("Start create product: {}", productDTO.toString());
        BaseResponse<ProductDTO> baseResponse = new BaseResponse<>();
        Optional<CategoryEntity> category = categoryRepository.findById(productDTO.getCategoryId());

        if (category.isEmpty()) {
>>>>>>> bbf2cbc8586955739e77511c509b1a72e5a6bc94
            baseResponse.setCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage("Category not exits in system");
            return baseResponse;
        }

        Set<SizeEntity> sizeEntities = sizeRepository.findByIds(productDTO.getSizeIds());
<<<<<<< HEAD
        if (CollectionUtils.isEmpty(sizeEntities)){
=======
        if (CollectionUtils.isEmpty(sizeEntities)) {
>>>>>>> bbf2cbc8586955739e77511c509b1a72e5a6bc94
            baseResponse.setCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage("Size not exits in system");
            return baseResponse;
        }
<<<<<<< HEAD
        ProductEntity productEntity = modelMapper.map(productDTO,ProductEntity.class);
=======
        ProductEntity productEntity = modelMapper.map(productDTO, ProductEntity.class);
>>>>>>> bbf2cbc8586955739e77511c509b1a72e5a6bc94
        productEntity.setCategoryEntity(category.get());
        productEntity.setSizeEntities(sizeEntities);
        LocalDateTime now = LocalDateTime.now();
        productEntity.setCreatedDate(now);
        productEntity.setDeleted(false);

<<<<<<< HEAD
        productRepository.save(productEntity);
        logger.info("Save product successfully");
        baseResponse.setMessage("Save product successfully");
        baseResponse.setCode(HttpStatus.OK.value());
=======
        ProductEntity productSave = productRepository.save(productEntity);
        productDTO.setId(productSave.getId());
        Long id = productSave.getId();
        logger.info("Save product successfully");
        baseResponse.setMessage("Save product successfully");
        baseResponse.setCode(HttpStatus.OK.value());
        baseResponse.setData(productDTO);
>>>>>>> bbf2cbc8586955739e77511c509b1a72e5a6bc94
        return baseResponse;
    }
}
