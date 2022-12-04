package top.mnsx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.mnsx.annotation.SystemLog;
import top.mnsx.constants.CommentConstants;
import top.mnsx.domain.ResponseResult;
import top.mnsx.domain.dto.CommentDto;
import top.mnsx.domain.entity.Comment;
import top.mnsx.service.CommentService;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @SystemLog
    @GetMapping("/commentList")
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize) {
        return commentService.commentList(CommentConstants.ARTICLE_COMMENT_TYPE, articleId, pageNum, pageSize);
    }

    @SystemLog
    @PostMapping
    public ResponseResult addComment(@RequestBody CommentDto commentDto) {
        return commentService.addComment(commentDto);
    }

    @SystemLog
    @GetMapping("/linkCommentList")
    public ResponseResult linkCommentList(Integer pageNum, Integer pageSize) {
        return commentService.commentList(CommentConstants.LINK_COMMENT_TYPE, null, pageNum, pageSize);
    }
}
