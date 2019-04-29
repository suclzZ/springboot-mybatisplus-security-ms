package com.sucl.smsm.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.sucl.smsm.core.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.sucl.smsm.security.user.IUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

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
public class User extends BaseEntity implements IUser {

    private static final long serialVersionUID = 1L;

    @TableId(value = "USER_ID", type = IdType.UUID)
    private String userId;

    @TableField("USER_NAME")
    private String username;

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


    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public List<String> getRoles() {
        return new ArrayList<>();
    }
}
