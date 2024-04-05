package com.t3h.group3_petshop.service;

import com.t3h.group3_petshop.entity.CommentEntity;
import com.t3h.group3_petshop.model.dto.CommentDTO;
import com.t3h.group3_petshop.model.dto.ProductDTO;
import com.t3h.group3_petshop.model.request.CommentFilterRequest;
import com.t3h.group3_petshop.model.request.ProductFilterRequest;
import com.t3h.group3_petshop.model.response.BaseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    BaseResponse<Page<CommentDTO>> getAll(CommentFilterRequest commentFilterRequest, int page, int size);
    BaseResponse<CommentDTO> addComment(CommentDTO commentDTO);
    BaseResponse<CommentDTO> updateComment(Long commentId, CommentDTO commentDTO);

    BaseResponse<?> deleteComment(Long commentId);
}
