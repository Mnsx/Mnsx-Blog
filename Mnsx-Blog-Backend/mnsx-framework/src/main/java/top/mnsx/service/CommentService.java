package top.mnsx.service;

import top.mnsx.domain.ResponseResult;
import top.mnsx.domain.dto.CommentDto;
import top.mnsx.domain.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Mnsx_x
* @description 针对表【blog_comment(评论表)】的数据库操作Service
* @createDate 2022-11-26 15:02:36
*/
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(String type, Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(CommentDto commentDto);
}
