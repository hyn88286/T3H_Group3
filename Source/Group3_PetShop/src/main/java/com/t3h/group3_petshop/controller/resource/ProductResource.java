package com.t3h.group3_petshop.controller.resource;

import com.t3h.group3_petshop.model.dto.ProductDTO;
import com.t3h.group3_petshop.model.dto.SizeDTO;
import com.t3h.group3_petshop.model.request.ProductFilterRequest;
import com.t3h.group3_petshop.model.response.BaseResponse;
import com.t3h.group3_petshop.service.IProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("api/product")
public class ProductResource {

    private final IProductService service;

    public ProductResource(IProductService service) {
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity<BaseResponse<Page<ProductDTO>>> getAll(@RequestBody ProductFilterRequest filterRequest,
                                                                 @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                                 @RequestParam(name = "size", required = false, defaultValue = "10") int size) {

        return ResponseEntity.ok(service.getAll(filterRequest, page, size));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateProduction(@RequestParam("id") String id,
                                              @RequestParam("name") String name,
                                              @RequestParam("code") String code,
                                              @RequestParam("quantity") String quantity,
                                              @RequestParam("price") String price,
                                              @RequestParam("short_description") String shortDescription,
                                              @RequestParam("description") String description,
                                              @RequestParam("category_id") String categoryId,
                                              @RequestParam("size_ids") String sizeIds,
                                              @RequestParam("image") MultipartFile image) throws IOException {

        ProductDTO productDTO = new ProductDTO();
        if (!Objects.equals(id, "")) {
            productDTO.setId(Long.valueOf(id));
        }
        productDTO.setCode(code);

        productDTO.setName(name);

        productDTO.setQuantity(Integer.parseInt(quantity));

        productDTO.setPrice(Double.valueOf(price));

        productDTO.setShortDescription(shortDescription);

        productDTO.setDescription(description);

        productDTO.setCategoryId(Long.valueOf(categoryId));

        Set<Long> setSizeIds = new HashSet<>();
        String[] arrSizeIds = sizeIds.split("\\s+");

        for (String sizeId : arrSizeIds) {
            setSizeIds.add(Long.valueOf(sizeId));
        }

        productDTO.setSizeIds(setSizeIds);

        return ResponseEntity.ok(service.updateProduct(productDTO, image));
    }

    @PostMapping("/detail")
    public ResponseEntity<?> getProductBy(@RequestBody ProductFilterRequest filterRequest) {
        return ResponseEntity.ok(service.getProductBy(filterRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<?>> deleteProduct(@PathVariable("id") Long id) {
        BaseResponse<?> response = service.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}