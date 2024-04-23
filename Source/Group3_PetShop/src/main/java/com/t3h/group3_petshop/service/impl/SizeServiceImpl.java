package com.t3h.group3_petshop.service.impl;

import com.t3h.group3_petshop.entity.SizeEntity;
import com.t3h.group3_petshop.model.dto.SizeDTO;
import com.t3h.group3_petshop.model.request.SizeFilterRequest;
import com.t3h.group3_petshop.model.response.BaseResponse;
import com.t3h.group3_petshop.repository.SizeRepository;
import com.t3h.group3_petshop.service.ISizeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SizeServiceImpl implements ISizeService {
    @Autowired
    private SizeRepository sizeRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BaseResponse<Page<SizeDTO>> getAll(SizeFilterRequest filterRequest, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<SizeEntity> sizeEntities = sizeRepository.findAllByFilter(filterRequest, pageable);
        List<SizeDTO> sizeDTOS = sizeEntities.getContent().stream().map(sizeEntity -> {
            SizeDTO sizeDTO = new SizeDTO();
            sizeDTO.setId(sizeEntity.getId());
            sizeDTO.setCode(sizeEntity.getCode());
            sizeDTO.setWeight(sizeEntity.getWeight());
            sizeDTO.setName(sizeEntity.getName());
            return sizeDTO;
        }).collect(Collectors.toList());

        Page<SizeDTO> pageData = new PageImpl<>(sizeDTOS, pageable, sizeEntities.getTotalElements());
        BaseResponse<Page<SizeDTO>> response = new BaseResponse<>();
        response.setCode(HttpStatus.OK.value());
        response.setMessage("Get all size successfully");
        response.setData(pageData);
        return response;
    }
}
