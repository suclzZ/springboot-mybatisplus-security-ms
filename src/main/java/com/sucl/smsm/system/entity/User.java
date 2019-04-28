package com.sucl.smsm.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.sucl.smsm.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author sucl
 * @since 2018-12-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
//@Accessors(chain = true)
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "USER_ID", type = IdType.UUID)
    private String userId;

    @TableField("USER_NAME")
    private String userName;

    @TableField("USER_CAPTION")
    private String userCaption;

    @TableField("PASSWORD")
    private String password;

    @TableField("AGE")
    private Integer age;

    @TableField("ADDRESS")
    private String address;

    @TableField("TELEPHONE")
    private String telephone;

    @TableField("EMAIL")
    private String email;

    @TableField("STATUS")
    private String status;

    @TableField("MEMO")
    private String memo;


}
