package top.mnsx.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName sys_user
 */
@TableName(value ="sys_user")
@Data
public class User implements Serializable {
    private Long id;

    private String userName;

    private String nickName;

    private String password;

    private String type;

    private String status;

    private String email;

    private String phonenumber;

    private String sex;

    private String avatar;

    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    private Integer delFlag;

    private static final long serialVersionUID = 1L;
}