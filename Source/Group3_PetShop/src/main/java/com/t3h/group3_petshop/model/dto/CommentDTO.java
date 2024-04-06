package com.t3h.group3_petshop.model.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private String content;
    private LocalDateTime createdDate;

    private String createdBy;

    private LocalDateTime modifiedDate;

    private String modifiedBy;
    private Long productId;
    private Long userId;
}
