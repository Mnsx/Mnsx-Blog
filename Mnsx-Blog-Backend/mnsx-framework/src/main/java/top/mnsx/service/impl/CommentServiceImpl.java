package top.mnsx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import top.mnsx.constants.CommentConstants;
import top.mnsx.domain.ResponseResult;
import top.mnsx.domain.dto.CommentDto;
import top.mnsx.domain.entity.Comment;
import top.mnsx.domain.entity.User;
import top.mnsx.domain.vo.CommentVo;
import top.mnsx.domain.vo.PageVo;
import top.mnsx.enums.AppHttpCodeEnum;
import top.mnsx.exception.SystemException;
import top.mnsx.service.CommentService;
import top.mnsx.mapper.CommentMapper;
import org.springframework.stereotype.Service;
import top.mnsx.service.UserService;
import top.mnsx.utils.BeanCopyUtil;

import java.util.List;

/**
* @author Mnsx_x
* @description 针对表【blog_comment(评论表)】的数据库操作Service实现
* @createDate 2022-11-26 15:02:36
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{

    @Autowired
    private UserService userService;

    @Override
    public ResponseResult commentList(String type, Long articleId, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        // 查询对应文章的跟评论
        wrapper.eq(CommentConstants.ARTICLE_COMMENT_TYPE.equals(type), Comment::getArticleId, articleId);
        // rootId为-1跟评论
        wrapper.eq(Comment::getRootId, CommentConstants.ROOT_COMMENT_ID);
        // 评论类型
        wrapper.eq(Comment::getType, type);
        // 分页
        Page<Comment> page = new Page<>(pageNum, pageSize);
        page(page, wrapper);

        List<CommentVo> commentVos = null;
        if (page.getRecords() != null) {
             commentVos = toCommentList(page.getRecords());
        }

        // 查询所有跟评论对应的子评论并赋值
        commentVos.forEach(commentVo -> {
            List<CommentVo> children = getChildren(commentVo.getId());
            commentVo.setChildren(children);
        });

        return ResponseResult.okResult(new PageVo(commentVos, page.getTotal()));
    }

    @Override
    public ResponseResult addComment(CommentDto commentDto) {
        Comment comment = BeanCopyUtil.copyBean(commentDto, Comment.class);
        if (!StringUtils.hasText(comment.getContent())) {
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }

    private List<CommentVo> toCommentList(List<Comment> list) {
        List<CommentVo> commentVos = BeanCopyUtil.copyBeanList(list, CommentVo.class);
        // 遍历vo集合
        commentVos.forEach(commentVo -> {
            // 通过CreateBy查询用户昵称
            User user = userService.getById(commentVo.getCreateBy());
            String nickName = user.getNickName();
            commentVo.setUsername(nickName);
            // 通过toCommentUserId查询评论人昵称
            Long toCommentUserId = commentVo.getToCommentUserId();
            // 判断toCommentId不为-1
            if (toCommentUserId != -1) {
                String toCommentNickName = userService.getById(toCommentUserId).getNickName();
                commentVo.setToCommentUseName(toCommentNickName);
            }
        });
        return commentVos;
    }

    /**
     * 查询所有跟评论对应的子评论并赋值
     * @param commentId 跟评论ID
     * @return 返回子评论集合
     */
    private List<CommentVo> getChildren(Long commentId) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getRootId, commentId);
        wrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> comments = list(wrapper);
        return toCommentList(comments);
    }
}




