package com.t3h.group3_petshop.service;

import com.t3h.group3_petshop.model.dto.SizeDTO;
import com.t3h.group3_petshop.model.request.SizeFilterRequest;
import com.t3h.group3_petshop.model.response.BaseResponse;
import org.springframework.data.domain.Page;

public interface ISizeService {
    BaseResponse<Page<SizeDTO>> getAll(SizeFilterRequest filterRequest, int page, int size);
}
