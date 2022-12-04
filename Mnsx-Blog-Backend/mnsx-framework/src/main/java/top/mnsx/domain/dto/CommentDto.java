package top.mnsx.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.mnsx.domain.vo.CommentVo;

import java.util.Date;
import java.util.List;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    /**
     * 文章id
     */
    private Long articleId;
    /**
     * 根评论id
     */
    private Long rootId;
    private String type;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 所回复的目标评论的userid
     */
    private Long toCommentUserId;
    /**
     * 回复目标评论id
     */
    private Long toCommentId;
}
