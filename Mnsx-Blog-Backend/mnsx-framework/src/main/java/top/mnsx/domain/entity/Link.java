package top.mnsx.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName blog_link
 */
@TableName(value ="blog_link")
@Data
public class Link implements Serializable {
    @TableId
    private Long id;

    private String name;

    private String logo;

    private String description;

    private String address;

    private String status;

    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    private Integer delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}