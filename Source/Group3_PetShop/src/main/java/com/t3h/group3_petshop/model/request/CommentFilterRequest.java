package com.t3h.group3_petshop.model.request;

import com.t3h.group3_petshop.model.dto.CommentDTO;
import lombok.Data;

@Data
public class CommentFilterRequest {
    private String content;
    private Long userId;
    private Long productId;
    private String username;

}
