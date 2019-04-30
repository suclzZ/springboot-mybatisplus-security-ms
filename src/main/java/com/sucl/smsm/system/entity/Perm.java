package com.sucl.smsm.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sucl.smsm.core.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author sucl
 * @since 2019-04-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
//@Accessors(chain = true)
public class Perm extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "perm_id", type = IdType.UUID)
    private String permId;

    private String perm;


}
