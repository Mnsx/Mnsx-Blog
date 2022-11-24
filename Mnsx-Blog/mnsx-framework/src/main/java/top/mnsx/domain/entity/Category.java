package top.mnsx.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName blog_category
 */
@TableName(value ="blog_category")
@Data
public class Category implements Serializable {
    @TableId
    private Long id;

    private String name;

    private Long pid;

    private String description;

    private String status;

    private Long createBy;

    private Date createTime;

    private Long updateBy;

    private Date updateTime;

    private Integer delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}