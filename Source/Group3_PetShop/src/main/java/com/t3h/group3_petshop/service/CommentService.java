package com.t3h.group3_petshop.service;

import com.t3h.group3_petshop.model.dto.CommentDTO;
import com.t3h.group3_petshop.model.request.CommentFilterRequest;
import com.t3h.group3_petshop.model.response.BaseResponse;
import org.springframework.data.domain.Page;


public interface CommentService {
    BaseResponse<Page<CommentDTO>> getAll(CommentFilterRequest commentFilterRequest, int page, int size);
    BaseResponse<CommentDTO> addComment(CommentDTO commentDTO);
    BaseResponse<CommentDTO> updateComment(Long commentId, CommentDTO commentDTO);

    BaseResponse<?> deleteComment(Long commentId);
}
