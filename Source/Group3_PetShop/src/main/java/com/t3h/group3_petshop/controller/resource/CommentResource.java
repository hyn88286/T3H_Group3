package com.t3h.group3_petshop.controller.resource;

import com.t3h.group3_petshop.entity.CommentEntity;
import com.t3h.group3_petshop.model.dto.CommentDTO;
import com.t3h.group3_petshop.model.dto.OrderDTO;
import com.t3h.group3_petshop.model.dto.ProductDTO;
import com.t3h.group3_petshop.model.request.CommentFilterRequest;
import com.t3h.group3_petshop.model.request.OrderFilterRequest;
import com.t3h.group3_petshop.model.request.ProductFilterRequest;
import com.t3h.group3_petshop.model.response.BaseResponse;
import com.t3h.group3_petshop.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/comments")
public class CommentResource {
    private final CommentService commentService;

    public CommentResource(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping()
    public ResponseEntity<BaseResponse<Page<CommentDTO>>> getAll(@RequestBody CommentFilterRequest filterRequest,
                                                                 @RequestParam(name = "page",required = false,defaultValue = "0") int page,
                                                                 @RequestParam(name = "size",required = false,defaultValue = "10") int size){

        return ResponseEntity.ok(commentService.getAll(filterRequest,page,size));
    }

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<CommentDTO>> addComment(@RequestBody CommentDTO commentDTO) {
        if (commentDTO == null || commentDTO.getContent() == null || commentDTO.getUserId() == null || commentDTO.getProductId() == null) {
            // Trả về lỗi nếu thông tin comment không hợp lệ
            BaseResponse<CommentDTO> response = new BaseResponse<>();
            response.setCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage("Invalid comment data");
            return ResponseEntity.badRequest().body(response);
        }

        // Thực hiện kiểm tra và xử lý comment ở đây
        return ResponseEntity.ok(commentService.addComment(commentDTO));
    }
    @PutMapping("/{commentId}")
    public ResponseEntity<BaseResponse<CommentDTO>> updateComment(@PathVariable Long commentId,
                                                                  @RequestBody CommentDTO commentDTO) {
        BaseResponse<CommentDTO> response = commentService.updateComment(commentId, commentDTO);
        return ResponseEntity.status(response.getCode()).body(response);
    }
    @DeleteMapping("/{commentId}")
    public ResponseEntity<BaseResponse<?>> deletecommet(@PathVariable("commentId") Long commentId){
        BaseResponse<?> baseResponse = commentService.deleteComment(commentId);
        return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
    }
}
