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

    private Long createBy;

    private Date createTime;

    private Long updateBy;

    private Date updateTime;

    private Integer delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}