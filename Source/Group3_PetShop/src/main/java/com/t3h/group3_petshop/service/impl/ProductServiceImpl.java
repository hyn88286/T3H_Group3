package com.t3h.group3_petshop.service.impl;

import com.t3h.group3_petshop.entity.CategoryEntity;
import com.t3h.group3_petshop.entity.ProductEntity;
import com.t3h.group3_petshop.entity.SizeEntity;
import com.t3h.group3_petshop.model.dto.ProductDTO;
import com.t3h.group3_petshop.model.dto.SizeDTO;
import com.t3h.group3_petshop.model.request.ProductFilterRequest;
import com.t3h.group3_petshop.model.response.BaseResponse;
import com.t3h.group3_petshop.repository.CategoryRepository;
import com.t3h.group3_petshop.repository.ProductRepository;
import com.t3h.group3_petshop.repository.SizeRepository;
import com.t3h.group3_petshop.service.IProductService;
import com.t3h.group3_petshop.utils.Constant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductServiceImpl implements IProductService {

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
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductEntity> productEntities = productRepository.findAllByFilter(filterRequest, pageable);

        List<ProductDTO> productDTOS = productEntities.getContent().stream().map(productEntity -> {
            ProductDTO productDTO = new ProductDTO();
            // Set giá trị productDTO
            setCommonValueProductDTO(productDTO, productEntity, filterRequest);
            return productDTO;
        }).collect(Collectors.toList());

        Page<ProductDTO> pageData = new PageImpl<>(productDTOS, pageable, productEntities.getTotalElements());
        BaseResponse<Page<ProductDTO>> response = new BaseResponse<>();
        response.setCode(200);
        response.setMessage("Get all product successfully");
        response.setData(pageData);
        return response;
    }

    @Override
    public BaseResponse<?> updateProduct(ProductDTO productDTO, MultipartFile file) throws IOException {

        BaseResponse<ProductDTO> baseResponse = new BaseResponse<>();

        Optional<CategoryEntity> category = categoryRepository.findById(productDTO.getCategoryId());

        if (category.isEmpty()) {
            baseResponse.setCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage("Category is not exist in system");
            return baseResponse;
        }

        Set<SizeEntity> sizeEntities = sizeRepository.findByIds(productDTO.getSizeIds());
        if (CollectionUtils.isEmpty(sizeEntities)) {
            baseResponse.setCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage("Size is not exist in system");
            return baseResponse;
        }

        ProductFilterRequest productFilterRequest = new ProductFilterRequest();
        productFilterRequest.setCode(productDTO.getCode());
        ProductEntity productExist = productRepository.findByFilter(productFilterRequest);
        if (productExist != null) {
            baseResponse.setCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage("Product with code is exist in system");
            return baseResponse;
        }
        ProductEntity productEntity = modelMapper.map(productDTO, ProductEntity.class);
        productEntity.setCategoryEntity(category.get());
        productEntity.setSizeEntities(sizeEntities);

        String imagePath = saveFileAndReturnPath(file);
        productEntity.setImage(imagePath);

        LocalDateTime now = LocalDateTime.now();
        productEntity.setCreatedDate(now);
        productEntity.setLastModifiedDate(now);
        productEntity.setDeleted(false);
        productRepository.save(productEntity);

        baseResponse.setMessage("Save product successfully");
        baseResponse.setCode(HttpStatus.OK.value());
        return baseResponse;
    }

    @Override
    public BaseResponse<ProductDTO> getProductBy(ProductFilterRequest filterRequest) {
        ProductEntity productEntity = productRepository.findByFilter(filterRequest);
        ProductDTO productDTO = new ProductDTO();
        BaseResponse<ProductDTO> response = new BaseResponse<>();
        // Set giá trị productDTO
        setCommonValueProductDTO(productDTO, productEntity, filterRequest);

        response.setCode(HttpStatus.OK.value());
        response.setMessage("Get product with code successfully");
        response.setData(productDTO);
        return response;
    }

    private void setCommonValueProductDTO(ProductDTO productDTO, ProductEntity productEntity, ProductFilterRequest filterRequest) {
        // Set id
        productDTO.setId(productEntity.getId());
        // Set tên
        productDTO.setName(productEntity.getName());
        // Set ảnh
        if(productEntity.getImage() != null){
            productDTO.setImage(productEntity.getImage());
        }else productDTO.setImage(Constant.IMAGE_PATH_DEPLOY + "file_test/test.jpg");

        // Set mô tả ngắn
        productDTO.setShortDescription(productEntity.getShortDescription());
        // Set mô tả chi tiết
        productDTO.setDescription(productEntity.getDescription());
        // Set code
        productDTO.setCode(productEntity.getCode());
        // Set tên danh mục
        productDTO.setCategory(productEntity.getCategoryEntity().getName());
        // Set danh sách size
        Set<SizeDTO> sizeDTOS = productEntity.getSizeEntities().stream().map(sizeEntity -> {
            SizeDTO sizeDTO = new SizeDTO();
            sizeDTO.setId(sizeEntity.getId());
            sizeDTO.setName(sizeEntity.getName());
            sizeDTO.setWeight(sizeEntity.getWeight());
            return sizeDTO;
        }).collect(Collectors.toSet());
        productDTO.setSizes(sizeDTOS);

        // Set giá sản phẩm trong khoảng min - max
        OptionalDouble minPrice = productEntity.getSizeEntities().stream()
                .mapToDouble(sizeEntity -> productEntity.getPrice() * sizeEntity.getWeight())
                .min();
        productDTO.setMinPrice(minPrice.orElse(0));
        OptionalDouble maxPrice = productEntity.getSizeEntities().stream()
                .mapToDouble(sizeEntity -> productEntity.getPrice() * sizeEntity.getWeight())
                .max();
        productDTO.setMaxPrice(maxPrice.orElse(0));
        OptionalDouble price = productEntity.getSizeEntities().stream()
                .mapToDouble(sizeEntity -> {
                    double val = 0;
                    if (sizeEntity.getId().equals(filterRequest.getSizeId())) {
                        val = productEntity.getPrice() * sizeEntity.getWeight();
                    }
                    return val;
                }).filter(val -> val != 0).findFirst();
        productDTO.setPrice(price.orElse(0));
        // Size id đang đc chọn
        productDTO.setSizeId(filterRequest.getSizeId());
    }

    @Override
    public BaseResponse<?> deleteProduct(Long id) {
        BaseResponse<String> baseResponse = new BaseResponse<>();
        Optional<ProductEntity> optionalProductEntity = productRepository.findById(id);
        if (optionalProductEntity.isEmpty()) {
            baseResponse.setCode(HttpStatus.NOT_FOUND.value());
            baseResponse.setMessage("error not found");
            return baseResponse;
        }
        ProductEntity product = optionalProductEntity.get();
        product.setDeleted(true);
        productRepository.save(product);

        baseResponse.setCode(HttpStatus.OK.value());
        baseResponse.setMessage("deleted successfully");
        return baseResponse;
    }

    private String saveFileAndReturnPath(MultipartFile file) throws IOException {
        File directory = new File(Constant.IMAGE_PATH_LOCAL);
        if (!directory.exists()) { //Kiểm tra thư mục đã tồn tại chưa
            directory.mkdirs();//Tạo các thư mục theo path cần thiết
        }
        long timestamp = Instant.now().getEpochSecond(); //Lấy thời gian hiện tại trả về timestamp

        int lastIndex = Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf('.');
        String extension = file.getOriginalFilename().substring(lastIndex + 1);
        String fileName = timestamp + "." + extension;

        String filePathSave = Constant.IMAGE_PATH_LOCAL + fileName; //Đường dẫn lưu ảnh

        String filePathDeploy = Constant.IMAGE_PATH_DEPLOY + fileName;

        file.transferTo(new File(filePathSave)); //Lưu trữ ảnh vào trong folder

        return filePathDeploy;
    }
}