package com.the.doanthucung.service.impl;

import com.the.doanthucung.entity.CategoryEntity;
import com.the.doanthucung.entity.ProductEntity;
import com.the.doanthucung.entity.SizeEntity;
import com.the.doanthucung.model.dto.ProductDTO;
import com.the.doanthucung.model.request.ProductFilterRequest;
import com.the.doanthucung.model.response.BaseResponse;
import com.the.doanthucung.repository.CategoryRepository;
import com.the.doanthucung.repository.ProductRepository;
import com.the.doanthucung.repository.SizeRepository;
import com.the.doanthucung.service.IProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    @Autowired
    private final CategoryRepository categoryRepository;

    @Autowired
    private final SizeRepository sizeRepository;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper, CategoryRepository categoryRepository, SizeRepository sizeRepository) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
        this.sizeRepository = sizeRepository;
    }

    @Override
    public BaseResponse<Page<ProductDTO>> getAll(ProductFilterRequest filterRequest, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<ProductEntity> productEntities = productRepository.findAllByFilter(filterRequest,pageable);

        List<ProductDTO> productDTOS = productEntities.getContent().stream().map(productEntity -> {
           ProductDTO productDTO = modelMapper.map(productEntity,ProductDTO.class);
           productDTO.setCategoryId(Long.valueOf(productEntity.getCategoryEntity().getName()));
           String sizeP = productEntity.getSizeEntities().stream().map(SizeEntity::getName).collect(Collectors.joining(","));
           productDTO.setSize(sizeP);
           return productDTO;
        }).collect(Collectors.toList());
        Page<ProductDTO> page1 = new PageImpl(productDTOS,pageable,productEntities.getTotalElements());
        BaseResponse<Page<ProductDTO>> response = new BaseResponse<>();
        response.setCode(200);
        response.setMessage("success");
        response.setData(page1);
        return response;
    }

    @Override
    public BaseResponse<?> createProduct(ProductDTO productDTO) {
        BaseResponse baseResponse = new BaseResponse();
        Optional<CategoryEntity> category = categoryRepository.findById(productDTO.getCategoryId());
        if (category.isEmpty()){
            baseResponse.setCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage("category not exits in system");
            return baseResponse;
        }
        Set<SizeEntity> sizeEntities = sizeRepository.finByIds(productDTO.getSizeIds());
        if (CollectionUtils.isEmpty(sizeEntities)){
            baseResponse.setCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage("size not exits in system");
            return baseResponse;
        }
        ProductEntity productEntity = modelMapper.map(productDTO,ProductEntity.class);
        productEntity.setCategoryEntity(category.get());
        productEntity.setSizeEntities(sizeEntities);
        LocalDateTime now = LocalDateTime.now();
        productEntity.setCreatedDate(now);
        productEntity.setDelete(false);


        productRepository.save(productEntity);
        baseResponse.setMessage("sve product success");
        baseResponse.setCode(HttpStatus.OK.value());
        return baseResponse;
    }
}
